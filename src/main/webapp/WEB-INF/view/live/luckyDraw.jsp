<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 大转盘抽奖 -->
<link href="${ctx}/css/activity-style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ctx}/js/jQueryRotate.2.2.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.easing.min.js"></script>
<script type="text/javascript" src="${ctx}/js/globalTip.js"></script>
<script type="text/javascript" src="${ctx}/js/prizeTip.js"></script>

<div class="luckyBox">
	<img class="bigLucky-img" alt="抽奖" src="${ctx}/images/rgtTop.png">
	<div class="bigLucky">
		<img class="lucky-tit" alt="抽奖" src="${ctx}/images/lucjtit.png">
		<div class="main">
				<script type="text/javascript">
					var loadingObj = new loading(document.getElementById('loading'), {
						radius : 20,
						circleLineWidth : 8
					});
					loadingObj.show();
				</script>
			<div id="outercont">
				<div id="outer-cont" style="overflow:hidden;">
					<div id="outer">
						<img src="${ctx}/images/activity-lottery-1.png" width="480px">
					</div>
				</div>
				<div id="inner-cont">
					<div id="inner">
						<div class="innerCenter">
							<div class="innerCenter-integral" onclick="lottery()">
								<span >${qidaIntegral}积分</span>
								<img class="innerCenter-img" src="${ctx}/images/turnplate-pointer.png" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	function lottery() {
		/* $("#outer").rotate({ //inner内部指针转动，outer外部转盘转动
			duration : 3000, //转动时间 
			angle :0, //开始角度 
			animateTo : 3600 , //转动角度 
			easing : $.easing.easeOutSine, //动画扩展 
		});	 */
		if (${isPulish} == 1) {
			globalTip("服务器在更新, 请稍后再试");
			return false;
		}
		$(".innerCenter-integral").attr("onclick", "");
		$.ajax({
			type : 'POST',
			url : '${ctx}/live/lottery.html',
			dataType : 'json',
			cache : false,
			error : function() {
				globalTip('出错了！');
				return false;
			},
			success : function(json) {
				//$(".innerCenter-integral").unbind('click').css("cursor", "default");
				if (json.success == 0) {
					globalTip(json.message);
					return false;
				} else {
					sending=true;
					var angle = parseInt(json.angle); //角度 
					var msg = json.msg; //提示信息
					$("#outer").rotate({ //inner内部指针转动，outer外部转盘转动
						duration : 3000, //转动时间 
						angle : 0, //开始角度 
						animateTo : 3600 + angle, //转动角度 
						easing : $.easing.easeOutSine, //动画扩展 
						callback : function() {
							$("#send_msg_text").val("我抽中了"+ msg +", 大家都来试试运气！");
							onSendMsg(); 
							prizelTip("恭喜您抽中了" + msg,"${ctx}/live/guess.html", "${HHR_PREFIX}/user_center/prizeManage.htm", json.qidaIntegral);
							$(".innerCenter-integral").attr("onclick", "lottery()");
						}
					});
				}
			}
		});
	}
</script>