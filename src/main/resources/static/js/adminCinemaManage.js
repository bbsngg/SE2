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

    var halls = [];
    var canSeeDate = 0;
    getCanSeeDayNum();
    getCinemaHalls();
    
    $("#hall-form-btn").click(function () {
        var name=$("#hall-name-input").val();
        var row=$("#hall-row-input");
        var column=$("#hall-column-input");
        var r = /^[1-9]?[0-9]$/;
        if(name===""||name===null){
            alert("请输入影厅名称！");
        }else if(!r.test(row.val())){
            alert("影厅行数输入不规范，请重新输入！");
        }else if(!r.test(column.val())){
            alert("影厅列数输入不规范，请重新输入！");
        }else{
            var formData={
                name:$("#hall-name-input").val(),
                row:$("#hall-row-input").val(),
                column:$("#hall-column-input").val()
            };
            postRequest(
                '/hall/add',
                formData,
                function (res) {
                    if(res.success){
                        getCinemaHalls();
                        $("#hallModal").modal('hide');
                    }else{
                        alert(res.message);
                    }
                },
                function (error) {
                    console.log(res);
                    alert(JSON.stringify(error));
                }
            )
        }
    });


    $('#hall-edit-form-btn').click(function () {
        var name=$("#hall-name-edit-input").val();
        var row=$("#hall-row-edit-input");
        var column=$("#hall-column-edit-input");
        var r = /^[1-9]?[0-9]$/;
        if(name===""||name===null){
            alert("请输入影厅名称！");
        }else if(!r.test(row.val())){
            alert("影厅行数输入不规范，请重新输入！");
        }else if(!r.test(column.val())){
            alert("影厅列数输入不规范，请重新输入！");
        }else{
            var formData={
                id:Number($('#hallEditModal')[0].dataset.hallId),
                name:$("#hall-name-edit-input").val(),
                row:$("#hall-row-edit-input").val(),
                column:$("#hall-column-edit-input").val()
            };
            postRequest(
                '/hall/update',
                formData,
                function (res) {
                    if(res.success){
                        getCinemaHalls();
                        $("#hallEditModal").modal('hide');
                    }else{
                        alert(res.message);
                    }
                }
            )
        }
    });
    
    $("#hall-edit-remove-btn").click(function () {
        var r=confirm("确认要删除该影厅吗？");
        if(r){
            deleteRequest(
                '/hall/delete',
                {hallIdList:[Number($("#hallEditModal")[0].dataset.hallId)]},
                function (res) {
                    if(res.success){
                        getCinemaHalls();
                        console.log(res);
                        console.log("成功！！");
                        $("#hallEditModal").modal('hide');
                    }else{
                        alert(res.message);
                    }
                },
                function (res) {
                    console.log("错！");
                    alert(JSON.stringify(error));
                }
            )
        }
    });

    function getCinemaHalls() {
        getRequest(
            '/hall/all',
            function (res) {
                halls = res.content;
                renderHall(halls);
                console.log(res.content);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }
    
    $(document).on('click','.cinema-hall',function (e) {
        console.log(e.currentTarget.dataset.hall);
        var hallInfo=JSON.parse(e.currentTarget.dataset.hall);
        $("#hall-name-edit-input").val(hallInfo.name);
        $("#hall-row-edit-input").val(hallInfo.row);
        $("#hall-column-edit-input").val(hallInfo.column);
        $("#hallEditModal").modal('show');
        $("#hallEditModal")[0].dataset.hallId=hallInfo.id;
        console.log(hallInfo);
    });
    
    function renderHall(halls){
        $('#hall-card').empty();
        var hallDomStr = "";
        halls.forEach(function (hall) {
            var seat = "";
            for(var i =0;i<hall.row;i++){
                var temp = "";
                for(var j =0;j<hall.column;j++){
                    temp+="<div class='cinema-hall-seat'></div>";
                }
                seat+= "<div>"+temp+"</div>";
            }
            var hallDom =
                "<div id='hall-"+hall.id+"' class='cinema-hall' data-hall='"+JSON.stringify(hall)+"' style='border:1px dashed gray;margin-top:10px'>"+
                "<div>" +
                "<span class='cinema-hall-name'>"+ hall.name +"</span>" +
                "<span class='cinema-hall-size'>"+ hall.column +'*'+ hall.row +"</span>" +
                "</div>" +
                "<div class='cinema-seat'>" + seat +
                "</div>" +
                "</div>";
            hallDomStr+=hallDom;
        });
        $('#hall-card').append(hallDomStr);
    }
    
    function getCanSeeDayNum() {
        getRequest(
            '/schedule/view',
            function (res) {
                canSeeDate = res.content;
                $("#can-see-num").text(canSeeDate);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }
    
    $('#canview-modify-btn').click(function () {
        $("#canview-modify-btn").hide();
        $("#canview-set-input").val(canSeeDate);
        $("#canview-set-input").show();
        $("#canview-confirm-btn").show();
    });
    
    $('#canview-confirm-btn').click(function () {
        var dayNum = $("#canview-set-input").val();
        // 验证一下是否为数字
        postRequest(
            '/schedule/view/set',
            {day:dayNum},
            function (res) {
                if(res.success){
                    getCanSeeDayNum();
                    canSeeDate = dayNum;
                    $("#canview-modify-btn").show();
                    $("#canview-set-input").hide();
                    $("#canview-confirm-btn").hide();
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    })
});