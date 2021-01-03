<%@page language ="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html lang>
<head>
    <meta charset="UTF-8">
    <title>通知详情</title>
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <script src="/bootstrap/js/jquery-3.4.1.min.js"></script>
    <script src="/bootstrap/js/bootstrap.min.js"></script>
    <script>
        function logout(){
            var message = confirm("你确定要退出吗？");
            if (message == true){
                return true;
            }else if (message == false){
                return false;
            }
        }

    </script>
    <style>
    pre {
    white-space: pre-wrap;       /* css-3 */
    white-space: -moz-pre-wrap;  /* Mozilla, since 1999 */
    /*white-space: -pre-wrap;      !* Opera 4-6 *!*/
    white-space: -o-pre-wrap;    /* Opera 7 */
    word-wrap: break-word;       /* Internet Explorer 5.5+ */
    word-break:break-all;
    overflow:hidden;
    }
    </style>

</head>
<body>
<!--导航条-->
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">图书馆自习室预约管理后台系统</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav" style="font-size: 15px">
                <li><a href="/students">学生信息</a></li>
                <li><a href="/seats">座位管理</a></li>
                <li class><a href="/chosen/reservations">选座管理<span class="sr-only">(current)</span></a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">数据统计<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/credits">学生信誉信息</a></li>
                        <li><a href="/rooms">自习室使用情况</a></li>
                    </ul>
                </li>
                <li><a href="/preservation">防疫溯源</a></li>
                <li class="active"><a href="/notices/list">通知管理<span class="sr-only">(current)</span></a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#"><span class="glyphicon glyphicon glyphicon-user" aria-hidden="true"></span>
                    <span class="text-default">${administrator.name}</span></a></li>
                <li>
                    <a href="/admin/logout" onclick="return logout()"><span class="glyphicon glyphicon glyphicon-off" aria-hidden="true"></span>
                        <span>退出</span></a>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<!--页面主体内容-->
<div class="container-fiuld">
    <div class="row" style="margin-top: 60px">
        <div class="col-sm-12 col-sm-offset-3">
            <div class="row" style="margin-top: 0px">
                <button type ="button" id="back"  class="btn btn-primary" onclick="window.location.href='/notices/list'" style="font-size: 14px;font-weight: bold;
                " >返回通知列表</button>
            </div>
        </div>
        <div class="col-sm-12 col-sm-offset-3">
            <div class="row">
                <div class="form-group" style="margin-top: 40px;  width:50%; ">
                    <h4 style="text-align: center; font-weight: bold ">${notice.title}</h4>
                    <p style="text-align: right ;height: 5px">发布日期: ${notice.date}</p>
                    <hr/>
                    <pre style="font-family:serif;font-size:16px; border: 0; background-color: white">${notice.text}</pre>
                </div>
            </div>
        </div>

    </div>


</div>
</body>
</html>