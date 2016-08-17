/**
 * Created by jovin on 11/4/16.
 */

//csrf tokens for ajax post
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$.ajaxSetup({
    beforeSend: function (xhr) {
        xhr.setRequestHeader(header, token);
    }
});
$(function () {
    $.fn.editable.defaults.mode = 'inline';


    $('#updateBlock').hide();

    $("#issueStatus").click(function () {

        $.ajax({
            type: "GET",
            url: '/jit/app/status/list',
            success: function (json) {


                $('#issueStatus').editable({
                    type: 'select',
                    pk: 1,
                    url: "/jit/app/issues/updateStatus/" + getIssueId(),
                    success :function(){
                        location.reload();
                    },
                    title: 'Update status',
                    source: json.data
                });

            }
        });


    });


    $.ajax({
        type: "GET",
        url: '/jit/app/issues/ajax/' + getIssueId(),
        success: function (json) {
            if (json.data != null) {

                $("#createdBy").text("Created by: " + json.data.userByCreatedById.name);
                $("#createdDate").text("Created on: " + json.data.createdOn);
                $("#issueTitle").text(json.data.title);
                $("#issueDescription").text(json.data.description);

                document.title = json.data.title;


                var updates = json.data.issuesUpdateses;

                if (updates.length > 0) {

                    populateUpdates(updates);

                }

                if (json.data.updatable == "true" && json.data.status.name != "Closed") {

                    $('#updateBlock').show();

                }


                if (json.data.trackers.name.toString() == "Bug") {
                    $("#issueType").addClass('issueTypeBug');
                    $("#issueType").text(json.data.trackers.name);

                } else if (json.data.trackers.name.toString() == "Modification") {
                    $("#issueType").addClass('issueTypeModification');
                    $("#issueType").text(json.data.trackers.name);

                } else {
                    $("#issueType").addClass('issueTypeFeature');
                    $("#issueType").text(json.data.trackers.name);
                }


                if (json.data.status.name.toString() == "Closed") {
                    $("#issueStatus").addClass('issueStatusClosed');
                    $("#issueStatus").text(json.data.status.name);

                } else {
                    $("#issueStatus").addClass('issueStatusOpen');
                    $("#issueStatus").text(json.data.status.name);
                }

            }else
                $("#issueDescription").text("Issue does not exist");

        }
    });


    $('#issueUpdateForm').validate({
        rules: {

            updateText: {
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
            if (element.parent('.input-group').length) {
                error.insertAfter(element.parent());
            } else {
                error.insertAfter(element);
            }

        },
        submitHandler: function (form) {


            addUpdate();
        }


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

            url: ctx + "/app/inbox/deleteFile",
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (data) {
                console.log(data.data);
            }

        });


    });


});



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
                url: ctx + '/app/inbox/messageFiles',
                data: formData,
                processData: false,
                contentType: false,
                type: 'POST',
                success: function (data) {
                    files++;
                    $.each(data.data, function (value, key) {
                        if (value != "Limit Reached") {
                            $('#files').append('<div class="uploadedFiles"><span>' + value + '</span><button class="btn btn-warning btn-xs delete"> Delete </button> <input type="hidden" value=" ' +key+'" class="fileLink"/></div>'
                            );

                            $('#spin').empty();
                            addToast('info', 'up', "File uploaded successfully")


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


//function for adding new issue via ajax post
function addUpdate() {


//populating the data from form fields
    var data = {
        updateText: $('#updateText').val()
    };


//ajax post
    $.ajax({

        type: "POST",
        url: "/jit/app/issues/updateIssue/" + getIssueId(),
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify(data),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {

            location.reload();


        }
    });
}

//function which returns issue id
function getIssueId() {

    //splitting the current url for getting the issue id
    var path = $(location).attr('href');
    var splitted = path.split('/');
    return splitted[7];
}

function populateUpdates(updates) {

    for (i = 0; i < updates.length; i++) {

        $('#updatesListBlock').append('<div class="panel panel-default">' +
            '<div class="panel-body">' +
            '<div class="row col-sm-12 col-lg-12 col-md-12">' +
            '<div class="row col-sm-10 col-lg-10 col-md-10">'
            + '<div class="issueDescription" id="updateDescription' + i + '"></div>'
            + '</div>' +
            '<div class="row col-sm-2 col-lg-2 col-md-2 ">'
            + '<div class="btn custom-button-orange btn-xs" id="updatedBy' + i + '"></div>'
            + '<div class="btn custom-button-grey btn-xs" id="updatedDate' + i + '"></div>'
            + '</div>'

            + '</div>'
            + '</div>'
            + '</div>');


        if (updates[i].updates != null)
            $("#updateDescription" + i).text(updates[i].updates.toString());
        if (updates[i].updatedBy != null) {
            $("#updatedBy" + i).html('<span class="glyphicon glyphicon-user"></span>' + ' ' + updates[i].updatedBy);

        }
        if (updates[i].date != null)
            $("#updatedDate" + i).html('<span class="glyphicon glyphicon-calendar"></span>' + ' ' + updates[i].date.toString());


    }


}