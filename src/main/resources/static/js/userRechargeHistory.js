$(document).ready(function () {
    getUserRechargeHistory()
});
function getUserRechargeHistory() {
    getRequest(
        '/trans/getByUserId/'+sessionStorage.getItem('id'),
        function (res) {
            for(var i=0;i<res.content.length;i++){
                res.content[i].time=splitTimeFormat(res.content[i].time);
            }
            console.log(res.content);
            console.log("成功！");
            renderUserRechargeList(res.content);
        },
        function (error) {
            console.log("失败！");
            alert(error);
        }
    )
}
function renderUserRechargeList(list) {
    $("#recharge-list").empty();
    var userRechargeDomStr="";
    list.forEach(function (rechargeItem) {
        if(rechargeItem.uses=="0"){
            userRechargeDomStr+="<div style='margin-bottom: 20px'>"+
                "<div>"+"<span>"+"充值金额："+rechargeItem.amount+"</span>"+"</div>"+
                "<div>"+"<span>"+"充值银行卡卡号："+rechargeItem.method+"</span>"+"</div>"+
                "<div>"+"<span>"+"充值时间："+rechargeItem.time+"</span>"+"</div>"+
                "</div>";
        }
    });
    $("#recharge-list").append(userRechargeDomStr);
}
function splitTimeFormat(date) {
    time1=date.split("T")[0];
    time2=date.split("T")[1].split(".")[0];
    return time1+" "+time2;
}