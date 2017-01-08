<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>${Title}</title>
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/webjars/bootstrap/3.3.7/css/bootstrap-theme.min.css" />
        <link rel="stylesheet/less" type="text/css" href="css/style.less" />
        <script type="text/javascript" src="<%=request.getContextPath()%>/webjars/less/2.5.3/less.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/webjars/jquery/3.1.1/jquery.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/script.js"></script>
    </head>
    <body>
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div id="myMaodal-alerts">
                <div class="alert alert-success success-add alert-dismissible top-right" style="display: none;" role="alert">
                    <strong>Success!</strong> Новый пользователь успешно добавлен!
                </div>
                <div class="alert alert-success success-update alert-dismissible top-right" style="display: none;" role="alert">
                    <strong>Success!</strong> Данные пользователя успешно обновлены!
                </div>
                <div class="alert alert-danger err-add alert-dismissible top-right" style="display: none;" role="alert">
                    <strong>Error!</strong> Не получилось добавить нового пользователя!
                </div>
                <div class="alert alert-danger err-update alert-dismissible top-right" style="display: none;" role="alert">
                    <strong>Error!</strong> Не удалось обновить данные!
                </div>
            </div>
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Добавить/Редактировать пользователя</h4>
                    </div>
                    <form class="form-horizontal" method="post" action="/add">
                        <div class="modal-body">
                                <div class="form-group">
                                    <label for="inputUsername" class="col-sm-2 control-label">Имя:</label>
                                    <div class="col-sm-10">
                                        <input type="text" name="name" class="form-control" id="inputUsername" placeholder="Username" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="inputAge" class="col-sm-2 control-label">Возраст:</label>
                                    <div class="col-sm-10">
                                        <input type="text" name="age" class="form-control" id="inputAge" placeholder="Age" />
                                    </div>
                                </div>

                        </div>
                        <input type="hidden" name="id-value" value="" />
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                            <button type="button" onclick="saveUser(this)" class="btn btn-primary">Сохранить</button>
                        </div>
                    </form>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
        <div class="container main-container">
            <div class="row">
                <div class="col-md-3">
                    <p>
                        <button onclick="addModal()" type="button" class="btn btn-primary btn-lg">
                            Добавить Пользователя
                        </button>
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
                        <table class="table table-bordered" id="usersTable">
                            <tr>
                                <th>#</th>
                                <th>Имя</th>
                                <th>Вазраст</th>
                                <th>Действия</th>
                            </tr>
                            <c:forEach var="user" items="${userList}">
                            <tr class="tr-${user.getId()}">
                                <td>${user.getId()}</td>
                                <td>${user.getName()}</td>
                                <td>${user.getAge()}</td>
                                <td>
                                    <button data-id="${user.getId()}" type="button" class="btn btn-primary btn-sm remove-btn">
                                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Удалить
                                    </button>
                                    <button data-id="${user.getId()}" type="button" class="btn btn-primary btn-sm edit-btn">
                                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Редактировать
                                    </button>
                                </td>
                            </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
