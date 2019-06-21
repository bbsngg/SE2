var selectedSeats = [];
var scheduleId;
var order = {ticketId: [], couponId: 0};
var coupons = [];
var isVIP = false;
var useVIP = true;

//数据准备，包括：场次信息、座位信息（详见renderSchedule方法）
$(document).ready(function () {
    //scheduleId是点击购买按钮对应的那一场次电影
    scheduleId = parseInt(window.location.href.split('?')[1].split('&')[1].split('=')[1]);

    getInfo();

    function getInfo() {
        getRequest(
            '/ticket/get/occupiedSeats?scheduleId=' + scheduleId,
            function (res) {//res的内容实际是ScheduleWithSeatVO
                if (res.success) {
                    renderSchedule(res.content.scheduleItem, res.content.seats);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }
    window.onunload=function (e) {
        if ($('#order-state').css("")!=="none") {
            postRequest(
                '/ticket/cancel',
                {
                    ticketId:order.ticketId
                },
                function () {
                    // alert("gun");
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            )
        }

    };

    window.onbeforeunload=function (e) {
        if ($('#order-state').css("display")!=="none") {
            e.returnValue=("不买票就想走？");
        }
    };
});

//页面注入数据：影厅名称、电影票费用、开始截止日期、座位信息
function renderSchedule(schedule, seats) {
    $('#schedule-hall-name').text(schedule.hallName);
    $('#order-schedule-hall-name').text(schedule.hallName);
    $('#schedule-fare').text(schedule.fare.toFixed(2));
    $('#order-schedule-fare').text(schedule.fare.toFixed(2));
    $('#schedule-time').text(schedule.startTime.substring(5, 7) + "月" + schedule.startTime.substring(8, 10) + "日 " + schedule.startTime.substring(11, 16) + "场");
    $('#order-schedule-time').text(schedule.startTime.substring(5, 7) + "月" + schedule.startTime.substring(8, 10) + "日 " + schedule.startTime.substring(11, 16) + "场");

    var hallDomStr = "";
    //影厅的座位信息（选中、未选中）
    var seat = "";
    for (var i = 0; i < seats.length; i++) {
        var temp = "";
        for (var j = 0; j < seats[i].length; j++) {
            var id = "seat" + i + j;

            if (seats[i][j] == 0) {
                // 未选
                //为座位绑定点击事件，绑定id，例如seat00 seat01，以便seatClick调用
                temp += "<button class='cinema-hall-seat-choose' id='" + id + "' onclick='seatClick(\"" + id + "\"," + i + "," + j + ")'></button>";
            } else {
                // 已选中
                temp += "<button class='cinema-hall-seat-lock'></button>";
            }
        }
        seat += "<div>" + temp + "</div>";
    }

    //影厅名及对应座位信息（座位数量信息）
    var hallDom =
        "<div class='cinema-hall'>" +
        "<div>" +
        "<span class='cinema-hall-name'>" + schedule.hallName + "</span>" +
        "<span class='cinema-hall-size'>" + seats.length + '*' + seats[0].length + "</span>" +
        "</div>" +
        "<div class='cinema-seat'>" + seat +
        "</div>" +
        "</div>";
    hallDomStr += hallDom;

    $('#hall-card').html(hallDomStr);//购买界面显示可供选择的座位
}

//选座位的前端响应
function seatClick(id, i, j) {
    let seat = $('#' + id);
    //修改class改变点击后的效果
    if (seat.hasClass("cinema-hall-seat-choose")) {
        seat.removeClass("cinema-hall-seat-choose");
        seat.addClass("cinema-hall-seat");
        //点击未选择的座位就变成已选，并添加进入selectedSeats[]
        selectedSeats[selectedSeats.length] = [i, j]
    } else {
        seat.removeClass("cinema-hall-seat");
        seat.addClass("cinema-hall-seat-choose");
        //从selectedSeats[]中移除该位置
        selectedSeats = selectedSeats.filter(function (value) {
            return value[0] != i || value[1] != j;
        })
    }
    //选中的座位按顺序排列，按座位号升序排列，行号相同再考虑列号
    selectedSeats.sort(function (x, y) {
        var res = x[0] - y[0];
        return res === 0 ? x[1] - y[1] : res;
    });

    let seatDetailStr = "";
    if (selectedSeats.length == 0) {
        seatDetailStr += "还未选择座位"
        $('#order-confirm-btn').attr("disabled", "disabled")//没有选座位设置购买按钮不可点击
    } else {
        for (let seatLoc of selectedSeats) {//按selectedSeats的顺序显示在界面右侧
            seatDetailStr += "<span>" + (seatLoc[0] + 1) + "排" + (seatLoc[1] + 1) + "座</span>";
        }
        $('#order-confirm-btn').removeAttr("disabled");
    }
    $('#seat-detail').html(seatDetailStr);
}

//确认下单按钮绑定事件
function orderConfirmClick() {
    $('#seat-state').css("display", "none");//停留在当前页面，但内容模块改为order-state状态
    $('#order-state').css("display", "");

    // TODO:这里是假数据，需要连接后端获取真数据，数据格式可以自行修改，但如果改了格式，别忘了修改renderOrder方法
    var seats=[];
    for(var i=0;i<selectedSeats.length;i++){
        seats.push({
            rowIndex:selectedSeats[i][0],
            columnIndex:selectedSeats[i][1]
        })
    }
    postRequest(
        '/ticket/lockSeat',
        {
            userId:sessionStorage.getItem('id'),
            scheduleId:scheduleId,
            seats:seats
        },
        function (res) {//返回的responseVO的content应该是List<TicketVO>,添加删除更改都返回所修改的对象
            //可以考虑返回一个List<ticketWithCoupon>，这样可以得到ticketVOList和Coupon，

            //如果能返回一个schedule就能获得total
            var orderInfo = {
                "ticketVOList": res.content.ticketVOList,
                "total": res.content.total,
                "coupons": res.content.coupons,
                "activities": res.content.activities
            };

            renderOrder(orderInfo);
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );




    getRequest(
        '/vip/' + sessionStorage.getItem('id') + '/get',
        //获取用户的vip信息
        function (res) {
            isVIP = res.success;
            useVIP = res.success;
            if (isVIP) {
                $('#member-balance').html("<div><b>会员卡余额：</b>" + res.content.balance.toFixed(2) + "元</div>");
            } else {
                $("#member-pay").css("display", "none");
                $("#nonmember-pay").addClass("active");

                $("#modal-body-member").css("display", "none");
                $("#modal-body-nonmember").css("display", "");
            }
        },
        function (error) {
            alert(error);
        });
}

//判断是否用会员卡支付，支付界面响应
function switchPay(type) {
    useVIP = (type == 0); //判断是否用会员卡支付
    if (type == 0) {
        $("#member-pay").addClass("active");
        $("#nonmember-pay").removeClass("active");

        $("#modal-body-member").css("display", "");
        $("#modal-body-nonmember").css("display", "none");
    } else {
        $("#member-pay").removeClass("active");
        $("#nonmember-pay").addClass("active");

        $("#modal-body-member").css("display", "none");
        $("#modal-body-nonmember").css("display", "");
    }
}

//订单数据响应相关
function renderOrder(orderInfo) {
    // 所选电影票数量，排号列号相关
    var ticketStr = "<div>" + selectedSeats.length + "张</div>";
    for (let ticketInfo of orderInfo.ticketVOList) {
        ticketStr += "<div>" + (ticketInfo.rowIndex + 1) + "排" + (ticketInfo.columnIndex + 1) + "座</div>";
        order.ticketId.push(ticketInfo.id);
    }
    $('#order-tickets').html(ticketStr);

    // 电影票订单总价
    var total = orderInfo.total.toFixed(2);
    $('#order-total').text(total);
    $('#order-footer-total').text("总金额： ¥" + total);

    // 优惠券相关
    var couponTicketStr = "";
    if (orderInfo.coupons.length == 0) {
        $('#order-discount').text("优惠金额：无");
        $('#order-actual-total').text(" ¥" + total);
        $('#pay-amount').html("<div><b>金额：</b>" + total + "元</div>");
    } else {
        coupons = orderInfo.coupons;
        for (let coupon of coupons) {
            couponTicketStr += "<option>满" + coupon.targetAmount + "减" + coupon.discountAmount + "</option>"
        }
        $('#order-coupons').html(couponTicketStr);//优惠券下拉选择框
        changeCoupon(0);//默认是第一个优惠券，对应html页面的下拉框绑定了changeCoupon方法，能动态修改优惠券
    }
}

//响应切换优惠券
function changeCoupon(couponIndex) {
    order.couponId = coupons[couponIndex].id;
    $('#order-discount').text("优惠金额： ¥" + coupons[couponIndex].discountAmount.toFixed(2));
    var actualTotal = (parseFloat($('#order-total').text()) - parseFloat(coupons[couponIndex].discountAmount)).toFixed(2);
    $('#order-actual-total').text(" ¥" + actualTotal);
    $('#pay-amount').html("<div><b>金额：</b>" + actualTotal + "元</div>");//点击确认订单，立即支付后界面显示金额：**元
}

//确认支付按钮绑定事件
function payConfirmClick() {
    if (useVIP) {//直接用会员卡支付就不用输入银行卡号密码
        postPayRequest(0);
    } else {
        if (validateForm()) {
            postRequest(
                '/bank/login',
                {
                    accountNumber:$('#userBuy-cardNum').val(),
                    password:$('#userBuy-cardPwd').val()
                },
                function (res) {
                    if(res.success){
                        postPayRequest(res.content.id);//传一个银行卡id
                    }else{
                        alert(res.message)
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
            // if ($('#userBuy-cardNum').val() === "123123123" && $('#userBuy-cardPwd').val() === "123123") {
            //     postPayRequest();
            // } else {
            //     alert("银行卡号或密码错误");
            // }
        }
    }
}

// TODO:填空 提交订单
function postPayRequest(cardId) {
    $('#order-state').css("display", "none");
    $('#success-state').css("display", "");
    $('#buyModal').modal('hide');
    if (!useVIP){
        postRequest(
            '/ticket/buy/'+cardId,
            {
                ticketId:order.ticketId,
                couponId:order.couponId
            },
            function(res){
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        )
    }else {
        postRequest(
            '/ticket/vip/buy',
            {
                ticketId:order.ticketId,
                couponId:order.couponId
            },
            function(res){
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        )
    }
}

//支付时如果银行卡号或密码为空就会提示***不能为空
function validateForm() {
    var isValidate = true;
    if (!$('#userBuy-cardNum').val()) {
        isValidate = false;
        $('#userBuy-cardNum').parent('.form-group').addClass('has-error');
        $('#userBuy-cardNum-error').css("visibility", "visible");
    }
    if (!$('#userBuy-cardPwd').val()) {
        isValidate = false;
        $('#userBuy-cardPwd').parent('.form-group').addClass('has-error');
        $('#userBuy-cardPwd-error').css("visibility", "visible");
    }
    return isValidate;
}