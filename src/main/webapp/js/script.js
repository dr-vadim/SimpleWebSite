$(document).ready(function(){

    // Пользователь
    $("#usersTable").on("click","button.remove-btn",function(){
        var id = $(this).attr("data-id");
        var tr = $("#usersTable").find(".tr-"+id).get(0);
        $.ajax({
            url: "/delete",
            method: "POST",
            data: {id: id},
            dataType: "json",
            success: function (data,status) {
                $(tr).remove();
            },
            error: function (req,status,err) {
            }
        });
    });

    $("#usersTable").on("click","button.edit-btn",function(){
        var form = $("#myModal").find("form");
        form.attr("action","/update");
        var id = $(this).attr("data-id");
        $.ajax({
            url: "/get",
            method: "POST",
            data: {id: id},
            dataType: "json",
            success: function(jsonData){
                if(jsonData.id){
                    $(form).find("input").each(function(){
                       var val = $(this).attr("name");
                       if(jsonData[val]){
                           $(this).val(jsonData[val]);
                       }
                    });
                }
            }
        });
        form.find("input[name='id-value']").val(id);
        $("#myModal").modal();
    });

    // Автомобили
    $("#autoTable").on("click","button.remove-btn",function(){
        var id = $(this).attr("data-id");
        var tr = $("#autoTable").find(".tr-"+id).get(0);
        $.ajax({
            url: "/auto/delete",
            method: "POST",
            data: {id: id},
            dataType: "json",
            success: function (data,status) {
                $(tr).remove();
            },
            error: function (req,status,err) {
            }
        });
    });

    $("#autoTable").on("click","button.edit-btn",function(){
        var form = $("#myModal").find("form");
        form.attr("action","/auto/update");
        var id = $(this).attr("data-id");
        $.ajax({
            url: "/auto/get",
            method: "POST",
            data: {id: id},
            dataType: "json",
            success: function(jsonData){
                if(jsonData.id){
                    $(form).find("input").each(function(){
                        var val = $(this).attr("name");
                        if(jsonData[val]){
                            $(this).val(jsonData[val]);
                        }
                    });
                }
            }
        });
        form.find("input[name='id-value']").val(id);
        $("#myModal").modal();
    });

});

// User
function addModal(){
    $("#myModal").find("form").attr("action","/add")
    $("#myModal").modal();
}
function saveUser(button){
    var form = $(button).closest("form");
    var action = $(form).attr("action");
    var name = $(form).find("[name='name']").val();
    var age = $(form).find("[name='age']").val();
    var id = $(form).find("[name='id-value']").val();
    var tr = $("#usersTable").find("tr.tr-"+id);
    var data = {  name: name, age: age };
    if(action == "/update")
        data.id = id;
    console.log(data);
    $.ajax({
        url: action,
        method: "POST",
        data: data,
        dataType: "json",
        success: function (data,status) {
            if(data.id) {
                var fields = ["id", "name", "age"];
                var actionsHtml =
                    "<a href=\"/auto?userId="+data.id+"\" role=\"button\" class=\"btn btn-primary btn-sm\">"
                        +"<span class=\"glyphicon glyphicon-list\" aria-hidden=\"true\"></span> Авто"+
                    "</a> "
                    +"<button data-id=\"" + data.id + "\" type=\"button\" class=\"btn btn-primary btn-sm remove-btn\">"
                        +"<span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span> Удалить"+
                    "</button> "
                    +"<button data-id=\"" + data.id + "\" type=\"button\" class=\"btn btn-primary btn-sm edit-btn\">" +
                        "<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span> Редактировать"+
                    "</button>";
                if(action == '/add') {
                    var htmlRow = "<tr class=\"tr-"+data.id+"\">";
                    for (var i = 0; i < fields.length; i++) {
                        htmlRow += "<td>"+data[fields[i]]+"</td>";
                    }
                    htmlRow += "<td>"+actionsHtml+"</td>";
                    $(htmlRow).appendTo("#usersTable");
                    $("#myMaodal-alerts").find("div.success-add").fadeIn(500).delay(2500).fadeOut(500);
                }else if(action == "/update"){
                    var i = 0;
                    console.log($(tr).find("td"));
                    $(tr).find("td").each(function(){
                       if(fields[i]){
                           $(this).text(data[fields[i]]);
                       } else{
                           $(this).html(actionsHtml);
                       }
                       i++;
                    });
                    $("#myMaodal-alerts").find("div.success-update").fadeIn(500).delay(2500).fadeOut(500);
                }
            }
        },
        error: function (req,status,err) {
            var classAlert = (action == "/add") ? "div.err-add" : "div.err-update";
            $("#myMaodal-alerts").find(classAlert).fadeIn(500).delay(2500).fadeOut(500);
        }
    });
}

// Auto
function addAutoModal(){
    $("#myModal").find("form").attr("action","/auto/add")
    $("#myModal").modal();
}
function saveAuto(button){
    var form = $(button).closest("form");
    var action = $(form).attr("action");
    var model = $(form).find("[name='model']").val();
    var color = $(form).find("[name='color']").val();
    var userId = $(form).find("[name='userId']").val();
    var id = $(form).find("[name='id']").val();
    var tr = $("#autoTable").find("tr.tr-"+id);
    var data = {  model: model, color: color, userId: userId };
    if(action == "/auto/update")
        data.id = id;
    console.log(data);
    $.ajax({
        url: action,
        method: "POST",
        data: data,
        dataType: "json",
        success: function (data,status) {
            if(data.id) {
                var fields = ["id", "model", "color"];
                var actionsHtml =
                    "<button data-id=\"" + data.id + "\" type=\"button\" class=\"btn btn-primary btn-sm remove-btn\">"
                        +"<span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span> Удалить"+
                    "</button> "
                    +"<button data-id=\"" + data.id + "\" type=\"button\" class=\"btn btn-primary btn-sm edit-btn\">" +
                        "<span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span> Редактировать"+
                    "</button>";
                if(action == '/auto/add') {
                    var htmlRow = "<tr class=\"tr-"+data.id+"\">";
                    for (var i = 0; i < fields.length; i++) {
                        htmlRow += "<td>"+data[fields[i]]+"</td>";
                    }
                    htmlRow += "<td>"+actionsHtml+"</td>";
                    $(htmlRow).appendTo("#autoTable");
                    $("#myMaodal-alerts").find("div.success-add").fadeIn(500).delay(2500).fadeOut(500);
                }else if(action == "/auto/update"){
                    var i = 0;
                    console.log($(tr).find("td"));
                    $(tr).find("td").each(function(){
                        if(fields[i]){
                            $(this).text(data[fields[i]]);
                        } else{
                            $(this).html(actionsHtml);
                        }
                        i++;
                    });
                    $("#myMaodal-alerts").find("div.success-update").fadeIn(500).delay(2500).fadeOut(500);
                }
            }
        },
        error: function (req,status,err) {
            var classAlert = (action == "/auto/add") ? "div.err-add" : "div.err-update";
            $("#myMaodal-alerts").find(classAlert).fadeIn(500).delay(2500).fadeOut(500);
        }
    });
}