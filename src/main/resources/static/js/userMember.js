var isBuyState = true;
var vipCardId;
var currentCardType;
var allCardTypes;
var currentCardName;

$(document).ready(function () {
    getVIP();
    getCoupon();
});


function getVIP() {
    getRequest(
        '/vip/' + sessionStorage.getItem('id') + '/get',
        function (res1) {
            getRequest(
                '/viptype/getAllType',
                function (res) {
                    console.log(res.content);
                    allCardTypes=res.content;
                    if (res1.success) {
                        // 是会员
                        vipCardId = res1.content.id;
                        currentCardType=res1.content.cardType;
                        allCardTypes.forEach(function (type) {
                            if (type.id===currentCardType){
                                currentCardName=type.name;
                            }
                        });
                        $("#member-card").css("visibility", "visible");
                        $("#member-card").css("display", "");
                        $("#nonmember-card").css("display", "none");

                        $("#member-id").text(vipCardId);
                        $("#member-name").text(currentCardName);
                        $("#member-balance").text("¥" + res1.content.balance.toFixed(2));
                        $("#member-joinDate").text(res1.content.joinDate.substring(0, 10));


                    } else {
                        // 非会员
                        $("#member-card").css("display", "none");
                        $("#nonmember-card").css("display", "");
                        currentCardType=1;
                    }
                    getVIPInfo();
                },
                function (error) {
                    alert(error);
                });

        },
        function (error) {
            alert(error);
        });

}

function getVIPInfo() {
    getRequest(
        '/viptype/getVIPInfo?typeId='+currentCardType,
        function (res) {
            if (res.success) {
                $("#member-buy-price").text(res.content.price);
                $("#member-buy-description").text("充值优惠：" + res.content.description + "。永久有效");

                $("#member-description").text(res.content.description);
            } else {
                alert(res.content);
            }

        },
        function (error) {
            alert(error);
        });
}

function buyClick() {
    clearForm();
    $('#buyModal').modal('show');
    $("#userMember-amount-group").css("display", "none");
    isBuyState = true;
}

function chargeClick() {
    clearForm();
    $('#buyModal').modal('show');
    $("#userMember-amount-group").css("display", "");
    isBuyState = false;
}

function clearForm() {
    $('#userMember-form input').val("");
    $('#userMember-form .form-group').removeClass("has-error");
    $('#userMember-form p').css("visibility", "hidden");
}

function confirmCommit() {
    if (validateForm()) {
        postRequest(
            '/bank/login',
            {
                accountNumber:$('#userMember-cardNum').val(),
                password:$('#userMember-cardPwd').val()
            },
            function (res) {
                if(res.success){
                    if (isBuyState) {
                        postRequest(
                            '/vip/add?userId=' + sessionStorage.getItem('id')+'&cardId='+res.content.id,
                            null,
                            function (res) {
                                $('#buyModal').modal('hide');
                                alert("购买会员卡成功");
                                getVIP();
                            },
                            function (error) {
                                alert(error);
                            });
                    } else {
                        postRequest(
                            '/vip/charge/'+res.content.id,
                            {vipId: vipCardId, amount: parseInt($('#userMember-amount').val())},
                            function (res) {
                                $('#buyModal').modal('hide');
                                alert("充值成功");
                                console.log("当前卡等级"+res.content.cardType);
                                if (currentCardType!==res.content.cardType){
                                    alert("恭喜升级！");
                                }
                                getVIP();
                            },
                            function (error) {
                                alert(error);
                            });
                    }
                }else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        )


        // if ($('#userMember-cardNum').val() === "123123123" && $('#userMember-cardPwd').val() === "123123") {
        //     if (isBuyState) {
        //         postRequest(
        //             '/vip/add?userId=' + sessionStorage.getItem('id'),
        //             null,
        //             function (res) {
        //                 $('#buyModal').modal('hide');
        //                 alert("购买会员卡成功");
        //                 getVIP();
        //             },
        //             function (error) {
        //                 alert(error);
        //             });
        //     } else {
        //         postRequest(
        //             '/vip/charge',
        //             {vipId: vipCardId, amount: parseInt($('#userMember-amount').val())},
        //             function (res) {
        //                 $('#buyModal').modal('hide');
        //                 alert("充值成功");
        //                 console.log("当前卡等级"+res.content.cardType);
        //                 if (currentCardType!==res.content.cardType){
        //                     alert("恭喜升级！");
        //                 }
        //                 getVIP();
        //             },
        //             function (error) {
        //                 alert(error);
        //             });
        //     }
        // } else {
        //     alert("银行卡号或密码错误");
        // }
    }
}

function validateForm() {
    var isValidate = true;
    if (!$('#userMember-cardNum').val()) {
        isValidate = false;
        $('#userMember-cardNum').parent('.form-group').addClass('has-error');
        $('#userMember-cardNum-error').css("visibility", "visible");
    }
    if (!$('#userMember-cardPwd').val()) {
        isValidate = false;
        $('#userMember-cardPwd').parent('.form-group').addClass('has-error');
        $('#userMember-cardPwd-error').css("visibility", "visible");
    }
    if (!isBuyState && (!$('#userMember-amount').val() || parseInt($('#userMember-amount').val()) <= 0)) {
        isValidate = false;
        $('#userMember-amount').parent('.form-group').addClass('has-error');
        $('#userMember-amount-error').css("visibility", "visible");
    }
    return isValidate;
}

function getCoupon() {
    getRequest(
        '/coupon/' + sessionStorage.getItem('id') + '/get',
        function (res) {
            if (res.success) {
                var couponList = res.content;
                var couponListContent = '';
                for (let coupon of couponList) {
                    couponListContent += '<div class="col-md-6 coupon-wrapper"><div class="coupon"><div class="content">' +
                        '<div class="col-md-8 left">' +
                        '<div class="name">' +
                        coupon.name +
                        '</div>' +
                        '<div class="description">' +
                        coupon.description +
                        '</div>' +
                        '<div class="price">' +
                        '满' + coupon.targetAmount + '减' + coupon.discountAmount +
                        '</div>' +
                        '</div>' +
                        '<div class="col-md-4 right">' +
                        '<div>有效日期：</div>' +
                        '<div>' + formatDate(coupon.startTime) + ' ~ ' + formatDate(coupon.endTime) + '</div>' +
                        '</div></div></div></div>'
                }
                $('#coupon-list').html(couponListContent);

            }
        },
        function (error) {
            alert(error);
        });
}

function formatDate(date) {
    return date.substring(5, 10).replace("-", ".");
}