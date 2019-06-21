$(document).ready(function () {
    getConsumeRecord();
});
function getConsumeRecord() {

    getRequest(
        '/trans/getByUserId/'+sessionStorage.getItem('id'),
        function (res) {
            for(var i=0;i<res.content.length;i++){
                res.content[i].time=splitTimeFormat(res.content[i].time);
            }
            renderUserConsumeList(res.content);
            console.log("成功！");
        },
        function (error) {
            console.log("失败！");
            alert(error);
        }
    )
}
function renderUserConsumeList(list) {
    $("#order-list").empty();
    var userConsumeDomstr='';
    list.forEach(function (consumeItem) {
        if(consumeItem.uses!="0"){
            var consumeMode="";
            var uses="";
            if(consumeItem.method==="vip"){
                consumeMode="会员卡支付";
            } else{
                consumeMode="银行卡支付(银行卡号):"+consumeItem.method;
            }
            if(consumeItem.uses=="-1"){
                uses="买卡";
                userConsumeDomstr+=
                    '<div style="margin-bottom: 20px">'+
                    '<div>'+'<span>'+'消费金额:'+consumeItem.amount+"元"+'</span>'+'</div>'+
                    '<div>'+'<span>'+'消费方式:'+consumeMode+'</span>'+'</div>'+
                    '<div>'+'<span>'+'消费用途:'+uses+'</span>'+'</div>'+
                    '<div>'+'<span>'+'消费时间:'+consumeItem.time+'</span>'+'</div>'+
                    '</div>'
            }else{
                uses="买票（电影票ID）："+consumeItem.uses;
                userConsumeDomstr+=
                    '<div style="margin-bottom: 20px">'+
                    '<div>'+'<span>'+'消费金额:'+consumeItem.amount+"元"+'</span>'+'</div>'+
                    '<div>'+'<span>'+'消费方式:'+consumeMode+'</span>'+'</div>'+
                    '<div>'+'<span>'+'消费用途:'+uses+'</span>'+'</div>'+
                    '<div>'+'<span>'+'消费时间:'+consumeItem.time+'</span>'+'</div>'+
                    "<button type='button' data-target='#consumeModal' data-backdrop='static' data-toggle='modal' class='btn' id='"+consumeItem.id+"' onclick='viewDetails(\""+consumeItem.uses+"\")'><span>详情</span></button>"+
                    '</div>'
            }
        }
    });
    $("#order-list").append(userConsumeDomstr);
}
function splitTimeFormat(date) {
    time1=date.split("T")[0];
    time2=date.split("T")[1].split(".")[0];
    return time1+" "+time2;
}
function viewDetails(uses){
    console.log(uses);
    var movieDetailsDomStr="";
    getRequest(
        '/ticket/get/details/'+uses,
        function (res) {
            console.log(res.content);
            console.log("获取成功！");
            movieDetailsDomStr+="电影名称："+res.content.movieName+"<br>"+
                "电影开始放映时间："+splitTimeFormat(res.content.startTime)+"<br>"+
                "电影结束放映时间："+splitTimeFormat(res.content.endTime)+"<br>"+
                "座位信息：";
            for(var i=0;i<res.content.seats.length;i++){
                movieDetailsDomStr+=res.content.seats[i].row+"排"+res.content.seats[i].column+"座 "
            }
            console.log(movieDetailsDomStr);
            $("#movie-details").html(movieDetailsDomStr);
        }
    );
}