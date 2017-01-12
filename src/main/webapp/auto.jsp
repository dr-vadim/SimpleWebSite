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
            <strong>Success!</strong> Новая машина успешно добавлена!
        </div>
        <div class="alert alert-success success-update alert-dismissible top-right" style="display: none;" role="alert">
            <strong>Success!</strong> Данные автомобиля успешно обновлены!
        </div>
        <div class="alert alert-danger err-add alert-dismissible top-right" style="display: none;" role="alert">
            <strong>Error!</strong> Не получилось добавить новую машину!
        </div>
        <div class="alert alert-danger err-update alert-dismissible top-right" style="display: none;" role="alert">
            <strong>Error!</strong> Не удалось обновить данные!
        </div>
    </div>
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Добавить/Редактировать машину</h4>
            </div>
            <form class="form-horizontal" method="post" action="/auto/add">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="inputModel" class="col-sm-2 control-label">Модель:</label>
                        <div class="col-sm-10">
                            <input type="text" name="model" class="form-control" id="inputModel" placeholder="Model" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputColor" class="col-sm-2 control-label">Цвет:</label>
                        <div class="col-sm-10">
                            <input type="text" name="color" class="form-control" id="inputColor" placeholder="Color" />
                        </div>
                    </div>

                </div>
                <input type="hidden" name="id" value="" />
                <input type="hidden" name="userId" value="${user.getId()}" />
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                    <button type="button" onclick="saveAuto(this)" class="btn btn-primary">Сохранить</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<div class="container main-container">
    <div class="row">
        <div class="col-md-3">
            <p>
                <button onclick="addAutoModal()" type="button" class="btn btn-primary btn-lg">
                    Добавить Машину
                </button>
            </p>
        </div>
        <div class="col-md-9">
            <div class="panel panel-default">
                <!-- Default panel contents -->
                <div class="panel-heading">Машины пользователя <b>${user.getName()}</b>
                    <a href="/" role="button" class="btn btn-primary btn-sm">
                        <span class="glyphicon glyphicon-user" aria-hidden="true"></span> Вернутся
                    </a>
                </div>
                <!-- Table -->
                <table class="table table-bordered" id="autoTable">
                    <tr>
                        <th>#</th>
                        <th>Модель</th>
                        <th>Цвет</th>
                        <th>Действия</th>
                    </tr>
                    <c:forEach var="auto" items="${listAuto}">
                        <tr class="tr-${auto.getId()}">
                            <td>${auto.getId()}</td>
                            <td>${auto.getModel()}</td>
                            <td>${auto.getColor()}</td>
                            <td>
                                <button data-id="${auto.getId()}" type="button" class="btn btn-primary btn-sm remove-btn">
                                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Удалить
                                </button>
                                <button data-id="${auto.getId()}" type="button" class="btn btn-primary btn-sm edit-btn">
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