var currentStrategyId = 0;
var currentStrategies=[];

$(document).ready(function() {
    if (sessionStorage.getItem('role')=='manager') {
        $('#power').css('display','inline');
        $('.nav-user-container').empty();
        $('.nav-user-container').append('<img class="avatar-lg" src="/images/manager.png" />' +
            '            <p class="title">Manager</p>');
    }else {
        $('.nav-user-container').empty();
        $('.nav-user-container').append('<img class="avatar-lg" src="/images/defaultAvatar.jpg" />' +
            '            <p class="title">Admin</p>');
    }
    $('.avatar-lg').click(function () {
        confirm('确认要退出登录吗？') && postRequest('/logout',null,function (res) {
            window.location.href='/index';
        });
    });

    getStrategies();


//发布策略
    $("#strategy-form-btn").click(function () {
        if (parseFloat($("#vip-discount").val())>parseFloat($("#vip-target").val())) {
            alert("满减不能减到负值！");
        }else {
            postRequest(
                '/viptype/addType',
                {
                    name:$("#vip-name").val(),
                    chargeAmount:$("#vip-need").val(),
                    targetAmount:$("#vip-target").val(),
                    discountAmount:$("#vip-discount").val()
                },
                function (res) {
                    if(res.success){
                        $("#strategyModal").modal('hide');
                        getStrategies();
                    } else {
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            )}
    });

    $("#modify-strategy-form-btn").click(function () {
        if (parseFloat($("#vip-discount-modify").val())>parseFloat($("#vip-target-modify").val())) {
            alert("满减不能减到负值！");
        }else {
            console.log("准备update");
            postRequest(
                '/viptype/updateType',
                {
                    id:currentStrategyId,
                    name:$("#vip-name-modify").val(),
                    chargeAmount:$("#vip-need-modify").val(),
                    targetAmount:$("#vip-target-modify").val(),
                    discountAmount:$("#vip-discount-modify").val()
                },
                function (res) {
                    if(res.success){
                        console.log($("#vip-discount-modify").val());
                        getStrategies();
                        $("#modifyStrategyModal").modal('hide');
                    } else {
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            )}

    });

});

function getStrategies() {
    getRequest(
        '/viptype/getAllType',
        function (res) {
            currentStrategies = res.content;
            renderStrategies(currentStrategies);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
}

function getStrategy(id) {
    var currentStrategy;
    currentStrategies.forEach(function (strategy) {
        if (strategy.id==id){
            currentStrategy=strategy;
        }
    });
    $("#vip-name-modify").val(currentStrategy.name);
    $("#vip-need-modify").val(currentStrategy.chargeAmount);
    $("#vip-target-modify").val(currentStrategy.targetAmount);
    $("#vip-discount-modify").val(currentStrategy.discountAmount);
}

function renderStrategies(strategies) {
    $(".content-strategy").empty();
    strategies.sort(function (a,b) {
        return a.chargeAmount-b.chargeAmount;
    });
    var strategiesDomStr = "";
    strategies.forEach(function (strategy) {
        strategiesDomStr +=
            "<br />"+
            "<div class='strategy-container'>"+
            "   <div class='strategy-line'>"+
            "       <span class='time'><p class='shit'>"+"会员等级为<span class='beStrong'>"+strategy.name+"</span>的用户享受充<span class='beStrong'>"+strategy.targetAmount+"</span>元赠送<span class='beStrong'>"+strategy.discountAmount+"</span>元优惠。(累计充值需满<span class='beStrong'>"+strategy.chargeAmount+"</span>元)</p></span>"+
            "   </div>"+
            "   <div class=\"strategy-operations\">" +
            "       <button type=\"button\" class=\"btn btn-primary btn-modify\" id=\"modify"+strategy.id+"\" data-backdrop=\"static\" data-toggle=\"modal\" data-target=\"#modifyStrategyModal\" onclick='modifyStrategy("+strategy.id+")'><span>修 改</span></button>" +
            "       <button type=\"button\" class=\"btn btn-danger btn-delete\" id=\"delete"+strategy.id+"\" onclick='deleteStrategy("+strategy.id+")'><span>删 除</span></button>" +
            "   </div>"+
            "</div>";
    })
    $(".content-strategy").append(strategiesDomStr);
}



//修改策略
function modifyStrategy(id) {
    currentStrategyId=id;
    getStrategy(currentStrategyId);
}



function deleteStrategy(id) {
    currentStrategyId=id;
    if (confirm("确实要删除这条策略吗？")){
        postRequest(
            '/viptype/delTypeById?typeId='+currentStrategyId,
            {
                //typeId:currentStrategyId
            },
            function (res) {
                if(res.success){
                    getStrategies();
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        )
    }
}