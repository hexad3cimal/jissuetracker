/**
 * Created by jovin on 17/2/16.
 */
//csrf tokens for ajax post
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var files = 0;

$.ajaxSetup({
    beforeSend: function (xhr) {
        xhr.setRequestHeader(header, token);
    }
});
$(function () {

    $(".fileLink").each(function () {
        $(this).val('');
    });

    $("#fileUpload").change(function () {

        saveMedia();
    });


    $(document).on('click', '.uploadedFiles', function () {
        var data = {"fileLocation": $(this).find('.fileLink').val()};
        $(this).closest('.fileLink').val();
        $(this).closest('.uploadedFiles').remove();
        $('#fileLimit').text('')
        files--;
        $.ajax({

            url:"/jit/app/issues/deleteFile",
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (data) {
                console.log(data.data);
            }

        });


    });




    $('#reload').click(function () {
        location.reload();
    });

    document.title = getProjectName() + ' - Home';

    $("#projectTitle").val(getProjectName());


    $("#completionDate").datetimepicker({

        container:'#issues'
    });

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
            url: '/jit/app/priority/list',
            success: function (json) {

                var $el = $("#priority");
                $el.empty(); // remove old options
                $el.append($("<option></option>")
                );
                $.each(json.data, function (value, key) {
                    $el.append($("<option></option>")
                        .attr("value", value).text(key));
                });
                $('#priority').chosen({width: "95%"});


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



    });


    $('#viewAllIssues').click(function () {

        $('#issueBlock').hide();


        $('#projectBlock').hide();

        //issueTable.ajax.reload();


    });


    $('#issues-by-you').click(function () {
        $("#issues-assigned-to-you").parent().removeClass("active");
        $("#all-issues").parent().removeClass("active");
        $('#request-type').val("issues-by-you")

        $(this).parent().addClass("active")
        issueTable.ajax.url('/jit/app/issues/created/' + getProjectName()).load();

    });


    $('#bug').click(function () {
        $("#feature").removeClass("active");
        $("#modification").removeClass("active");
        $(this).addClass("active")
        issueTable.ajax.url('/jit/app/issues/filtered/' + getProjectName()+'/'+$('#request-type').val()+'/'+ $('#open-close').val()+'/Bug').load();

    });

    $('#feature').click(function () {
        $("#bug").removeClass("active");
        $("#modification").removeClass("active");
        $(this).addClass("active")
        issueTable.ajax.url('/jit/app/issues/filtered/' + getProjectName()+'/'+$('#request-type').val()+'/'+ $('#open-close').val()+'/Feature').load();

    });

    $('#modification').click(function () {
        $("#feature").removeClass("active");
        $("#bug").removeClass("active");
        $(this).addClass("active")
        issueTable.ajax.url('/jit/app/issues/filtered/' + getProjectName()+'/'+$('#request-type').val()+'/'+ $('#open-close').val()+'/Modification').load();

    });


    $('#open-issues').click(function () {
        $("#closed-issues").removeClass("active");
        $('#open-close').val("Open")
        $(this).addClass("active")
        issueTable.ajax.url('/jit/app/issues/filtered/' + getProjectName()+'/'+$('#request-type').val()+'/Open').load();

    });
    $('#closed-issues').click(function () {
        $("#open-issues").removeClass("active");
        $('#open-close').val("Closed")
        $(this).addClass("active")
        issueTable.ajax.url('/jit/app/issues/filtered/' + getProjectName()+'/'+$('#request-type').val()+'/Closed').load();

    });

    $('#issues-assigned-to-you').click(function () {
        $("#issues-by-you").parent().removeClass("active");
        $("#all-issues").parent().removeClass("active");
        $('#request-type').val("issues-assigned-to-you")


        $(this).parent().addClass("active")

        issueTable.ajax.url('/jit/app/issues/assigned/' + getProjectName()).load();


    });


    $('#all-issues').click(function () {
        $("#issues-assigned-to-you").parent().removeClass("active");
        $("#issues-by-you").parent().removeClass("active");
        $('#request-type').val("all-issues")


        $(this).parent().addClass("active")

        issueTable.ajax.url('/jit/app/issues/' + getProjectName()).load();


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
            "dataSrc": function (json) {


                if (json.data == null)
                    json.data = []
                return json.data
            }
        },
        "columns": [
            {"data": "question",
                "render":function(data, type, full, meta){


                    return '<li class="list-group-item">'+
                        '<div class="media">'+
                        '<i class="fa fa-cog pull-left"></i>'+
                        '<div class="media-body">'+
                        '<a href="/jit/app/issues/get/'+full.id+'" <strong>'+full.title+'</strong></a>'+'&nbsp;'+
                        '<span class="label label-danger">'+full.status+'</span>'+'&nbsp;'+
                        '<span class="label label-info">'+full.tracker+'</span>'+'&nbsp;'+
                        '<span id="issueId" class="number pull-right">'+'#'+full.id+'</span>'+
                        '<p class="info">Opened by '+full.createdBy+' at '+full.createdOn+'</p>'+
                        '</div>'+
                        '</div>'+
                        '</li>'
                }
            }

        ]


    });


    $.ajax({
        type: "GET",
        url: '/jit/app/project/projectHomeList?projectName=' + getProjectName(),
        success: function (json) {

            $("#projectTitle").text(getProjectName());

            if (json.data.Description != null)
                $("#projectDescription").text(json.data.Description);

            if (json.data.Manager != null)
                $("#manager").text(json.data.Manager);

            if (json.data.Developers != null)
                $("#developers").text(json.data.Developers);

            if (json.data.Testers != null)
                $("#testers").text(json.data.Testers);

            if (json.data.Reporters != null)
                $("#reporters").text(json.data.Reporters);


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
            },
            priority: {
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

    var files = [];

    $(".fileLink").each(function () {
        files.push($(this).val())
    });


//populating the data from form fields
    var data = {
        title: $('input[name=title]').val(),
        description: $('#description').val(),
        completionDate: $('input[name=completionDate]').val(),
        status: $('#status').chosen().val(),
        assigned: $('#assigned').chosen().val(),
        tracker: $('#tracker').chosen().val(),
        priority: $('#priority').chosen().val(),
        files: files

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
function getProjectName() {

    //splitting the current url for getting the project name
    var path = $(location).attr('href');
    var splitted = path.split('/');
    return splitted[6];
}


function saveMedia() {


    $('#spin').append('<div class="bmd-spinner bmd-spinner-default bmd-spinner-sm">' +
        '<svg viewBox="0 0 50 50">' +
        '<circle cx="25" cy="25" r="20"></circle>' +
        '</svg>')
    var mySelections = [];
    var formData = new FormData();
    formData.append('file', $('input[type=file]')[0].files[0]);
    console.log("form data " + formData);
    console.log($('input[type=file]')[0].files[0].size);
    if (files < 3) {

        if ($('input[type=file]')[0].files[0].size > 2097152) {
            $('#files').append('<div id="fileLimit">Files with size less than 2 mb can be uploaded</div>');


        } else {
            $.ajax({
                url: '/jit/app/issues/messageFiles',
                data: formData,
                processData: false,
                contentType: false,
                type: 'POST',
                success: function (data) {
                    files++;
                    $.each(data.data, function (value, key) {
                        if (value != "Limit Reached") {
                            $('#files').append('<div class="uploadedFiles"><span>' + value + '</span><button class="btn btn-warning btn-xs delete"> Delete </button> <input type="hidden" value=" '+value+'&'+key+'" class="fileLink"/></div>')


                            $('#spin').empty();
                        } else
                            $('#files').append('<div id="fileLimit">Upto 3 files can be uploaded</div>');

                        $('#spin').empty();

                    });


                },
                error: function (err) {
                }


            });
        }
    }
    else {
        $('#files').append('<div id="fileLimit">Upto 3 files can be uploaded</div>');
        $('#spin').empty();

    }


}