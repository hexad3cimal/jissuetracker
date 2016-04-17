/**
 * Created by jovin on 11/4/16.
 */

//csrf tokens for ajax post
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");


$(function () {
    $.fn.editable.defaults.mode = 'inline';

    $.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        }
    });

    $('#updateBlock').hide();

    $("#issueStatus").click(function (){

        $.ajax({
            type: "GET",
            url: '/jit/app/status/list',
            success: function (json) {


                $('#issueStatus').editable({
                    type: 'select',
                    pk: 1,
                    url: "/jit/app/issues/updateStatus/" + getIssueId(),

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

            $("#createdBy").text("Created by: " + json.data.userByCreatedById.name);
            $("#createdDate").text("Created on: " + json.data.createdOn);
            $("#issueTitle").text(json.data.title);
            $("#issueDescription").text(json.data.description);

            document.title = json.data.title;


            var updates = json.data.issuesUpdateses;

            if (updates.length > 0) {

                populateUpdates(updates);

            }

            if (json.data.updatable.toString() == "true") {

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

            logger("Reached submit handler");
            addUpdate();
        }


    });


});

//function for adding new issue via ajax post
function addUpdate() {

//populating the data from form fields
    var data = {
        updateText: $('#updateText').val()
    };

    console.log(data.updateText);

//ajax post
    $.ajax({

        type: "POST",
        url: "/jit/app/issues/updateIssue/" + getIssueId(),
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify(data),
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
        logger(updates[i]);
        $('#updatesListBlock').append('<div class="panel panel-default">' +
            '<div class="panel-body">' +
            '<div class="row col-sm-12 col-lg-12 col-md-12">' +
            '<div class="form-group">&nbsp;&nbsp;'
            + '</div>' +

            '<div class="row col-sm-10 col-lg-10 col-md-10">'
            + '<div class="issueDescription" id="updateDescription' + i + '"></div>'
            + '</div>' +
            '<div class="row col-sm-2 col-lg-2 col-md-2">'
            + '<div class="createdBy" id="updatedBy' + i + '"></div>'
            + '<div class="createdDate" id="updatedDate' + i + '"></div>'
            + '</div>'

            + '</div>'
            + '</div>'
            + '</div>');


        if (updates[i].updates != null)
            $("#updateDescription" + i).text(updates[i].updates.toString());
        if (updates[i].user != null)
            $("#updatedBy" + i).text(updates[i].user.name.toString());
        if (updates[i].date != null)
            $("#updatedDate" + i).text(updates[i].date.toString());


    }


    function logger(logger){

        console.log(logger);

    }


}