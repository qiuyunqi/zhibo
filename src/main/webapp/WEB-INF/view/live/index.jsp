<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, initial-scale=1.0, user-scalable=no" name="viewport"/>
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title>直播-live</title>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<link type="text/css" rel="stylesheet" href="${ctx}/css/qd.css"></link>
<style>
@media screen and (max-width:1655px){
	.conceptLeft{display: block;}
	.stcockQd{width:710px;}
	.luckyBox{right:710px;}
}
@media screen and (max-width:1521px){
	.conceptLeft{display: block;width:235px;}
	.stcockQd{width:710px;}
	.luckyBox{right:710px;left:235px;}
}
@media screen and (min-width: 1187px) and (max-width:1420px){
	.memTip{display: block;}
	.memberLft{display: none;}
	.stcockQd{width:710px;}
	.luckyBox{right:710px;left:0;}
	.zbBody{overflow:auto;min-width:1200px;min-height:882px;  }
}
@media screen and (min-width: 1421px){
	.memTip{display: none;}
	.memberLft{display: block;}
}

@media screen and (max-width:1186px){
	.memTip{display: block;}
	.memberLft{display: none;}
	.stcockQd{width:710px;}
	.luckyBox{right:710px;left:0;}
	.zbBody{overflow:auto;min-width:1200px;min-height:882px;  }
}
li a.zhiboNav{
	background: #f0f9ff none repeat scroll 0 0;
    border-top: 2px solid #2db1e1;
    color: #2db1e1;
}
</style>
</head>
<body class="zbBody" onbeforeunload="quitBigGroup()">
	<!-- 顶部客服电话以及导航 -->
	<%@ include file="topLive.jsp" %>
	<!-- 蓝色用户名部分 -->
	<div class="userName">
		<div class="userContainer">
			<span class="fontSize">欢迎来到小积分博大奖</span>
		</div>
	</div>
	<!-- 内容部分  -->
	<div class="index-wrapper">
		<div  class="hotList">
		 		<!-- 左边内容 -->
		 		<%@ include file="member.jsp" %>
		 		<!-- 抽奖 -->
		 		<%@ include file="luckyDraw.jsp" %>
		 		
		 		<!-- 中间视频部分 -->
		 		<div class="stcockQd">
		 			<!-- 私聊/点播/点赞/客服/保存到桌面 -->
		 			<div class="qdLine" id="video-discuss-tool">
		 				<div class="qdLine-conten">
			 				<!-- <a class="sTalk" href="javascript:void(0)" title="我的私聊" data-toggle="modal" data-target="#private"></a> -->
			 				<!-- <a href="javascript:void(0);" class="zan"  title="点赞" onclick="sendGroupLoveMsg()"><i class="video-discuss-like"></i><span>点赞</span></a>
			 				<a class="chance" href="javascript:void(0)" title="点播" data-toggle="modal" data-target="#demand"></a> -->
			 				<!-- <a class="saveUrl"  onclick="toDesktop('http:\//baidu.com/','超级合伙人直播间')">保存到桌面</a>
			 				<a class="kefuQq" target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=2211247320&site=qq&menu=yes">QQ在线客服</a> -->
		 				</div>
		 			</div>
		 			<!-- 视频 -->
		 			<div class="media">
		 				<div id="id_video_container" style="width:100%;height:auto;"></div>
		 			</div>
		 			<!-- 右边聊天 -->
		 		<%@ include file="chatRoom.jsp" %>
		 		</div>
		</div>
	</div>
	<!-- 弹窗 私聊-->
	<div class="dialog modal" id="private" tabindex="-1" role="dialog" aria-labelledby="myModalLabel6" aria-hidden="true">
		<div class="fullPage"></div>
		<div class="data">
			 <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
		        <h3 id="myModalLabel6">在线私聊</h3>
		      </div>
		      <!-- 私聊内容 -->
		      <div class="modal-body">
		      	<!-- 左边部分 -->
				<div class="leftTalk">
					<p class="lfTalkLis"><img src="${ctx}/images/tx.png"><span>admin</span></p>
				</div>
				<!-- 右边部分 -->
				<div class="rihtTalk">
					<!-- 聊天内容 -->
					<div class="talkContent"></div>
					<!-- 输入框 -->
					<div class="talkInput video-discuss-form" id="video-discuss-form">
						<form action='' method='post'>
							<div class="video-discuss-pane">
			                <div class="video-discuss-form" id="video-discuss-form">
			                	<a href="javascript:void(0);"  onclick="showEmotionDialog()"><i class="video-discuss-face"></i>表情</a>
			                	<a href="javascript:void(0);" class="clScr" onclick="clearScreen()"><i class="clearScreen"></i>清屏</a>
				                <button type="button" class="video-discuss-button" onclick="onSendMsg()">发送</button>
								<textarea type="text" rows='3' name='message' class="video-discuss-input" id="send_msg_text"></textarea>
			                </div>
							<div class="video-discuss-emotion" id="video-discuss-emotion" style="display: none">
			                    <div class="video-emotion-pane">
			                        <ul id="emotionUL">
			                        </ul>
			                    </div>
			                </div>
			                </div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- footer -->
	<div class="index-footer">
		<span>版权及最终解释权所有©超级合伙人 Copyright Reserved 2014-2016 沪ICP备15014727号-1</span>
	</div>
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
	<script src="${ctx}/js/live_connect.js"></script>
 <!-- <script src="http://qzonestyle.gtimg.cn/open/qcloud/video/live/h5/live_connect.js" charset="utf-8"></script> -->
<script type="text/javascript">
var player;
(function(){
	var option ={
		"channel_id":"9896587163607547842",
		"app_id":"1252771481",
		"width":"704",
		"height":"400",
		"volume": "0.6",
		"https": 1
	};
player = new qcVideo.Player("id_video_container", option);
})();
</script> 
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "https://hm.baidu.com/hm.js?690d69a00c22990dc2a6ecb0b0dcfe12";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>	
</body>
</html>