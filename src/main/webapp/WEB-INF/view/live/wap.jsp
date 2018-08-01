<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0"/>
    <meta name="keywords" content="">
	<meta name="description" content="">
	<meta name="format-detection" content="telephone=no">
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<link rel="stylesheet" href="${ctx}/css/mobile_wap.css?v=1.0.3">
    <title>小合直播</title>
</head>
<body onbeforeunload="quitBigGroup()">
		<div class="video-page">
			<div class="video-pane-head">
                 <div class="video-pane-info">
                      <div class="video-info">
                          <div class="user-img">
                              <img src="${ctx}/images/logo.png" width="150">
                          </div>
                       <div class="user-info-text">
                 	   <div class="user-info-name">小合直播</div>
                       <div class="user-info-num"> 
                                
                          <i class="user-icon-fans"></i><span id="user-icon-fans">0</span>
                          <i class="user-icon-like"></i><span id="user-icon-like">0</span>
                        </div>
                     </div>
                  </div>
              </div>
              <a href="javascript:void(0);" class="video-close" onclick="quitBigGroup()"></a>
            </div>
		
		
            <div class="video-play">
				<div id="id_video_container" style="width:100%; height:auto;"></div>
            </div>
            
            <!-- 公告 -->
            <div class="video-tip">
            	<img src="${ctx}/images/mes.png">
            	<span>欢迎来到小合直播</span>
            </div>
            <!--聊天部分 start-->
            <div class="video-pane">
                <div class="video-pane-body">
                    <div class="video-discuss">
                        <ul class="video-sms-list" id="video_sms_list">
                        </ul>
                        <div class="video-discuss-pane">
                            <div class="video-discuss-tool" id="video-discuss-tool">
                                <!--<span class="like-icon zoomIn green"></span>-->
                                <a href="javascript:void(0);" class="video-discuss-sms" onclick="smsPicClick()"></a>
                                
                                <a href="javascript:void(0);" class="video-discuss-like" onclick="sendGroupLoveMsg()"></a>
                            </div>
                            <div class="video-discuss-form" id="video-discuss-form" style="display: none">
                                <input type="text" class="video-discuss-input" id="send_msg_text">
                                <a href="javascript:void(0);" class="video-discuss-face" onclick="showEmotionDialog()"></a>
                                <button class="video-discuss-button" onclick="onSendMsg()">发送</button>
                            </div>
                            <div class="video-discuss-emotion" id="video-discuss-emotion" style="display: none">
                                <div class="video-emotion-pane">
                                    <ul id="emotionUL">
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
       </div>
<script src="//qzonestyle.gtimg.cn/open/qcloud/video/live/h5/live_connect.js" charset="utf-8"></script>
<script type="text/javascript">
(function () {
     var player = new qcVideo.Player("id_video_container", {
         "channel_id":"9896587163607547842",
		 "app_id":"1252771481",
         "width":480,
         "height":320,
         "https":1
     });
})()
</script>

 <!--引入webim sdk-->
<script type="text/javascript" src="${ctx}/sdk/webim.js"></script>
<script type="text/javascript" src="${ctx}/sdk/json2.js"></script>
<!--TLS web sdk(只用于托管模式，独立模式不用引入)-->
<script type="text/javascript" src="https://tls.qcloud.com/libs/api.min.js"></script>
<!--webim demo js-->
<script type="text/javascript" src="${ctx}/js/demo_base_wap.js"></script>
<script type="text/javascript" src="${ctx}/js/demo_group_notice.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-2.1.4.min.js"></script>
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
    }
}else{//独立模式
    //sdk登录
    sdkLogin();
}
</script>
<script type="text/javascript">
//确定介绍/声明/公告详情 的高度
var het = $(".stcockQd").height();
var introPhgt = het-570;
$(".introInfo").height(introPhgt);

//介绍/声明/公告切换
var introLis=$(".leftIntro li");
var introInfo=$(".introP");
introLis.click(function(){
	var introIndex=$(this).index();
	introLis.removeClass();
	$(this).addClass("introActive");
	introInfo.css("display","none");
	introInfo.eq(introIndex).css("display","block");
});
</script>
</body>
</html>