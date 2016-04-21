/**
 * Created by jovin on 14/2/16.
 */

//csrf tokens for ajax post
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(function () {

    $('#addProject').click(function(){

        window.location("http://localhost:8080/jit/app/project/add")

    });



    var datatable = $('#projectTable').DataTable({
        dom: 'frtip',
        deferLoading: 30,
        responsive: true,
        ajax: {

            "url": '/jit/app/project/list/',
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
            {"data": "description"},
            {"data": "createdOn"},
            {"data": "updatedOn"},
            {"data": "users"},
            {"data": "name"},
            {"data": "manager"},
            {"data": "name"},

        ], "columnDefs": [{
            "targets": 5,
            "data": "name",
            "render": function (data) {
                if (data != null)
                    return '<a style="text-decoration:none;padding-left:4px;padding-right:4px;color: white;background-color: #061901" href="/jit/app/project/edit/' + data + '">Edit</a>';
            }
        },
            {
                "targets": 4,
                "data": "users",
                "render": function (data) {
                    var name = [data[0].name];
                    for (j = 0; j < data.length; j++) {

                        if (name[j] != data[j].name)
                            name.push(data[j].name);

                    }
                    return name;

                }
            },
            {
                "targets": 6,
                "data": "manager",
                "render": function (data) {
                    $('#manager').val(data);
                    if ($('#userMailId').val() == $('#manager').val())
                        datatable.column(5).visible(true);

                    return name;

                }
            }, {
                "targets": 7,
                "data": "name",
                "render": function (data, type, full, meta) {
                    if (data != null)
                        return '<a style="text-decoration:none;padding-left:4px;padding-right:4px;color: white;background-color: #061901" href="/jit/app/project/' + data + '">View Issues</a>';
                }
            }
        ]

    });

    if ($('#roleId').val() != 1)
        datatable.column(5).visible(false);


    if ($('#userMailId').val() == $('#manager').val())
        datatable.column(5).visible(true);

    datatable.column(6).visible(false);


});

