/**
 * Created by jovin on 21/4/16.
 */

//csrf tokens for ajax post
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(function () {

    $('#addUser').click(function(){

        window.location.href="http://localhost:8080/jit/app/user/add";

    });



    var datatable = $('#userTable').DataTable({
        dom: 'frtip',
        deferLoading: 30,
        responsive: true,
        ajax: {

            "url": '/jit/app/user/list/',
            "type": 'POST',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            }, "dataSrc": function (json) {


                if (json.data == null)
                    json.data = []
                return json.data
            }
        },
        "columns": [
            {"data": "name"},
            {"data": "email"},
            {"data": "roles.rolename"},
            {"data": "id"},

        ], "columnDefs": [{
            "targets": 3,
            "data": "id",
            "render": function (data) {
                if (data != null)
                    return '<a style="text-decoration:none;padding-left:4px;padding-right:4px;color: white;background-color: #061901" href="/jit/app/user/edit/' + data + '">Edit</a>';
            }
        }  ]

    });




});
