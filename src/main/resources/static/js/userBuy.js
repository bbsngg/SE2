//已购买电影票界面
$(document).ready(function () {
    getMovieList();


});

function getMovieList() {
    getRequest(
        '/ticket/get/' + sessionStorage.getItem('id'),
        function (res) {
            for(var i=0;i<res.content.length;i++){
                res.content[i].schedule.startTime=splitTimeFormat(res.content[i].schedule.startTime);
                res.content[i].schedule.endTime=splitTimeFormat(res.content[i].schedule.endTime);
            }
            var dateToTime = function(str){
                return (new Date(str.replace(/-/g,'/'))).getTime(); //用/替换日期中的-是为了解决Safari的兼容
            }
            for(var i=0; i < res.content.length; i++){
                res.content[i].newTime = dateToTime(res.content[i].schedule.startTime);
            }
            res.content.sort(function(a, b) {
                return b.newTime> a.newTime ? 1 : -1;
            });
            renderTicketList(res.content);
        },
        function (error) {
            alert(error);
        });
}

// TODO:填空
function renderTicketList(list) {
    $(".table").empty();
    var ticketDomStr = '';
    list.forEach(function (ticket) {
        ticket.state = ticket.state || '';
        if (ticket.state=='已完成'){
        ticketDomStr +=
            "<tr>"+
            "<td>"+ticket.schedule.movieName+"</td>"+
            "<td>"+ticket.schedule.hallName+"</td>"+
            "<td>"+(ticket.rowIndex+1)+"排"+(ticket.columnIndex+1)+"列"+"</td>"+
            "<td>"+ticket.schedule.startTime+"</td>"+
            "<td>"+ticket.schedule.endTime+"</td>"+
            "<td>"+ticket.state+"</td>"+
            "<td>"+"<button type='button' class='btn btn-danger' onclick='returnTicket("+ticket.id+")'><span>退票</span></button>"+"</td>"+
            "</tr>"
        }
        else {
            ticketDomStr +=
                "<tr>"+
                "<td>"+ticket.schedule.movieName+"</td>"+
                "<td>"+ticket.schedule.hallName+"</td>"+
                "<td>"+(ticket.columnIndex+1)+"排"+(ticket.rowIndex+1)+"列"+"</td>"+
                "<td>"+ticket.schedule.startTime+"</td>"+
                "<td>"+ticket.schedule.endTime+"</td>"+
                "<td>"+ticket.state+"</td>"+
                "</tr>"
        }
    });
    $(".table").append(ticketDomStr);
}

function splitTimeFormat(date) {
    time1=date.split("T")[0];
    time2=date.split("T")[1].split(".")[0];
    return time1+" "+time2;
}

function returnTicket(id) {
    if (confirm('确实要退这张票吗？')) {
        postRequest(
            '/ticket/refund?ticketId='+id,
            {
            },
            function (res) {
                if(res.success){
                    alert('退票成功！');
                    getMovieList();
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