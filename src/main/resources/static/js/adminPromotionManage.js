$(document).ready(function() {
    var movieList_const=[];

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

    getAllMovies();

    getActivities();

    getCoupons();

    function getActivities() {
        getRequest(
            '/activity/get',
            function (res) {
                var activities = res.content;
                renderActivities(activities);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function renderActivities(activities) {
        $(".content-activity").empty();
        var activitiesDomStr = "";

        activities.forEach(function (activity) {
            var movieDomStr = "";
            if(activity.movieList.length !== movieList_const.length){
                activity.movieList.forEach(function (movie) {
                    movieDomStr += "<li class='activity-movie primary-text'>"+movie.name+"</li>";
                });
            }else{
                movieDomStr = "<li class='activity-movie primary-text'>所有热映电影</li>";
            }

            activitiesDomStr+=
                "<div class='activity-container'>" +
                "    <div class='activity-card card'>" +
                "       <div class='activity-line'>" +
                "           <span class='title'>"+activity.name+"</span>" +
                "           <span class='gray-text'>"+activity.description+"</span>" +
                "       </div>" +
                "       <div class='activity-line'>" +
                "           <span>活动时间："+formatDate(new Date(activity.startTime))+"至"+formatDate(new Date(activity.endTime))+"</span>" +
                "       </div>" +
                "       <div class='activity-line'>" +
                "           <span>参与电影：</span>" +
                "               <ul>"+movieDomStr+"</ul>" +
                "       </div>" +
                "    </div>" +
                "    <div class='activity-coupon primary-bg'>" +
                "        <span class='title'>优惠券："+activity.coupon.name+"</span>" +
                "        <span class='title'>满"+activity.coupon.targetAmount+"减<span class='error-text title'>"+activity.coupon.discountAmount+"</span></span>" +
                "        <span class='gray-text'>"+activity.coupon.description+"</span>" +
                "    </div>" +
                "</div>";
        });
        $(".content-activity").append(activitiesDomStr);
    }

    function getAllMovies() {
        getRequest(
            '/movie/all/exclude/off',
            function (res) {
                var movieList = res.content;
                getAllMoviesToAdd(res.content);
                $('#activity-movie-input').append("<option value="+ -1 +">所有电影</option>");
                movieList.forEach(function (movie) {
                    $('#activity-movie-input').append("<option value="+ movie.id +">"+movie.name+"</option>");
                });
            },
            function (error) {
                alert(error);
            }

        );
    }


    function getAllMoviesToAdd(content) {
        movieList_const = content;
    }


    //发布活动 post
    $("#activity-form-btn").click(function () {

        if (selectedMovieIds.size===0){
            movieList_const.forEach(function (movie) {
                selectedMovieIds.add(movie.id);
                selectedMovieNames.add(movie.name);
            })
        }
        var form = {
            name: $("#activity-name-input").val(),
            description: $("#activity-description-input").val(),
            startTime: $("#activity-start-date-input").val(),
            endTime: $("#activity-end-date-input").val(),
            movieList: [...selectedMovieIds], //被选择参加活动的电影
        couponForm: {
            description: $("#coupon-description-input").val(),
                name: $("#coupon-name-input").val(),
                targetAmount: $("#coupon-target-input").val(),
                discountAmount: $("#coupon-discount-input").val(),
                startTime: $("#activity-start-date-input").val(),
                endTime: $("#activity-end-date-input").val()
        }
    };

        postRequest(
            '/activity/publish',
            form,
            function (res) {
                if(res.success){
                    getActivities();
                    $("#activityModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    //ES6新api 不重复集合 Set
    var selectedMovieIds = new Set();
    var selectedMovieNames = new Set();

    $('#activity-movie-input').change(function () {
        var movieId = $('#activity-movie-input').val();
        var movieName = $('#activity-movie-input').children('option:selected').text();

        if(movieId==-1){        // -1代表所有电影标号
            selectedMovieIds.clear();
            selectedMovieNames.clear();
        } else {
            selectedMovieIds.add(movieId);
            selectedMovieNames.add(movieName);
        }
        renderSelectedMovies();
    });

    //渲染选择的参加活动的电影
    // 将已经选择参加活动的电影显示在选择框下方
    function renderSelectedMovies() {
        $('#selected-movies').empty();
        var moviesDomStr = "";
        selectedMovieNames.forEach(function (movieName) {
            moviesDomStr += "<span class='label label-primary'>"+movieName+"</span>";
        });
        $('#selected-movies').append(moviesDomStr);
    }
    
    function getUsers() {
        getRequest()
    }
    
    function getCoupons() {
        getRequest(
            '/activity/get',
            function (res) {
                var allActivities=res.content;
                allActivities.forEach(function (activity) {
                    var couponDescription=activity.coupon.name+" "+activity.coupon.description;
                    var optionStr="<option value='"+activity.coupon.id+"'>"+couponDescription+"</option>";
                    $("#coupon-input").append(optionStr);
                });
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        )
    }
});