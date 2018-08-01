/**
 * Created by admin on 2016/12/12.
 */
function prizelTip(message,url, priUrl, integral){
    var prize = "<div class='prizeTip'>" +
			        "<div class='prizetipContain'>" +
			        	"<div class='prizetipimg'>" +
			        	"<div class='prizeGif'></div>"+
			        	"</div>"+
			        	"<div class='prizetip-message'><span class='prizeSpan'>恭喜</span>"+ message +
			        		"<span class='prizeAgain'>您还剩下"+ integral +"积分</span>" +
			        	"</div>"+
			        	"<div class='prizetipBtn'>"+
			        		"<a class='prizetip-sure'>关闭</a>" +
			        		"<a class='prizetip-sure' href="+priUrl+">奖品管理</a>"+
			        	"</div>" +
			        	"<div class='prize-foot'>"+
			        		"<a href="+url+"><i class='prize-footI'></i><span>您还可以去大盘猜猜乐试试运气</span></a>"+
			        	"</div>"
			        "</div>" +
			    "</div>"   ;

    $("body").append(prize);

    $(".prizetip-sure").on('click',function(){
    	$(".prizeTip").remove();
    });
}
