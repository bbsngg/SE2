var userList=[];
var currentUserId;
var currentUser;

$(document).ready(function () {
    if (sessionStorage.getItem('role')=='manager') {
        $('#power').css('display','inline');
    }
    getUserList();

    $("#authority-form-btn").click(function () {
        console.log(currentUser+"-->"+currentUserId);
        postRequest(
                '/role/update',
                {
                    id:currentUserId,
                    username:currentUser,
                    roleName:$("#role-input").val()
                },
                function (res) {
                    if(res.success){
                        $("#setAuthority").modal('hide');
                        alert("修改权限成功！");
                        getUserList();
                    } else {
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            )
    });

});


function getUserList() {
    getRequest(
        '/role/all',
        function (res) {
            userList = res.content;
            $("#account-table").empty();
            $("#account-table").append('<tr>' +
                '               <th>帐户名</th>' +
                '               <th>权限</th>' +
                '               <th>操作</th>' +
                '            </tr>');
            userList.forEach(function (user) {
                $("#account-table").append('<tr>' +
                    '<td>'+user.username+'</td>' +
                    '<td>'+user.roleName+'</td>' +
                    '<td>'+'<button type="button" class="btn btn-primary" data-backdrop="static" data-toggle="modal" data-target="#setAuthority" onclick="setCurrentUser('+user.id+',\''+user.username+'\')"><i class="icon-pencil"></i> 修改权限</button>' +
                    '&nbsp;' +
                    '<button type="button" class="btn btn-danger" onclick="deleteUser('+user.id+')"><i class="icon-trash"></i> 删除帐户</button>'+'</td>' +
                    '</tr>'
                );
            })
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
}

function setCurrentUser(id,name) {
    currentUser=name;
    currentUserId=id;
    console.log("调用set"+currentUser+"-->"+currentUserId);

}

function deleteUser(id) {
    if (confirm("确实要删除该账号吗？")){
        deleteRequest(
            '/role/delete?id='+id,
            {
            },
            function (res) {
                if(res.success){
                    alert("删除账号成功！");
                    getUserList();
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