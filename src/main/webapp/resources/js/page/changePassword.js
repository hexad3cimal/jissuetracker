/**
 * Created by jovin on 20/4/16.
 */

//csrf tokens for ajax post
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

//adding csrf tokens in all ajax
$.ajaxSetup({
    beforeSend: function (xhr) {
        xhr.setRequestHeader(header, token);
    }
});


$(function () {

    //calling function to check whether action is edit or add



    $('#user').validate({
        rules: {

            password: {
                required: true
            },
            password2: {
                equalTo : "#password"
            },

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
            addUser();
        }

    });

});

function addUser() {


    var JsonData = {
        id: $('#id').val(),
       password: $('#password').val()

    };


    $.ajax({
        url: '/jit/app/user/addNew',
        type: 'POST',
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify(JsonData),
        success: function (data) {
                window.location.href = "http://localhost:8080/jit/app/user/";

        }
    });

}



