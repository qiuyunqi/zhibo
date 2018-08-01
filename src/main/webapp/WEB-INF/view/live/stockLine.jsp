<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${ctx}/css/mobile.css?v=1.0.3"></link>
<script src="${ctx}/js/jquery-1.11.1.min.js"></script>
<!-- k线图 -->
<div class="guessLine">
	<!-- 指数相关 -->
	<div class="guess-stockInfo">
		<div class="guess-kLineTit">
			<span>上证综合指数</span>
			<span id="nowDate">2017/02/08/三 14:28</span>
			<span id="curPrice">价 <span class="zhangColr">3146.58</span><i class='guessUp'></i></span>
			<span id="tradeVol">量 11.83万</span>
			<span id="chanageRate">幅 <span class="dieColr">+ 0.23%</span><i class='guessUp'></i></span>
		</div>
	<!-- k线图 -->
	<div class="stockLine-content">
		<div class="clear"></div>
		<div class="stock-lineBody">
		<!-- 行情图 -->
			<div class="stock-linelist">
				<!-- 分时 -->
				<div class="lineInfo">
					<div class="lineInfo">
						<div id="content">
        					<div id="main">
        					</div>
        <!-- <div id="main2">
        </div> -->
        
    </div>
					</div>
					<!-- <div class="container" id="container"></div> -->
				</div>
				<!-- 覆盖层时间 -->
				<!-- <div class="datetip">
					<span class="datetip-nine">09:30</span>
					<span class="datetip-eleven">11:00</span>
					<span class="datetip-one">13:00</span>
					<span class="datetip-three">15:00</span>
				</div> -->
				<!-- 分时 end-->
			</div><!-- 行情图end -->
		</div>
	</div>
</div>
<script type="text/javascript">
$(function(){
	getData();
	getSingleData();
	
	setInterval(function () {
		getData();
	}, 30000);
	
	setInterval(function () {
		getSingleData();
	}, 10000);
	$(".upji").click(function(){
	console.log(123);
});
}); 

function getData() {
		$.get("${ctx}/live/getStockData.html", function(result) {
			if (result.success == 1) {
				refreshEcharts(result.IndShortName+'('+result.indcode+')',result.list);
			}else{
				refreshEcharts('',[]);
			}
		}, "json");
	}
	
function getSingleData() {
		$.get("${ctx}/live/getStockData1.html", function(result) {
			if (result.success == 1) {
				$("#nowDate").html(result.nowDate);
				//涨class=“zhangColr”，i的class=guessUp，跌时class='dieColr',i的class='guessDown'
				if (result.curFalg == 1) {
					$("#curPrice").html("价 "+"<span class='zhangColr'>"+result.curPrice.toFixed(2)+"</span><i class='guessUp'></i>");
				} else {
					$("#curPrice").html("价 "+"<span class='dieColr'>"+result.curPrice.toFixed(2)+"</span><i class='guessDown'></i>");
				}
				$("#tradeVo	l").html("量 "+result.tradeVol);
				result.changeRate=((result.changeRate.substring(0,result.changeRate.length-1))*1).toFixed(2)+'%';
				if (result.changeFalg == 1) {
					$("#chanageRate").html("幅 "+"<span class='zhangColr'>"+result.changeRate+"</span><i class='guessUp'></i>" );
				} else {
					$("#chanageRate").html("幅 "+"<span class='dieColr'>"+result.changeRate+"</span><i class='guessDown'></i>" );
				};
			}
			
		}, "json");
	}
</script>
<script src="${ctx}/js/jquery-2.1.4.min.js"></script>
<script src="${ctx}/js/echarts.js"></script>
<script src="${ctx}/js/kline.js"></script>