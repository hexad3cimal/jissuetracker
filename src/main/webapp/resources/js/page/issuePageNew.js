/**
 * Created by jovin on 11/4/16.
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
    $.fn.editable.defaults.mode = 'inline';

    $(".fileLink").each(function () {
        $(this).val('');
    });

$(document).on('click', '#issueStatus', function () {

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

                $('#issues-block').append(json.data)

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

            url:"/jit/app/issues/deleteFile",
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


//function for adding new issue via ajax post
function addUpdate() {

    var files = [];

    $(".fileLink").each(function () {
        files.push($(this).val())
    });


//populating the data from form fields
    var data = {
        updateText: $('#updateText').val(),
        files: files

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
