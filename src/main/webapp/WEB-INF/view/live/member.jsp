<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 在线会员/我的客服 -->
	<div class="conceptLeft">
		<div id="memTip" class="memTip acv"><span>在线会员</span></div>
		<div class="afterDiv Online">
			<p>在线会员</p>
			<!-- 在线会员 -->
			<ul class="afterDiv-ul"></ul>
		</div>
		<div class="memberLft">
			<img src="${ctx}/images/leftTop.png" />
			<!-- 在线会员/我的客服 -->
			<div class="member">
				<!-- tab标签 -->
				<div class="memberTit">
					<ul class="discussList textCen disLis">
						<li class="discussLi  textCen disListAct">在线会员<br/>(<span id="memberCount">0</span>)</li>
						<li class=" discussLi  textCen disLiss">我的客服<br/>(0)</li>
					</ul>
				</div>
				<!-- 会员详情 -->
				<div class="memberInfo">
					<!-- 在线会员 -->
					<div class="members Online">
						<ul id="memberList">
						</ul>
					</div>
					<!-- 我的客服-->
					<div class="members mine">
						<!-- 客服 -->
						<div class="kefu">
							<img src="${ctx}/images/tx.png"><span>admin</span><i class="adminIco"></i>
						</div>
						<!-- 在线私聊 -->
						<!-- <div class="chat">
							<a href="javascript:void(0)" title="我的私聊" data-toggle="modal" data-target="#private">在线私聊</a>
							<a><i></i>QQ交谈</a>
						</div> -->
						<!-- 客服简介 -->
						<div class="kefuInfo">
							<p><a class="kefuQq" target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=2211247320&site=qq&menu=yes">QQ在线客服</a></p>
							<!-- <p class="telephone">电话：<span>13501861044</span></p> -->
							<p>客服简介： 昵称admin，是小合网络科技（上海）有限公司的客户经理，QQ：，电话：13501861044 </p>
						</div>
					</div>
				</div>
			</div>
			
		</div>
	</div>
<script type="text/javascript">
	//沪深和美股切换
	var titLis = $(".stockTit span");
	var ulLis = $(".stockInfo ul");
  	titLis.click(function(){
     var index = $(this).index();
     titLis.removeClass();
     $(this).attr("class","stockHactiv");
     ulLis.css('display','none');
     ulLis.eq(index).css('display','block');
  });
  
	//在线会员和我的客服切换
	var memberLis = $(".discussLi");
	var members = $(".members");
	memberLis.click(function(){
     var index = $(this).index();
     memberLis.attr("class","discussLi  textCen disLiss");
     $(this).attr("class","discussLi  textCen disListAct");
     members.css('display','none');
     members.eq(index).css('display','block');
  });
  
		$("#memTip").click(function(){
		var txt = $(this).attr("class");
		var memberHml = $("#memberList").html();
			if(txt == "memTip acv"){
				$(this).attr("class","memTip");
				$(this).css("margin-left","235px");
				$(".afterDiv-ul").append(memberHml);
				$(".afterDiv").show();
				
			}else{
				$(this).attr("class","memTip acv");
				$(this).css("margin-left","0");
				$(".afterDiv").hide();
				$(".afterDiv-ul").html("");
			}
		});
</script>