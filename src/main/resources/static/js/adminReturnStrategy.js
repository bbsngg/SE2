var currentStrategyId = 0;


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
        if (parseFloat($("#strategy-start-time-input").val())<=parseFloat($("#strategy-end-time-input").val())) {
            alert("策略开始时间必须大于结束时间！");
        }else {
            postRequest(
                '/refund/add',
                {
                    startTime:$("#strategy-start-time-input").val(),
                    endTime:$("#strategy-end-time-input").val(),
                    percent:$("#strategy-rate-input").val()
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

    $("#strategy-form-btn-modify").click(function () {
        if (parseFloat($("#strategy-start-time-input").val())<=parseFloat($("#strategy-end-time-input").val())) {
            alert("策略开始时间必须大于结束时间！");
        }else {
            postRequest(
                '/refund/update',
                {
                    id:currentStrategyId,
                    startTime:$("#strategy-start-time-input-modify").val(),
                    endTime:$("#strategy-end-time-input-modify").val(),
                    percent:$("#strategy-rate-input-modify").val()
                },
                function (res) {
                    if(res.success){
                        getStrategies();
                        $("#strategyModifyModal").modal('hide');
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
        '/refund/all',
        function (res) {
            var strategies = res.content;
            renderStrategies(strategies);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
}

function getStrategy(id) {
    getRequest(
        '/refund/'+id,
        function (res) {
            var currentStrategy = res.content;
            $("#strategy-start-time-input-modify").val(currentStrategy.startTime);
            $("#strategy-end-time-input-modify").val(currentStrategy.endTime);
            $("#strategy-rate-input-modify").val(currentStrategy.percent);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
}

function renderStrategies(strategies) {
    $(".content-strategy").empty();
    var strategiesDomStr = "";
    strategies.sort(function (a,b) {
       return a.percent-b.percent;
    });
    strategies.forEach(function (strategy) {
        strategiesDomStr +=
            "<br />"+
            "<div class='strategy-container'>"+
            "   <div class='strategy-line'>"+
            "       <span class='time'><p class='shit'>"+"距影片放映<span class='beStrong'>"+strategy.startTime+"</span>时至<span class='beStrong'>"+strategy.endTime+"</span>时退票, 扣除费率为：<span class='beStrong'>"+100*strategy.percent+"%</span></p></span>"+
            "   </div>"+
            "   <div class=\"strategy-operations\">" +
            "       <button type=\"button\" class=\"btn btn-primary btn-modify\" id=\"modify"+strategy.id+"\" data-backdrop=\"static\" data-toggle=\"modal\" data-target=\"#strategyModifyModal\" onclick='modifyStrategy("+strategy.id+")'><span>修 改</span></button>" +
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
        deleteRequest(
            '/refund/delete',
            {
                id:currentStrategyId
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