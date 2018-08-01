<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${ctx}/css/mobile.css?v=1.0.3">
<!-- 聊天室 -->
	<div class="roomBody">
	<%-- <img src="${ctx}/images/rgtTop.png"/> --%>
		<!-- 显示内容 -->
		<div id="div1" class="wordInfo">
			<ul class="video-sms-list" id="video_sms_list">
	       </ul>
		</div>
		<!-- 输入框 -->
		<div class='fabuBody'>
                <div class="video-discuss-pane">
                <div class="video-discuss-form" id="video-discuss-form">
                	<a href="javascript:void(0);"  onclick="showEmotionDialog()"><i class="video-discuss-face"></i>表情</a>
                	<a href="javascript:void(0);" class="clScr" onclick="clearScreen()"><i class="clearScreen"></i>清屏</a>
	                <button type="button" class="video-discuss-button" onclick="onSendMsg()">发送</button>
					<textarea onkeyup="checkText(this.value)" type="text" rows='2' maxlength="30" name='message' class="video-discuss-input" id="send_msg_text" placeholder="限30字以内" style="overflow-y:hidden"></textarea>
                </div>
                <div class="video-discuss-emotion" id="video-discuss-emotion" style="display: none;">
                    <div class="video-emotion-pane">
                        <ul id="emotionUL">
                        </ul>
                    </div>
                </div>
                </div>
		</div>
	</div>
<%-- <script type="text/javascript" src="${ctx}/js/jquery-2.1.4.min.js"></script> --%>
<!--引入webim sdk-->
<script type="text/javascript" src="${ctx}/sdk/webim.js"></script>
<script type="text/javascript" src="${ctx}/sdk/json2.js"></script>
<!--TLS web sdk(只用于托管模式，独立模式不用引入)-->
<script type="text/javascript" src="https://tls.qcloud.com/libs/api.min.js"></script>
<!--webim demo js-->
<script type="text/javascript" src="${ctx}/js/demo_base.js"></script>
<script type="text/javascript" src="${ctx}/js/demo_group_notice.js"></script>
<script type="text/javascript">
//帐号模式，0-表示独立模式，1-表示托管模式
var accountMode=${accountMode};

//官方 demo appid,需要开发者自己修改（托管模式）
var sdkAppID = ${sdkAppID};
var accountType = ${accountType};

var avChatRoomId = '${avChatRoomId}'; //默认房间群ID，群类型必须是直播聊天室（AVChatRoom），这个为官方测试ID(托管模式)

if(webim.Tool.getQueryString("groupid")){
    avChatRoomId=webim.Tool.getQueryString("groupid");//用户自定义房间群id
}

var selType=webim.SESSION_TYPE.GROUP;
var selToID = avChatRoomId;//当前选中聊天id（当聊天类型为私聊时，该值为好友帐号，否则为群号）
var selSess = null;//当前聊天会话

//默认群组头像(选填)
var selSessHeadUrl = '${ctx}/img/2017.jpg';


//当前用户身份
var loginInfo = {
        'sdkAppID': sdkAppID, //用户所属应用id,必填
        'appIDAt3rd': sdkAppID, //用户所属应用id，必填
        'accountType': accountType, //用户所属应用帐号类型，必填
        'identifier': '${identifier}', //当前用户ID,必须是否字符串类型，选填
        'identifierNick': '${identifierNick}', //当前用户昵称，选填
        'userSig': '${userSig}', //当前用户身份凭证，必须是字符串类型，选填
        'headurl': '${empty headurl?'../img/2016.gif':headurl}'//当前用户默认头像，选填
};
//监听（多终端同步）群系统消息方法，方法都定义在demo_group_notice.js文件中
//注意每个数字代表的含义，比如，
//1表示监听申请加群消息，2表示监听申请加群被同意消息，3表示监听申请加群被拒绝消息等
var onGroupSystemNotifys = {
        //"1": onApplyJoinGroupRequestNotify, //申请加群请求（只有管理员会收到,暂不支持）
        //"2": onApplyJoinGroupAcceptNotify, //申请加群被同意（只有申请人能够收到,暂不支持）
        //"3": onApplyJoinGroupRefuseNotify, //申请加群被拒绝（只有申请人能够收到,暂不支持）
        //"4": onKickedGroupNotify, //被管理员踢出群(只有被踢者接收到,暂不支持)
        "5": onDestoryGroupNotify, //群被解散(全员接收)
        //"6": onCreateGroupNotify, //创建群(创建者接收,暂不支持)
        //"7": onInvitedJoinGroupNotify, //邀请加群(被邀请者接收,暂不支持)
        //"8": onQuitGroupNotify, //主动退群(主动退出者接收,暂不支持)
        //"9": onSetedGroupAdminNotify, //设置管理员(被设置者接收,暂不支持)
        //"10": onCanceledGroupAdminNotify, //取消管理员(被取消者接收,暂不支持)
        "11": onRevokeGroupNotify, //群已被回收(全员接收)
        "255": onCustomGroupNotify//用户自定义通知(默认全员接收)
};


