<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-INF/view/live/include.jsp"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta
	content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, initial-scale=1.0, user-scalable=no"
	name="viewport" />
<title>大盘猜猜乐</title>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/qd.css"></link>
<style>
li a.guessNav {
	background: #f0f9ff none repeat scroll 0 0;
	border-top: 2px solid #2db1e1;
	color: #2db1e1;
}
</style>
</head>
<body>
	<!-- 顶部客服电话以及导航 -->
	<%@ include file="topLive.jsp"%>
	<!-- 内容部分  -->
	<div class="guess-content">
		<!-- banner -->
		<div class="guess-banner">
			<div class="guess-banImg">
				<img alt="大盘猜猜乐" src="${ctx}/images/guessBan.png">
			</div>
		</div>
		<!-- banner下内容 -->
		<!-- k线图 -->
		<%@ include file="stockLine.jsp"%>
		<div class="kongge"></div>
		<!-- k线图end -->
		<!-- 猜涨跌 -->
		<div class="guessSlds">
			<!-- 箭头 -->
			<div class="guess-nex">
				<a class="prev" href="javascript:void(0)"><i class="preBtn"></i></a>
				<a class="next" href="javascript:void(0)"><i class="nexBtn"></i></a>
			</div>
			<div class="guess-zdBody">
				<!-- <ul class="nowContent">
					
					</ul> -->
			</div>
		</div>
		<!-- 猜涨跌end -->
		<!-- banner下内容end  -->
		<c:if test="${not empty recordList}">
		<div id="guessData">
        	<div class="dataTitle">
        		<ul>
        			<li>序号</li>
        			<li class="titleName">期数</li>
        			<li class="titleName">猜注积分</li>
        			<li class="titleName">猜涨跌(奇、偶)</li>
        			<li class="stakeDate">猜注时间</li>
        			<!-- <li class="titleName">赔率</li> -->
        			<li class="titleName">盈亏金额（元）</li>
        		</ul>
        	</div>
        	<div class="dataNumber">
	        	<c:forEach items="${recordList}" var="recored" varStatus="index">
	        		<ul>
	        			<li>${index.index+1}</li>
	        			<li class="stakeNumber">${recored.guessMarket.name}</li>
	        			<li class="stakeData">${recored.score}</li>
	        			<li class="stakeType">
	        				<c:if test="${recored.guessType == 1}">涨(奇)</c:if>
	        				<c:if test="${recored.guessType == 2}">涨(偶)</c:if>
	        				<c:if test="${recored.guessType == 3}">跌(奇)</c:if>
	        				<c:if test="${recored.guessType == 4}">跌(偶)</c:if>
	        			</li>
	        			<li class="stakeDate">
	        				<fmt:formatDate value="${recored.guessTime}" pattern="yyyy-MM-dd HH:mm:ss" />
	        			</li>
	        			<li class="stakeMoney">${recored.orderIncome}</li>
	        		</ul>
	        	</c:forEach>
        	</div>
       		<div class="page">
				<domi:pagination ctx="${ctx}" pageVolume="${pageSize}"
					url="${ctx}/live/guess.html"
					totalNum="${totalCount}" curPageNum="${pageNo}"
					formId="pageForm">
				</domi:pagination>
			</div>
        </div>
		</c:if>
		<!-- 中奖/说明 -->
		<div class="guess-downCont">
			<!-- 中奖 -->
			<div class="guess-detail">
				<h5>
					<span>上期中奖名单</span>
				</h5>
				<div class="guess-detalInfo" style="height: 471px; ">
					<ul class="guess-dInfoTit">
						<li>用户名</li>
						<li>奖品</li>
						<li>中奖时间</li>
						<li>用户名</li>
						<li>奖品</li>
						<li>中奖时间</li>
					</ul>
					<!-- 中奖列表 -->
					<div class="guess-textbd">
						<ul class="guess-dInfoCont">
							<c:forEach items="${records }" var="record">
								<li style="height: 40px; "><span class="guess-userName">${empty record.fuUser.nickName?'':record.fuUser.nickName }</span>
									<span class="guess-prize">${record.orderIncome }积分</span> <span
									class="guess-date"><fmt:formatDate
											value="${record.guessMarket.acceptTime }"
											pattern="MM-dd HH:mm" /></span></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
			<!-- 说明 -->
			<%@ include file="caption.jsp"%>
		</div>
		<div class="clear downLoa"></div>
	</div>
	<!-- footer -->
	<%@ include file="footer.jsp"%>
	<script src="${ctx}/js/jquery.SuperSlide.2.1.1.js"></script>
	<script src="${ctx}/js/globalTip.js"></script>
	<script>
		//退出
		function logout() {
			window.setTimeout(function() {
				location.href = "${ctx}/live/logout"
			}, 1000);
		}

		//中奖名单向上滚动
		jQuery(".guess-detalInfo").slide({
			mainCell : ".guess-textbd ul",
			autoPlay : true,
			effect : "topMarquee",
			vis : 3,
			interTime : 50
		});

		//鼠标经过箭头才显示
		$(".guess-nex a").mouseover(function() {
			$(this).css("opacity", "1");
		});
		$(".guess-nex a").mouseout(function() {
			$(this).css("opacity", "0.3");
		});

		//鼠标点击涨跌出现弹框
		//上午
		$(".nowamZc i").click(function() {
			var jdVal = $(this).parent().next().find(".guessBtn").text();
			globalTip("有" + jdVal + "的人赞同你的观点");
		});
		//下午
		$(".nowpmZc i").click(function() {
			var jdVal = $(this).parent().next().find(".guessBtn").text();
			globalTip("有" + jdVal + "的人赞同你的观点");
		});

		var pageNo = 1;
		var pageSize = 20;
		searchGuess(pageNo, pageSize);

	

		//分页查询邀请的专家  
		function searchGuess(pageNo, pageSize) {
			
					$.post(
							"${ctx}/live/guessData.html",
							{
								pageNo : pageNo,
								pageSize : pageSize
							},
							function(data) {
								var data = eval("(" + data + ")");
								var length = data.array.length;
								var result = '';
								result += "<ul class='nowContent'>";

								
								for (var i = 0; i < length; i++) {
									var arrText = [];
									if (data.array[i].state == 0) {//未结算的
										if (data.array[i].type == 0) {//上午盘
											arrText
													.push("<li class='guess-article amBtn'>");
										}
										if (data.array[i].type == 1) {//下午盘
											arrText
													.push("<li class='guess-article pmBtn'>");
										}
 
											arrText.push("<h4>"
													+ data.array[i].createTime
													+ "&nbsp;&nbsp;大盘猜猜乐（"
													+ data.array[i].time
													+ "）</h4>");

										arrText.push("<div class='guess-zd'>");
										arrText.push("<div class='guess-zj'>");
										arrText.push("<div class='guess-zjbtn nowamZc'>");
										arrText.push("<i class='upji' onclick='payGuess("
														+ data.array[i].id
														+ ",1,"
														+ data.array[i].guessScore
														+ ")'></i>");
										arrText.push("</div>");
										arrText.push("<div class='guess-jd'>");
										arrText.push("<div class='vsjind'>");
										arrText
												.push("<div class='redbar'></div>");
										arrText.push("</div>");
										arrText
												.push("<span class='guessCol'>涨(奇数)：<i class='guessBtn'>"
														+ data.array[i].percent1
														+ "%</i></span>");
										arrText.push("</div>");
										arrText.push("</div>");

										arrText.push("<div class='guess-zj'>");
										arrText
												.push("<div class='guess-zjbtn nowamZc'>");
										arrText
												.push("<i class='upou' onclick='payGuess("
														+ data.array[i].id
														+ ",2,"
														+ data.array[i].guessScore
														+ ")'></i>");
										arrText.push("</div>");
										arrText.push("<div class='guess-jd'>");
										arrText.push("<div class='vsjind'>");
										arrText
												.push("<div class='redbar'></div>");
										arrText.push("</div>");
										arrText
												.push("<span class='guessCol'>涨(偶数)：<i class='guessBtn'>"
														+ data.array[i].percent2
														+ "%</i></span>");
										arrText.push("</div>");
										arrText.push("</div>");

										arrText.push("<div class='guess-zj'>");
										arrText
												.push("<div class='guess-zjbtn nowamZc'>");
										arrText
												.push("<i class='downji' onclick='payGuess("
														+ data.array[i].id
														+ ",3,"
														+ data.array[i].guessScore
														+ ")'></i>");
										arrText.push("</div>");
										arrText.push("<div class='guess-jd'>");
										arrText.push("<div class='vsjind'>");
										arrText
												.push("<div class='redbar'></div>");
										arrText.push("</div>");
										arrText
												.push("<span class='guessDownCol'>跌(奇数)：<i class='guessBtn'>"
														+ data.array[i].percent3
														+ "%</i></span>");
										arrText.push("</div>");
										arrText.push("</div>");

										arrText.push("<div class='guess-zj'>");
										arrText
												.push("<div class='guess-zjbtn nowamZc'>");
										arrText
												.push("<i class='downou' onclick='payGuess("
														+ data.array[i].id
														+ ",4,"
														+ data.array[i].guessScore
														+ ")'></i>");
										arrText.push("</div>");
										arrText.push("<div class='guess-jd'>");
										arrText.push("<div class='vsjind'>");
										arrText
												.push("<div class='redbar'></div>");
										arrText.push("</div>");
										arrText
												.push("<span class='guessDownCol'>跌(偶数)：<i class='guessBtn'>"
														+ data.array[i].percent4
														+ "%</i></span>");
										arrText.push("</div>");
										arrText.push("</div>");

										arrText.push("</div>");
										arrText
												.push("<div class='clear kongge'></div>");
										arrText.push("</li>");
									}
									if (data.array[i].state == 1) {//已结算的
										if (data.array[i].type == 0) {//上午盘
											arrText
													.push("<li class='guess-article amBtn'>");
										}
										if (data.array[i].type == 1) {//下午盘
											arrText
													.push("<li class='guess-article pmBtn'>");
										}
										arrText
												.push("<h4>"
														+ data.array[i].createTime
														+ "&nbsp;&nbsp;大盘猜猜乐（"
														+ data.array[i].time
														+ "）</h4>");

										arrText.push("<div class='guess-zd'>");
										if (data.array[i].acceptResult == 1
												|| data.array[i].acceptResult == 2) {
											arrText
													.push("<h5 class='zhangColr'><span>大盘指数："
															+ data.array[i].acceptFactor
															+ "</span><i class='guessUp'></i></h5>");
										}
										if (data.array[i].acceptResult == 3
												|| data.array[i].acceptResult == 4) {
											arrText
													.push("<h5 class='dieColr'><span>大盘指数："
															+ data.array[i].acceptFactor
															+ "</span><i class='guessDown'></i></h5>");
										}
										arrText
												.push("<div class='clear'></div>");
										arrText.push("<div class='guess-zj'>");
										arrText
												.push("<div class='guess-zjbtn'>");
										if (data.array[i].acceptResult != 1) {
											arrText
													.push("<i class='qupji'></i>");
										}
										if (data.array[i].acceptResult == 1) {
											arrText
													.push("<i class='upji'></i>");
											arrText
													.push("<i class='guess-right'></i>");
										}
										arrText.push("</div>");
										arrText.push("<div class='guess-jd'>");
										arrText.push("<div class='vsjind'>");
										arrText
												.push("<div class='redbar'></div>");
										arrText.push("</div>");
										arrText
												.push("<span class='guessCol'>涨(奇数)：<i class='guessBtn'>"
														+ data.array[i].percent1
														+ "%</i></span>");
										arrText.push("</div>");
										arrText.push("</div>");

										arrText.push("<div class='guess-zj'>");
										arrText
												.push("<div class='guess-zjbtn'>");
										if (data.array[i].acceptResult != 2) {
											arrText
													.push("<i class='qupou'></i>");
										}
										if (data.array[i].acceptResult == 2) {
											arrText
													.push("<i class='upou'></i>");
											arrText
													.push("<i class='guess-right'></i>");
										}
										arrText.push("</div>");
										arrText.push("<div class='guess-jd'>");
										arrText.push("<div class='vsjind'>");
										arrText
												.push("<div class='redbar'></div>");
										arrText.push("</div>");
										arrText
												.push("<span class='guessCol'>涨(偶数)：<i class='guessBtn'>"
														+ data.array[i].percent2
														+ "%</i></span>");
										arrText.push("</div>");
										arrText.push("</div>");

										arrText.push("<div class='guess-zj'>");
										arrText
												.push("<div class='guess-zjbtn'>");
										if (data.array[i].acceptResult != 3) {
											arrText
													.push("<i class='qdownji'></i>");
										}
										if (data.array[i].acceptResult == 3) {
											arrText
													.push("<i class='downji'></i>");
											arrText
													.push("<i class='guess-right'></i>");
										}
										arrText.push("</div>");
										arrText.push("<div class='guess-jd'>");
										arrText.push("<div class='vsjind'>");
										arrText
												.push("<div class='redbar'></div>");
										arrText.push("</div>");
										arrText
												.push("<span class='guessDownCol'>跌(奇数)：<i class='guessBtn'>"
														+ data.array[i].percent3
														+ "%</i></span>");
										arrText.push("</div>");
										arrText.push("</div>");

										arrText.push("<div class='guess-zj'>");
										arrText
												.push("<div class='guess-zjbtn nowamZc'>");
										if (data.array[i].acceptResult != 4) {
											arrText
													.push("<i class='qdownou'></i>");
										}
										if (data.array[i].acceptResult == 4) {
											arrText
													.push("<i class='downou'></i>");
											arrText
													.push("<i class='guess-right'></i>");
										}
										arrText.push("</div>");
										arrText.push("<div class='guess-jd'>");
										arrText.push("<div class='vsjind'>");
										arrText
												.push("<div class='redbar'></div>");
										arrText.push("</div>");
										arrText
												.push("<span class='guessDownCol'>跌(偶数)：<i class='guessBtn'>"
														+ data.array[i].percent4
														+ "%</i></span>");
										arrText.push("</div>");
										arrText.push("</div>");

										arrText.push("</div>");
										arrText
												.push("<div class='clear kongge'></div>");
										arrText.push("</li>");
									}
									if ((i + 1) % 2 == 0 && (i + 1) < length) {
										arrText.push("</ul>");
										arrText.push("<ul class='nowContent'>");
									}
									result += arrText.join('');
								}
								result += "</ul>";
								$(".guess-zdBody").append(result);
								//进度条
								$(".guessBtn").each(
										function() {
											var val = $(this).text();
											$(this).parent().prev().find(
													".redbar").width(val);
										});
								//向左向右滑动
								jQuery(".guessSlds").slide({
									mainCell : ".guess-zdBody",
									effect : "left",
									autoPlay : false,
									pnLoop : false,
									easing : "easeInBack"
								});
							})
		}

		function payGuess(marketId, guessType, guessScore) {
		var thisId=['upji','upou','downji','downou'];
			//$("."+(thisId[guessType-1])).css('background','url(../images/guessZjClick.png)');
			//setTimeout(function(){
			//$("."+(thisId[guessType-1])).css('background','url(../images/guessZj.png)');
			//},500);
			$.post("${ctx}/live/isClose.html", {
				marketId : marketId
			}, function(data) {
			
				if (data == "-1") {
					layer.open({
						content : "本期竞猜已封盘！<br/>（参与时间：00:00-09:15）",
						btn : [ "确定" ],
						shadeClose : false,
						yes : function() {
							layer.closeAll();
						}
					});
					return false;
				}
				if (data == "-2") {
					layer.open({
						content : "本期竞猜已封盘！<br/>（参与时间：00:00-13:00）",
						btn : [ "确定" ],
						shadeClose : false,
						yes : function() {
							layer.closeAll();
						}
					});
					return false;
				}
				layer.open({
					content : "竞猜本期活动每次需要支付" + guessScore + "积分！",
					btn : [ "确定", "取消" ],
					shadeClose : false,
					yes : function() {
						$.post("${ctx}/live/payGuess.html", {
							marketId : marketId,
							guessType : guessType
						}, function(data) {
							if (data == "-2") {
								layer.open({
									content : "未登录",
									btn : [ "确定" ],
									shadeClose : false,
									yes : function() {
										layer.closeAll();
									}
								});
								location.href = "${ctx}/live/guess.html";
								return false;
							}
							if (data == "-3") {
								layer.open({
									content : "您的积分不足！",
									btn : [ "确定" ],
									shadeClose : false,
									yes : function() {
										layer.closeAll();
									}
								});
								return false;
							}
							layer.open({
								content : "竞猜成功！",
								btn : [ "确定" ],
								shadeClose : false,
								yes : function() {
									layer.closeAll();
									location.href = location.href;
								}
							});
						})
					}
				})
			})
		}
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