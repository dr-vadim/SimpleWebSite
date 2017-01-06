<%@ page import="models.User" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String title = request.getAttribute("Title").toString();
    List<User> users = (List<User>)request.getAttribute("userList");
%>
<html>
    <head>
        <title>${Title}</title>
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/webjars/bootstrap/3.3.7/css/bootstrap-theme.min.css" />
        <link rel="stylesheet/less" type="text/css" href="css/style.less" />
        <script type="text/javascript" src="<%=request.getContextPath()%>/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/webjars/less/2.5.3/less.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/webjars/jquery/3.1.1/jquery.js"></script>
        <script type="text/javascript" src="js/script.js"></script>
    </head>
    <body>
        <div class="container main-container">
            <div class="row">
                <div class="col-md-3">
                    <p>
                        <button type="button" class="btn btn-primary btn-lg">Добавить Пользователя</button>
                    </p>
                    <p>
                        <button type="button" class="btn btn-primary btn-lg">Добавить Авто</button>
                    </p>
                </div>
                <div class="col-md-9">
                    <h2>Hello!</h2>
                    <div class="panel panel-default">
                        <!-- Default panel contents -->
                        <div class="panel-heading">Panel heading</div>
                        <!-- Table -->
                        <table class="table table-bordered">
                            <tr>
                                <th>#</th>
                                <th>Имя</th>
                                <th>Вазраст</th>
                            </tr>
                            <c:forEach var="user" items="${userList}">
                            <tr>
                                <td>${user.getId()}</td>
                                <td>${user.getName()}</td>
                                <td>${user.getAge()}</td>
                            </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
