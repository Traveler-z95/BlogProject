<%--
  Created by IntelliJ IDEA.
  User: Da
  Date: 2017/7/12
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>博客 博客管理</title>

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
<div layout:fragment="content">
    <h1>博客-博客管理</h1>
    <hr/>

    <form action="/admin/blogs/query" method="post">
        <h4>条件查询：</h4>
        标题：<input type="text" name="title" />
        起始日期：<input type="date"name="minDate" />
        截止日期：<input type="date"name="maxDate" />
        <input type="submit" value="查询" />
    </form>

    <!-- 如果用户列表为空 -->
    <c:if test="${empty sourceCodeList}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>Blog表为空，请<a href="/admin/blogs/add" type="button" class="btn btn-primary btn-sm">添加</a>
        </div>
    </c:if>

    <a href="/admin/blogs/add" type="button" class="btn btn-primary btn-sm">添加</a>

    <!-- 如果用户列表非空 -->
    <c:if test="${!empty sourceCodeList}">
    <table class="table table-bordered table-striped">
        <tr>
            <th>ID</th>
            <th>标题</th>
            <th>作者</th>
            <th>类型</th>
            <th>发布日期</th>
            <th>操作</th>
        </tr>
        <!--根据key得到的那个sourceCodeList就需要遍历出来，按照他包含的BlogEntitiy去读出来-->
        <c:forEach items="${sourceCodeList}" var="blog">
            <tr>
                <td>${blog.id}</td>
                <td>${blog.title}</td>
                <td>${blog.userByUserId.username}, ${blog.userByUserId.firstName} ${blog.userByUserId.lastName}</td>
                <td>${blog.typeByTypeId.name}</td>
                <td><fmt:formatDate value="${blog.pubDate }" pattern="yyyy-MM-dd"/></td>
                <td>
                    <a href="/admin/blogs/show/${blog.id}" type="button" class="btn btn-sm btn-success">详情</a>
                    <a href="/admin/blogs/update/${blog.id}" type="button" class="btn btn-sm btn-warning">修改</a>
                    <a href="/admin/blogs/delete/${blog.id}" type="button" class="btn btn-sm btn-danger">删除</a>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <!--这里就是处理那几个传过来的page数据的啦，从而实现分页嘛-->
            <td colspan="6" align="center" bgcolor="#5BA8DE">共${totalPageNumber}条记录 共${totalPages}页
                当前第${numberPage+1}页<br>

                <c:choose>
                    <c:when test="${numberPage!=0}">
                        <a href="${path}/admin/blogs?pageNumber=${numberPage-1}"><input type="button"
                                                                                          name="previousPage"
                                                                                          value="上一页"/></a>
                    </c:when>
                    <c:otherwise>

                        <input type="button" disabled="disabled" name="previousPage" value="上一页"/>

                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${numberPage != totalPages-1}">
                        <a href="${path}/admin/blogs?pageNumber=${numberPage+1}"><input type="button" name="nextPage"
                                                                                          value="下一页"/></a>
                    </c:when>
                    <c:otherwise>

                        <input type="button" disabled="disabled" name="nextPage" value="下一页"/>

                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </table>
</c:if>
</div>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>
