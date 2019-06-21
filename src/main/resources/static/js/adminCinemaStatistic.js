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

    getScheduleRate();
    
    getBoxOffice();
    
    getAudiencePrice();
    
    getPlacingRate();

    getPopularMovie();
});
    
    function getScheduleRate() {
    
        getRequest(
            '/statistics/scheduleRate',
            function (res) {
                var data = res.content||[];
                var tableData = data.map(function (item) {
                   return {
                       value: item.time,
                       name: item.name
                   };
                });
                var nameList = data.map(function (item) {
                    return item.name;
                });
                var option = {
                    title : {
                        text: '今日排片率',
                        subtext: new Date().toLocaleDateString(),
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        x : 'center',
                        y : 'bottom',
                        data:nameList
                    },
                    toolbox: {
                        show : true,
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            magicType : {
                                show: true,
                                type: ['pie', 'funnel']
                            },
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    calculable : true,
                    series : [
                        {
                            name:'面积模式',
                            type:'pie',
                            radius : [30, 110],
                            center : ['50%', '50%'],
                            roseType : 'area',
                            data:tableData
                        }
                    ]
                };
                var scheduleRateChart = echarts.init($("#schedule-rate-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }
    
    function getBoxOffice() {
    
        getRequest(
            '/statistics/boxOffice/total',
            function (res) {
                var data = res.content || [];
                var tableData = data.map(function (item) {
                    return item.boxOffice;
                });
                var nameList = data.map(function (item) {
                    return item.name;
                });
                var option = {
                    title : {
                        text: '所有电影票房',
                        subtext: '截止至'+new Date().toLocaleDateString(),
                        x:'center'
                    },
                    xAxis: {
                        type: 'category',
                        data: nameList,
                        axisLabel:{
                            interval:0
                        }
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: tableData,
                        type: 'bar'
                    }]
                };
                var scheduleRateChart = echarts.init($("#box-office-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            });
    }
    
    function getAudiencePrice() {
        getRequest(
            '/statistics/audience/price',
            function (res) {
                var data = res.content || [];
                var tableData = data.map(function (item) {
                    return item.price;
                });
                var nameList = data.map(function (item) {
                    return formatDate(new Date(item.date));
                });
                var option = {
                    title : {
                        text: '每日客单价',
                        x:'center'
                    },
                    xAxis: {
                        type: 'category',
                        data: nameList,
                        axisLabel:{
                            interval:0
                        }
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: tableData,
                        type: 'line'
                    }]
                };
                var scheduleRateChart = echarts.init($("#audience-price-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            });
    }
    
    function getPlacingRate() {
        // todo
        var date=new Date().toLocaleDateString().replace('/','-');
        date=date.replace('/','-');
        getRequest(
            '/statistics/PlacingRate?date='+date,
            function(res){
                var data=res.content||[];
                var tableData=data.map(function(item){
                    return item.placingRate;
                });
                var nameList=data.map(function(item){
                    return item.name;
                });
                var option={
                    title:{
                        text:'今日上座率',
                        subtext:new Date().toLocaleDateString(),
                        x:'center'
                    },
                    xAxis:{
                        type:'category',
                        data:nameList,
                        axisLabel:{
                            interval:0
                        }
                    },
                    yAxis:{
                        type:'value'
                    },
                    series:[{
                        data:tableData,
                        type:'bar'
                    }]
                };
                var scheduleRateChart=echarts.init($("#place-rate-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function(error){
                alert(JSON.stringify(error));
            }
        )
    }


    function getPopularMovie() {
        // todo
        var days=$("#select-days").val();
        var movieNum=$("#select-movieNum").val();
        if(parseInt(days)<=0||parseInt(movieNum)<=0){
            alert("选择不规范！请重新选择！")
        }else {
            getRequest(
                '/statistics/popular/movie?days=' + parseInt(days) + '&movieNum=' + parseInt(movieNum),
                function (res) {
                    var data = res.content || [];
                    var tableData = data.map(function (item) {
                        return {
                            value: item.boxOffice,
                            name: item.name
                        };
                    });
                    var nameList = data.map(function (item) {
                        return item.name;
                    });
                    var option = {
                        title: {
                            subtext: days + '天内最受欢迎的' + movieNum + '部电影',
                            x: 'center'
                        },
                        tooltip: {
                            trigger: 'item',
                            formatter: "{a} <br/>{b} : {c} ({d}%)"
                        },
                        legend: {
                            x: 'center',
                            y: 'bottom',
                            data: nameList
                        },
                        calculable: true,
                        series: [
                            {
                                name: '最受欢迎的电影',
                                type: 'pie',
                                radius: [30, 110],
                                //center : ['50%', '50%'],
                                //roseType : 'area',
                                data: tableData,
                                itemStyle: {
                                    emphasis: {
                                        shadowBlur: 10,
                                        shadowOffsetX: 0,
                                        shadowColor: 'rgba(30,144,255,0.5)'
                                    }
                                }
                            }
                        ]
                    };
                    var scheduleRateChart = echarts.init($("#popular-movie-container")[0]);
                    scheduleRateChart.setOption(option);
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
    }