//监听连接状态回调变化事件
var onConnNotify=function(resp){
    switch(resp.ErrorCode){
        case webim.CONNECTION_STATUS.ON:
            //webim.Log.warn('连接状态正常...');
            break;
        case webim.CONNECTION_STATUS.OFF:
            webim.Log.warn('连接已断开，无法收到新消息，请检查下你的网络是否正常');
            break;
        default:
            webim.Log.error('未知连接状态,status='+resp.ErrorCode);
            break;
    }
};


//监听事件
var listeners = {
        "onConnNotify": onConnNotify, //选填
        "jsonpCallback": jsonpCallback, //IE9(含)以下浏览器用到的jsonp回调函数,移动端可不填，pc端必填
        "onBigGroupMsgNotify": onBigGroupMsgNotify, //监听新消息(大群)事件，必填
        "onMsgNotify": onMsgNotify,//监听新消息(私聊(包括普通消息和全员推送消息)，普通群(非直播聊天室)消息)事件，必填
        "onGroupSystemNotifys": onGroupSystemNotifys, //监听（多终端同步）群系统消息事件，必填
        "onGroupInfoChangeNotify": onGroupInfoChangeNotify//监听群资料变化事件，选填
};


var isAccessFormalEnv=true;//是否访问正式环境

if(webim.Tool.getQueryString("isAccessFormalEnv")=="false"){
    isAccessFormalEnv=false;//访问测试环境
}

var isLogOn=true;//是否在浏览器控制台打印sdk日志

//其他对象，选填
var options={
    'isAccessFormalEnv': isAccessFormalEnv,//是否访问正式环境，默认访问正式，选填
    'isLogOn': isLogOn//是否开启控制台打印日志,默认开启，选填
};

var curPlayAudio=null;//当前正在播放的audio对象

var openEmotionFlag=false;//是否打开过表情

if(accountMode==1){//托管模式
    //判断是否已经拿到临时身份凭证
    if (webim.Tool.getQueryString('tmpsig')) {
        if (loginInfo.identifier == null) {
            webim.Log.info('start fetchUserSig');
            //获取正式身份凭证，成功后会回调tlsGetUserSig(res)函数
            TLSHelper.fetchUserSig();
        }
    } else {//未登录,无登录态模式
        //sdk登录
        sdkLogin();
        setTimeout("getGroupMemberInfo(avChatRoomId)", 1500);
    }
}else{//独立模式
    //sdk登录
    sdkLogin();
    setTimeout("getGroupMemberInfo(avChatRoomId)", 1500);
}
                  
//清屏
function clearScreen(){
	$("#video_sms_list").find("li").remove();
}
                  
//网页内按下回车触发
$(document).keypress(function(e) { 
     // 回车键事件 
     if(e.keyCode == 13) { 
  	   onSendMsg();
     } 
});


function sc(){
	var e=document.getElementById("div1");
	e.scrollTop=e.scrollHeight;
}

var s=setInterval("sc()",500);

//脏字检测
function checkText(content){
	$.post("${ctx}/live/checkText",{content:content},function(data){
		if(data!=null){
			$("#send_msg_text").val(data);
		}
	});
}

//退出
function logout(){
	quitBigGroup();
	window.setTimeout(function(){location.href="${ctx}/live/logout"}, 1000);
}


//读取群组成员
function getGroupMemberInfo(group_id) {
    var options = {
        'GroupId': group_id,
        'MemberInfoFilter': [
        	'Account',
            'Role'
        ]
    };
    webim.getGroupMemberInfo(
            options,
            function (resp) {
                if (resp.MemberNum <= 0) {
                    alert('该群组目前没有成员');
                    return;
                }
                var html="";
	  			var count=0;
                for (var i in resp.MemberList) {
                	if(resp.MemberList[i].Role=="Admin"){
		  				html+="<li><img src='${ctx}/images/tx.png'><span>"+resp.MemberList[i].Member_Account+"</span><i class='adminIco'></i></li>";
		  				count++;
		  			}
                }
                for (var i in resp.MemberList) {
                	if(resp.MemberList[i].Role=="Member"){
		  				html+="<li><img src='${ctx}/images/tx.png'><span>"+resp.MemberList[i].Member_Account+"</span><i class='hyIco'></i></li>";
		  				count++;
		  			}
                }
                $("#memberList").append(html);
	  			$("#memberCount").text(count);
            },
            function (err) {
                alert(err.ErrorInfo);
            }
    );
}						
</script>

