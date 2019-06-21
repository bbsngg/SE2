$(document).ready(function(){

    var movieId = parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);
    var userId = sessionStorage.getItem('id');
    var isLike = false;
    var movie;

    getMovie();
    if(sessionStorage.getItem('role') === 'admin'||sessionStorage.getItem('role') === 'manager')
        getMovieLikeChart();

    function getMovieLikeChart() {
        getRequest(
            '/movie/' + movieId + '/like/date',
            function(res){
                var data = res.content,
                    dateArray = [],
                    numberArray = [];
                data.forEach(function (item) {
                    dateArray.push(item.likeTime);
                    numberArray.push(item.likeNum);
                });

                var myChart = echarts.init($("#like-date-chart")[0]);

                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: '想看人数变化表'
                    },
                    xAxis: {
                        type: 'category',
                        data: dateArray
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: numberArray,
                        type: 'line'
                    }]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            },
            function (error) {
                alert(error);
            }
        );
    }

    function getMovie() {
        getRequest(
            '/movie/'+movieId + '/' + userId,
            function(res){
                movie = res.content;
                isLike = movie.islike;
                repaintMovieDetail(movie);
            },
            function (error) {
                alert(error);
            }
        );
    }

    function repaintMovieDetail(movie) {
        !isLike ? $('.icon-heart').removeClass('error-text') : $('.icon-heart').addClass('error-text');
        $('#like-btn span').text(isLike ? ' 已想看' : ' 想 看');
        $('#movie-img').attr('src',movie.posterUrl);
        $('#movie-name').text(movie.name);
        $('#order-movie-name').text(movie.name);
        $('#movie-description').text(movie.description);
        $('#movie-startDate').text(new Date(movie.startDate).toLocaleDateString());
        $('#movie-type').text(movie.type);
        $('#movie-country').text(movie.country);
        $('#movie-language').text(movie.language);
        $('#movie-director').text(movie.director);
        $('#movie-starring').text(movie.starring);
        $('#movie-writer').text(movie.screenWriter);
        $('#movie-length').text(movie.length);
    }

    // user界面才有
    $('#like-btn').click(function () {
        var url = isLike ?'/movie/'+ movieId +'/unlike?userId='+ userId :'/movie/'+ movieId +'/like?userId='+ userId;
        postRequest(
            url,
            null,
            function (res) {
                isLike = !isLike;
                getMovie();
            },
            function (error) {
                alert(error);
            });
    });

    // admin界面才有

    $("#modify-btn").click(function () {
        // alert('交给你们啦，修改时需要在对应html文件添加表单然后获取用户输入，提交给后端，别忘记对用户输入进行验证。（可参照添加电影&添加排片&修改排片）');
        $("#movie-edit-name-input").val(movie.name);
        $("#movie-edit-date-input").val(movie.startDate.slice(0,10));
        $("#movie-edit-img-input").val(movie.posterUrl);
        $("#movie-edit-description-input").val(movie.description);
        $("#movie-edit-type-input").val(movie.type);
        $("#movie-edit-length-input").val(movie.length);
        $("#movie-edit-country-input").val(movie.country);
        $("#movie-edit-language-input").val(movie.language);
        $("#movie-edit-director-input").val(movie.director);
        $("#movie-edit-star-input").val(movie.starring);
        $("#movie-edit-writer-input").val(movie.director);
        //$("#movieModal").modal('hide');
    });

    $("#delete-btn").click(function () {
        // alert('交给你们啦，下架别忘记需要一个确认提示框，也别忘记下架之后要对用户有所提示哦');
        var r=confirm("确认要下架该电影吗？");
        if (r) {
            console.log(movieId);
            postRequest(
                '/movie/off/batch',
                {movieIdList:[movieId]},
                function (res) {
                    if(res.success){
                        alert("下架成功！");
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

    $('#movie-edit-form-btn').click(function () {
        if ($('#movie-edit-name-input').val() === ''){
            alert("电影名称不能为空！")
        }else if($('#movie-edit-img-input').val() === ''){
            alert("电影海报不能为空！")
        }else if(parseInt($('#movie-edit-length-input').val()) <= 0){
            alert("片长不能小于等于零！")
        }
        else{
            var formData = getMovieEditForm();
            console.log(formData);
            postRequest(
                '/movie/update',
                formData,
                function (res) {
                    if(res.success){
                        console.log(res);
                        getMovie();
                        $("#movieModal").modal('hide');
                        //$("#movieEditModal").modal('hide');
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

    function getMovieEditForm() {
        return {
            id: movieId,
            name: $('#movie-edit-name-input').val(),
            startDate: $('#movie-edit-date-input').val(),
            posterUrl: $('#movie-edit-img-input').val(),
            description: $('#movie-edit-description-input').val(),
            type: $('#movie-edit-type-input').val(),
            length: $('#movie-edit-length-input').val(),
            country: $('#movie-edit-country-input').val(),
            starring: $('#movie-edit-star-input').val(),
            director: $('#movie-edit-director-input').val(),
            screenWriter: $('#movie-edit-writer-input').val(),
            language: $('#movie-edit-language-input').val()
        };
    }
});