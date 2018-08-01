<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div id="hhead">
	<div class="hhead">
	   <span class="headTxt">客服热线：010-53320537&nbsp;&nbsp;服务时间：9:00 - 18:00</span> 
	  <ul class="navind">
	  <!-- 登录的之后显示用户名以及退出-->
		  <c:if test="${!empty fuUser}">
		  <li class="navli"><a href="javascript:void(0)"  style="color: #f96900;" onclick="logout()">退出</a></li>
		   <li class="navli allbd" id="allbd">
		  	 <span class="nh">欢迎您，</span>
		  	 	<a id="nickName" class="pptv" href="javascript:void(0)">${empty fuUser.nickName?'佚名':fuUser.nickName}</a>
		  	 <!-- 弹窗 -->
			  	 <div class="topTc">
			  	 	<div class="informationTop meTip">
						<div class="inforBod">
							<div class="inforBody-gz">
								<div class="information-tox">
									<c:if test="${empty fuUser}">
									<img src="${ctx}/web/images_qiDa/defTx.jpg" />
									</c:if>
									<c:if test="${not empty fuUser}">
									<img src="${empty fuUser.userAvatar?'../images_qiDa/tx.png':fuUser.userAvatar}" />
									</c:if>
								</div>
							</div>
							<div class="inforBody-text">
								<div class="chartNa defaultCol"><a href="${ctx}/web/ai/meindex.html">${empty fuUser.nickName?'佚名':fuUser.nickName }</a></div>
								<div class="chartName-jifen smalSize">余额：
								<c:if test="${!empty fuUser.accountBalance && fuUser.accountBalance>1000000}">
									<a title="${empty fuUser.accountBalance?0:fuUser.accountBalance}" href="${HHR_PREFIX}/user_center/centerIndex.htm">
										<fmt:formatNumber value="${fuUser.accountBalance/1000000+0.0001}" pattern="#,###,##0.00"/>百万
									</a>
								</c:if>	
								<c:if test="${empty fuUser.accountBalance || fuUser.accountBalance<1000000}">
									<a href="${HHR_PREFIX}/user_center/centerIndex.htm">
										<fmt:formatNumber value="${empty fuUser.accountBalance?0:fuUser.accountBalance}" pattern="#,###,##0.00"/>元 
									</a>	
								</c:if>
								&nbsp;&nbsp;<a class="timony" href="${HHR_PREFIX}/user_draw_money/drawMoney.htm" target="_blank">提现</a></div>
								<div class="chartName-jifen smalSize">积分：<a href="${QIDIAN_PREFIX}/web/ai/exchange.html"><fmt:formatNumber value="${empty fuUser.qidaIntegral?0:fuUser.qidaIntegral}" pattern="#,###,##0.00"/></a></div>
							</div>
							<i class="card-top"></i>
						</div>
					</div>
				</div>
		<!-- 弹窗end -->
			</li>
		  </c:if>
		  <!-- 未登录的时候显示为空 -->
		  <c:if test="${empty fuUser}">
			  <li class="navli"></li>
		  </c:if>
		</ul>
	</div>
</div>
<div class="clear"></div>
<div id="hmenu">
  <div class="hmenu">
    <div class="hlogo">
		<a href="javascript:void(0);">
		    <img src="../images/logo.png" title="首页"/>
		</a>
    </div>
    <ul class="hmenuul">
    	<li><a class="zhiboNav" href="${ZHIBO_PREFIX}/live/index">直播</a></li>
    	<li><a class="guessNav" href="${ZHIBO_PREFIX}/live/guess.html">大盘猜猜乐</a></li>
    </ul>
  </div>
</div>
<!-- 弹窗 财经日历 -->
<div class="dialog modal" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="fullPage"></div>
	<div class="data">
		 <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
	        <h3 id="myModalLabel">财经日历</h3>
	      </div>
	      <!-- 日历列表 -->
	      <div class="modal-body">
			<ul class="dataInfo">
				<li><span class="dataTime">23:24</span><p>白宫发言人舒尔茨：美国与菲律宾的关系可追溯到几十年前白宫发言人舒尔茨：美国与菲律宾的关系可追溯到几十年前白宫发言人舒尔茨：美国与菲律宾的关系可追溯到几十年前白宫发言人舒尔茨：美国与菲律宾的关系可追溯到几十年前</p></li>
				<li><span class="dataTime">23:24</span><p>白宫发言人舒尔茨：美国与菲律宾的关系可追溯到几十年前</p></li>
			</ul>
		</div>
	</div>
</div>
<!-- 弹窗喊单提醒 -->
<div class="dialog modal" id="tips" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
	<div class="fullPage"></div>
	<div class="data">
		 <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
	        <h3 id="myModalLabel1">喊单提醒</h3>
	      </div>
	      <!-- 登陆后提醒内容 (需要做判断)-->
		      <div class="modal-body clasTab">
		      	<table class="classLs" cellpadding="0" cellspacing="0" width="100%" border="0">
		      		<tr class="grey">	
		      			<th>单号 </th>
		      			<th>开仓时间</th>
		      			<th>类型</th>
		      			<th>仓位</th>
		      			<th>商品</th>
		      			<th>开仓价</th>
		      			<th>加仓价</th>
		      			<th>减仓价</th>
		      			<th>均价</th>
		      			<th>止损价</th>
		      			<th>止盈价</th>
		      			<th>平仓时间</th>
		      			<th>平仓价</th>
		      			<th>获利点数</th>
		      			<th>分析师</th>
		      		</tr>
		      		<tr>
		      			<td>8:30</td>
		      			<td>沪深指数</td>
		      			<td>沪深指数</td>
		      			<td>沪深指数</td>
		      			<td>沪深指数</td>
		      			<td>沪深指数</td>
		      			<td>沪深指数</td>
		      			<td>沪深指数</td>
		      			<td></td>
		      			<td></td>
		      			<td></td>
		      			<td></td>
		      			<td></td>
		      			<td></td>
		      			<td></td>
		      		</tr>
		      	</table>
			</div>
	      <!-- 未登陆时提醒内容 (需要做判断是否登陆)-->
		      <div class="modal-body">
		      	<p class="tipsTxt">您没有此权限，请联系客服</p>
			</div>
	</div>
