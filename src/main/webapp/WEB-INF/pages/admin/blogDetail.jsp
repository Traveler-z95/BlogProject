<%--
  Created by IntelliJ IDEA.
  User: Da
  Date: 2017/2/19
  Time: 22:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <!-- 这个是重点！！！jstl使用获取数据的前提-->
    <%@ page isELIgnored="false" %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>博客 博文详情</title>

    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="container">
    <h1>博客 博文详情</h1>
    <hr/>

    <table class="table table-bordered table-striped">
        <tr>
            <th>博文ID</th>
            <td>${blog.id}</td>
        </tr>
        <tr>
            <th>Title</th>
            <td>${blog.title}</td>
        </tr>
        <tr>
            <th>Author</th>
            <td>${blog.userByUserId.username}, ${blog.userByUserId.firstName} ${blog.userByUserId.lastName}</td>
        </tr>
        <tr>
            <th>Type</th>
            <td>${blog.typeByTypeId.name}</td>
        </tr>
        <tr>
            <th>Content</th>
            <td>${blog.content}</td>
        </tr>
        <tr>
            <th>Publish Date</th>
            <td><fmt:formatDate value="${blog.pubDate}" pattern="yyyy年MM月dd日"/></td>
        </tr>
    </table>
</div>


<div>
    <!-- 如果评论列表为空 -->
    <c:if test="${empty commentList}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>Comment表为空，请<a href="/admin/comments/add" type="button" class="btn btn-primary btn-sm">添加</a>
        </div>
    </c:if>

    <!-- 如果评论列表非空 -->
    <c:if test="${!empty commentList}">
     <table class="table table-bordered table-striped">
        <tr>
            <th>评论ID</th>
            <th>博文标题</th>
            <th>内容</th>
            <th>发布日期</th>
        </tr>
        <c:forEach items="${commentList}" var="comment">
         <tr>
                <td>${comment.id}</td>
                <td>${comment.blogByBlogId.title}</td>
                <td>${comment.content}</td>
                <td><fmt:formatDate value="${comment.pubDate }" pattern="yyyy-MM-dd"/></td>
         </tr>
        </c:forEach>
    </table>
    </c:if>
</div>


<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>
