/**
 * Created by jovin on 14/2/16.
 */

//csrf tokens for ajax post
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(function(){

    $('#addProject').hide();


    var datatable = $('#projectTable').DataTable({
        dom: 'frtip',
        deferLoading: 30,
        responsive: true,
        ajax: {

            "url": '/jit/app/project/list/',
            "type": 'POST',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            }
        },
        "columns": [
            { "data": "name" },
            { "data": "description" },
            { "data": "createdOn" },
            { "data": "updatedOn" },
            { "data": "users" },
            { "data": "name" }

        ],  "columnDefs": [ {
            "targets": 5,
            "data": "name",
            "render": function ( data, type, full, meta ) {
                if(data != null)
                    return '<a style="text-decoration:none;padding-left:4px;padding-right:4px;color: white;background-color: #061901" href="/jit/app/project/edit/'+data+'">Edit</a>';
            }
        } ]



    });


});