</div>
<!-- 弹窗课程表提醒 -->
<div class="dialog modal" id="classList" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2" aria-hidden="true">
	<div class="fullPage"></div>
	<div class="data">
		 <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
	        <h3 id="myModalLabel2">课程表</h3>
	      </div>
	      <!-- 课程表列表 -->
		      <div class="modal-body clasTab">
		      	<table class="classLs" cellpadding="0" cellspacing="0" width="100%" border="0">
		      		<tr class="grey">	
		      			<th width="16%">上课时间</th>
		      			<th width="12%">星期一</th>
		      			<th width="12%">星期二</th>
		      			<th width="12%">星期三</th>
		      			<th width="12%">星期四</th>
		      			<th width="12%">星期五</th>
		      			<th width="12%">星期六</th>
		      			<th width="12%">星期日</th>
		      		</tr>
		      		<tr>
		      			<td>8:30</td>
		      			<td>沪深指数</td>
		      			<td>沪深指数</td>
		      			<td>沪深指数</td>
		      			<td>沪深指数</td>
		      			<td>沪深指数</td>
		      			<td>沪深指数</td>
		      			<td>沪深指数</td>
		      		</tr>
		      	</table>
			</div>
	</div>
</div>
<!-- 弹窗在线开户提醒 -->
<div class="dialog modal" id="openStock" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3" aria-hidden="true">
	<div class="fullPage"></div>
	<div class="data">
		 <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
	        <h3 id="myModalLabel3">在线开户</h3>
	      </div>
	      <!-- 在线开户 -->
	      <div class="modal-body stockForm">
		      <div class="biaodan zhuce_tbl">
		          <form id="stockForm">
		            <table class="stcoFom" width="100%" border="0" cellspacing="0" cellpadding="0">
						<tbody>
						    <tr>
						      <td><div class="number">开户姓名：</div><div class="wbk" ><input name="openUser" id="openUser" type="text" placeholder="开户姓名"></div></td>
						      <td><div class="biaodan_font onShow" id="phoneTip" style="width: 250px;padding-left: 5px">请输入开户姓名<a href="javascript:void(0);" class=" wenti" original-title="为了你的账号安全，只能绑定开户姓名本人的交易账号。获取更多帮助，请致电超级合伙人400-0320-898（010-59231688）"></a></div></td>
						    </tr>
						    <tr>
						      <td><div class="number">开户券商：</div><div class="wbk" ><input name="openEquity" id="openEquity" type="text" placeholder="开户劵商"></div></td>
						      <td ><div class="onShow" id="nickNameTip"  style="width: 250px;padding-left: 15px">请输入开户券商</div></td>
						    </tr>
						    <tr>
						      <td><div class="number">营业部：</div><div class="wbk" ><input name="salesDept" id="salesDept" type="text" placeholder="营业部"></div></td>
						      <td ><div class="fl biaodan_font onShow" id="passwordTip"  style="width: 250px;padding-left: 5px">请输入营业部名称</div></td>
						    </tr>
						    <tr>
						      <td><div class="number">资金账号：</div><div class="wbk" ><input name="capitalAccount" id="capitalAccount" type="text" placeholder="资金账号"></div></td>
						      <td ><div class="fl biaodan_font onShow" id="repasswordTip"  style="width: 250px;padding-left: 5px">请输入资金账号</div></td>
						    </tr>
						    <tr>
						      <td><div class="number">账户类型：</div>
						      <select name="accountType" id="accountType" style="width:340px;height:30px;">
						      	<option value="1">普通账户</option>
						      	<option value="2">融资融券</option>
						      	<option value="3">信用账户</option>
						      </select></td>
						      <td><div class="onShow" id="invitationCodeTip"  style="width: 250px;padding-left: 15px">请选择账户类型</div></td>
						    </tr>
						    <tr>
						      <td><div class="number">交易密码：</div><div class="wbk" ><input name="capitalPassword" id="capitalPassword" type="password" placeholder="交易密码"></div></td>
						      <td><div class="onShow" id="phoneCodeTip"  style="width: 250px;padding-left: 15px">请输入交易密码</div></td>
						    </tr>
						  </tbody>
						</table>
					</form>
		          </div>
		        <div class="jiaoyibtn">
				  	<a href="javascript:void(0)" class="next">提交</a>
				</div> 
		  </div>
	</div>
</div>
<script src="${ctx}/js/jquery-1.11.1.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script type="text/javascript">
//弹窗口close按钮
	$(".close").click(function(){
		$(this).parent().parent().parent().css("display","none");
	});
	
$(document).ready(function(){
	$("#nickName").mouseover(function(){
		$(".topTc").show();
	});
	$("#nickName").mouseout(function(){
		$(".topTc").hide();
	});
	$(".topTc").mouseover(function(){
		$(".topTc").show();
	});
	$(".topTc").mouseout(function(){
		$(".topTc").hide();
	});
});

</script>
