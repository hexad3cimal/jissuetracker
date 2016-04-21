/**
 * Created by jovin on 17/2/16.
 */
//csrf tokens for ajax post
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");


$(function () {

    $('#reload').click(function() {
        location.reload();
    });

    document.title = getProjectName()+' - Home';

    $("#projectTitle").val(getProjectName());

    $('#issueBlock').hide();

    $("#completionDate").datetimepicker();

    $('#addIssue').click(function () {

        $.ajax({
            type: "GET",
            url: '/jit/app/tracker/list',
            success: function (json) {

                var $el = $("#tracker");
                $el.empty(); // remove old options
                $el.append($("<option></option>")
                );
                $.each(json.data, function (value, key) {
                    $el.append($("<option></option>")
                        .attr("value", value).text(key));
                });
                $('#tracker').chosen({width: "95%"});
            }
        });



        $.ajax({
            type: "GET",
            url: '/jit/app/status/list',
            success: function (json) {

                var $el = $("#status");
                $el.empty(); // remove old options
                $el.append($("<option></option>")
                );
                $.each(json.data, function (value, key) {
                    $el.append($("<option></option>")
                        .attr("value", value).text(key));
                });
                $('#status').chosen({width: "95%"});


            }
        });


        $.ajax({
            type: "GET",
            url: '/jit/app/project/' + getProjectName() + '/userList',
            success: function (json) {

                var $el = $("#assigned");
                $el.empty(); // remove old options
                $el.append($("<option></option>"));
                $el.append($("<option></option>")
                    .attr("value", " ").text("Choose One"));
                $.each(json.data, function (value, key) {
                    $el.append($("<option></option>")
                        .attr("value", value).text(key));
                });

                $('#assigned').chosen({width: "95%"});


            }
        });

        $('#issueBlock').show();


        $('#projectBlock').hide();
        $('#datatables').hide();


    });


    $('#viewAllIssues').click(function(){

        $('#issueBlock').hide();


        $('#projectBlock').hide();

        //issueTable.ajax.reload();


    });


    var issueTable = $('#issueTable').DataTable({
        dom: 'frtip',
        deferLoading: 30,
        responsive: true,
        ajax: {

            "url": '/jit/app/issues/' + getProjectName(),
            "type": 'POST',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            "dataSrc": function ( json ) {


                if(json.data == null)
                    json.data = []
                return json.data
            }
        },
        "columns": [
            { "data": "title" },
            { "data": "createdBy" },
            { "data": "assignedTo" },
            { "data": "status" },
            { "data": "tracker" },
            { "data": "createdOn" },
            { "data": "updatedOn" },
            { "data": "url" }

        ],  "columnDefs": [ {
            "targets": 7,
            "data": "url",
            "render": function ( data, type, full, meta ) {
                if(data != null)
                    return '<a style="text-decoration:none;padding-left:4px;padding-right:4px;color: white;background-color: #cb2027" href="'+data+'">View</a>';
            }
        } ]


    });



    $.ajax({
        type: "GET",
        url: '/jit/app/project/projectHomeList?projectName=' + getProjectName(),
        success: function (json) {

            $("#projectTitle").text(getProjectName());
            $("#projectDescription").text(json.data.Description);
            $("#manager").text(json.data.Manager);
            $("#developers").text(json.data.Developers);
            $("#testers").text(json.data.Testers);


        }

    });


//for enabling the validation of chosen plugin fields
    $.validator.setDefaults({
        ignore: ':not(select:hidden, input:visible, textarea:visible)'
    });


//for real time validating the select
    $('#issue select').on('change', function (e) {
        $('#issue').validate().element($(this));
    });

    $('#issue').validate({
        rules: {

            title: {
                required: true,
                remote: {
                    url: "/jit/app/issues/checkIfIssueExist",
                    type: "GET"
                }
            },
            description: {
                required: true
            },
            status: {
                required: true
            },
            tracker: {
                required: true
            },
            assigned: {
                required: true
            }


        },

        highlight: function (element) {
            $(element).closest('.form-group').addClass('has-error');

        },
        unhighlight: function (element) {
            $(element).closest('.form-group').removeClass('has-error');
        },
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function (error, element) {
            if (element.attr("name") == "title") {

                $.ajax({

                    type: "GET",
                    url: '/jit/app/issues/issueTitleUrlMap?title=' + element.val(),
                    success: function (json) {
                        var $el = $("#titleError");

                        $.each(json.data, function (value, key) {

                            $el.append($("</br><span style='color:red'></span>").text("Oops, this one looks like "));
                            $el.append($("<a></a>")
                                .attr("href", key).text(value));
                        });
                    }
                });
            }

            else if (element.parent('.input-group').length) {
                error.insertAfter(element.parent());
            } else {
                error.insertAfter(element);
            }

        },
        submitHandler: function (form) {
            addIssue();
        }


    });


});

//function for adding new issue via ajax post
function addIssue() {

//populating the data from form fields
    var data = {
        title: $('input[name=title]').val(),
        description: $('#description').val(),
        completionDate: $('input[name=completionDate]').val(),
        status: $('#status').chosen().val(),
        assigned: $('#assigned').chosen().val(),
        tracker: $('#tracker').chosen().val()
    };


//ajax post
    $.ajax({

        type: "POST",
        url: "/jit/app/issues/" + getProjectName() + "/add",
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify(data),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {


        }
    });
}

//function which returns current project name
function getProjectName(){

    //splitting the current url for getting the project name
    var path = $(location).attr('href');
    var splitted = path.split('/');
    return splitted[6];
}