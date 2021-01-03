<%@page language ="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <script src="/bootstrap/js/jquery-3.4.1.min.js"></script>
    <script src="/bootstrap/js/bootstrap.min.js"></script>
    <script src="/bootstrap/js/jquery.validate.min.js"></script>
    <script src="/bootstrap/js/messages_zh.js"></script>

    <script>
        $().ready(function() {
            jQuery.validator.addMethod("isId",function (value,element) {
                var id = value;
                if (!/^[0-9]*$/.test(id)){
                    return false;
                }
                return this.optional(element) || /^[0-9]/.test(id);
            },"账号必须为数字");
            $("#loginForm").validate({
                rules:{
                    userid:{
                        required: true,
                        minlength: 9,
                        maxlength: 9,
                        isId: true
                    },
                    password:{
                        required: true
                    }
                },
                messages:{
                    userid:{
                        required: "账号不能为空",
                        minlength: "账号长度为9位",
                        maxlength: "账号长度为9位"
                    },
                    password:{
                        required: "密码不能为空"
                    }
                },
            });
        });

        // $(function () {
        //     $("#login").click(function () {
        //         var id = $("#userid").val();
        //         var pwd = $("#password").val();
        //         $.ajax({
        //             url: "/login",
        //             async: false,
        //             type: "POST",
        //             data: {
        //                     "id": id,
        //                     "password": pwd
        //                     },
        //             success:function (result) {
        //             var json = $.parseJSON(result);
        //             var status = json.flag;
        //             if (status == true){
        //                 location.href='/personality';
        //             } else{
        //                 alert("账号或密码有误！");
        //             }
        //         }
        //         });
        //     });
        // });
    </script>
    <script>
        function isLogin(){
            var id = $("#userid").val();
            var pwd = $("#password").val();
            var temp = false;
            if ((id != null&&id!="") && (pwd != null&&pwd!="")){
                $.ajax({
                    url:"/islogin",
                    async:false,
                    datatype:"json",
                    type:"get",
                    data: {
                        "id": id,
                        "password": pwd
                    },
                    success: function (result) {
                        var status = result.flag;
                        if (status == true) {
                            temp = true;
                        } else if (status == false && id != null && pwd != null){
                            alert("账号或密码有误！");
                            document.getElementById("loginForm").reset()
                            temp = false;
                        }
                    }
                });
            }

            return temp;
        }
    </script>
    <style>
        .error{
            color:red;
            font-size: 16px
        }
    </style>
</head>
<body>
<div class="container-fiuld">
    <div class="row" style="margin:150px auto; width: 50%;">
        <div class="col-sm-6 col-sm-offset-3" style="border: 1px solid black">
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3">
                    <h1 class="text-center" style="font-family: 楷体;font-weight:bold;font-size: 30px;color:purple" >
                        学生登录</h1>
                </div>
            </div>
            <div class="row" >
                <div>
                    <form id="loginForm" action="/login" method="post" onsubmit="return isLogin()">
                        <div class="form-group" style="margin-top: 30px;margin-left: 60px; width:70%; ">
                            <label for="userid" style="font-size: 16px">学号：</label>
                            <input type="text" class="form-control" id="userid" name="userid" placeholder="请输入学号"
                                   style="">
                        </div>
                        <div class="form-group" style="margin-top: 30px;margin-left: 60px; width:70%; ">
                            <label for="password" style="font-size: 16px">密码：</label>
                            <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码"
                                   style="">
                        </div>

                        <div class="form-group text-center" style="margin-top: 30px; float:left; width:100%">
                            <button type = "submit" class="btn btn-primary" style="width:25%; font-weight:bold;font-size:16px">登&nbsp&nbsp录</button>
                            <button type = "button" class="btn btn-success" style="width:25%; margin-left:45px; font-weight:bold;font-size:16px" onclick="window.location.href='/register'">注&nbsp&nbsp册</button>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>