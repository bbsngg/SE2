var colors = [
    '#FF6666',
    '#3399FF',
    '#FF9933',
    '#66cccc',
    '#FFCCCC',
    '#9966FF',
    'steelblue'
];
var movieList;
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

    var hallId,
        scheduleDate = formatDate(new Date()),
        schedules = [];

    initSelectAndDate();
    
    function getSchedules() {
    
        getRequest(
            '/schedule/search?hallId='+hallId+'&startDate='+scheduleDate.replace(/-/g,'/'),
            function (res) {
                schedules = res.content;
                renderScheduleTable(schedules);
             },
            function (error) {
                alert(JSON.stringify(error));
             }
        );
    }
    
    function renderScheduleTable(schedules){
        $('.schedule-date-container').empty();
        $(".schedule-time-line").siblings().remove();
        schedules.forEach(function (scheduleOfDate) {
            $('.schedule-date-container').append("<div class='schedule-date'>"+formatDate(new Date(scheduleOfDate.date))+"</div>");
            var scheduleDateDom = $(" <ul class='schedule-item-line'></ul>");
            $(".schedule-container").append(scheduleDateDom);
            scheduleOfDate.scheduleItemList.forEach(function (schedule,index) {
                var scheduleStyle = mapScheduleStyle(schedule);
                var scheduleItemDom =$(
                    "<li id='schedule-"+ schedule.id +"' class='schedule-item' data-schedule='"+JSON.stringify(schedule)+"' style='background:"+scheduleStyle.color+";top:"+scheduleStyle.top+";height:"+scheduleStyle.height+"'>"+
                    "<span>"+schedule.movieName+"</span>"+
                    "<span class='error-text'>¥"+schedule.fare+"</span>"+
                    "<span>"+formatTime(new Date(schedule.startTime))+"-"+formatTime(new Date(schedule.endTime))+"</span>"+
                    "</li>");
                scheduleDateDom.append(scheduleItemDom);
            });
        })
    }
    
    function mapScheduleStyle(schedule) {
        var start = new Date(schedule.startTime).getHours()+new Date(schedule.startTime).getMinutes()/60,
            end = new Date(schedule.endTime).getHours()+new Date(schedule.endTime).getMinutes()/60 ;
        return {
            color: colors[schedule.movieId%colors.length],
            top: 40*start+'px',
            height: 40*(end-start)+'px'
        }
    }
    
    function initSelectAndDate() {
        $('#schedule-date-input').val(scheduleDate);
        getCinemaHalls();
        getAllMovies();
        
        // 过滤条件变化后重新查询
        $('#hall-select').change (function () {
            hallId=$(this).children('option:selected').val();
            getSchedules();
        });
        $('#schedule-date-input').change(function () {
            scheduleDate = $('#schedule-date-input').val();
            getSchedules();
        });
    }
    
    function getCinemaHalls() {
        var halls = [];
        getRequest(
            '/hall/all',
            function (res) {
                halls = res.content;
                hallId = halls[0].id;
                halls.forEach(function (hall) {
                    $('#hall-select').append("<option value="+ hall.id +">"+hall.name+"</option>");
                    $('#schedule-hall-input').append("<option value="+ hall.id +">"+hall.name+"</option>");
                    $('#schedule-edit-hall-input').append("<option value="+ hall.id +">"+hall.name+"</option>");
                });
                getSchedules();
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }
    
    function getAllMovies() {
        getRequest(
            '/movie/all/exclude/off',
            function (res) {
                movieList = res.content;
                movieList.forEach(function (movie) {
                    $('#schedule-movie-input').append("<option value="+ movie.id +">"+movie.name+"</option>");
                    $("#schedule-edit-movie-input").append("<option value="+ movie.id +">"+movie.name+"</option>");
                });
            },
            function (error) {
                alert(error);
            }
        );
    }
    
    $(document).on('click','.schedule-item',function (e) {
        // console.log(JSON.parse(e.target.dataset.schedule));
        var schedule = JSON.parse(e.target.dataset.schedule);
        $("#schedule-edit-hall-input").children('option[value='+schedule.hallId+']').attr('selected',true);
        $("#schedule-edit-movie-input").children('option[value='+schedule.movieId+']').attr('selected',true);
        $("#schedule-edit-start-date-input").val(schedule.startTime.slice(0,16));
        $("#schedule-edit-end-date-input").val(schedule.endTime.slice(0,16));
        $("#schedule-edit-price-input").val(schedule.fare);
        $('#scheduleEditModal').modal('show');//点击排片信息就展示出修改排片的界面
        //$('#scheduleEditModal')是jquery包装对象，而$('#scheduleEditModal')[0]是js原生dom对象，可以操作dom特有的属性
        $('#scheduleEditModal')[0].dataset.scheduleId = schedule.id;//为对应div的data-schedule-id属性设置值，以方便更新、删除操作时获取scheduleId
        console.log(schedule);
    });

    $('#schedule-form-btn').click(function () {
        //todo 需要做一下？
        //表单验证部分
        var price=$("#schedule-price-input");
        var hallTime=(new Date($("#schedule-end-date-input").val()).getTime()-new Date($("#schedule-start-date-input").val()).getTime())/60000;
        var movieTime;
        for(var i=0;i<movieList.length;i++){
            if(movieList[i].id==$("#schedule-movie-input").children('option:selected').val()){
                movieTime=movieList[i].length;
                break;
            }
        }
        if(parseInt(price.val())<=0){
            alert("票价应该不小于0元!");
        }else if(movieTime>hallTime){
            alert("电影放映时间应该不小于排片时间!");
        }
        else{
            var form = {
                hallId: $("#schedule-hall-input").children('option:selected').val(),
                movieId : $("#schedule-movie-input").children('option:selected').val(),
                startTime: $("#schedule-start-date-input").val(),
                endTime: $("#schedule-end-date-input").val(),
                fare: $("#schedule-price-input").val()
            };
            postRequest(
                '/schedule/add',
                form,
                function (res) {
                    if(res.success){
                        getSchedules();//每次点击添加排片按钮就重新渲染排片信息页面
                        $("#scheduleModal").modal('hide');
                    } else {
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
    });
    
    $('#schedule-edit-form-btn').click(function () {
        //todo 需要做一下表单验证？
        var price=$("#schedule-edit-price-input");
        var hallTime=(new Date($("#schedule-edit-end-date-input").val()).getTime()-new Date($("#schedule-edit-start-date-input").val()).getTime())/60000;
        var movieTime;
        for(var i=0;i<movieList.length;i++){
            if(movieList[i].id==$("#schedule-edit-movie-input").children('option:selected').val()){
                movieTime=movieList[i].length;
                break;
            }
        }
        if(parseInt(price.val())<=0){
            alert("票价应该不小于0元!");
        }else if(movieTime>hallTime){
            alert("电影放映时间应该不小于排片时间!");
        }else{
            var form = {
                id: Number($('#scheduleEditModal')[0].dataset.scheduleId),
                hallId: $("#schedule-edit-hall-input").children('option:selected').val(),
                movieId : $("#schedule-edit-movie-input").children('option:selected').val(),
                startTime: $("#schedule-edit-start-date-input").val(),
                endTime: $("#schedule-edit-end-date-input").val(),
                fare: $("#schedule-edit-price-input").val()
            };


            postRequest(
                '/schedule/update',
                form,
                function (res) {
                    if(res.success){
                        getSchedules();
                        $("#scheduleEditModal").modal('hide');
                    } else{
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
    });
    
    $("#schedule-edit-remove-btn").click(function () {
        var r=confirm("确认要删除该排片信息吗");
        if (r) {
            deleteRequest(
                '/schedule/delete/batch',
                {scheduleIdList:[Number($('#scheduleEditModal')[0].dataset.scheduleId)]},
                function (res) {
                    if(res.success){
                        getSchedules();
                        $("#scheduleEditModal").modal('hide');
                    } else{
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
    })

});

