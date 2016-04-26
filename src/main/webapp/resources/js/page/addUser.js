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
    editOrAddChecker();

    $.ajax({

        url: '/jit/app/roles/list',
        type: 'GET',
        success: function (json) {

            var $el = $("#role");
            $el.empty(); // remove old options
            $el.append($("<option></option>"));
            $.each(json.data, function (value, key) {
                $el.append($("<option></option>")
                    .attr("value", value).text(key));
            });

            $("#role").val($('#roleIds').val());

        }
    })


    $('#user').validate({
        rules: {
            name: {
                required: true

            },
            email: {
                required: true,
                remote: {
                    url: "/jit/app/user/doesUserExist",
                    type: "GET"
                }
            },
            password: {
                required: true
            },
            role: {
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
            addUser();
        }

    });

});

function addUser() {

    //var stack_center = {"dir1": "down", "dir2": "right", "firstpos1": 25, "firstpos2": ($(window).width() / 2) - (Number(PNotify.prototype.options.width.replace(/\D/g, '')) / 2)};
    //$(window).resize(function(){
    //    stack_center.firstpos2 = ($(window).width() / 2) - (Number(PNotify.prototype.options.width.replace(/\D/g, '')) / 2);
    //});
    //PNotify.prototype.options.styling = "bootstrap3";

    var JsonData = {
        email: $('#email').val(),
        id: $('#id').val(),
        name: $('#name').val(),
        role: $('#role').val(),
        password: $('#password').val()

    };


    $.ajax({
        url: '/jit/app/user/addNew',
        type: 'POST',
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify(JsonData),
        success: function (data) {

            var path = $(location).attr('href');
            var splitted = path.split('/');
            if (splitted[6] == "profile")
                window.location.href = "http://localhost:8080/jit/app/user/profile";
            else
                window.location.href = "http://localhost:8080/jit/app/user/";

        }
    });

}

//function to format texts in the dom
function editOrAddChecker() {

    var path = $(location).attr('href');
    var splitted = path.split('/');

    if (splitted[6] == "add") {
        $('#submitButton').val("Add new user")
        document.title = "Add new User";

    } else {
        $('#submitButton').val("Update User");
        $('.panel-title').text("Update User");
        document.title = "Update User";

    }


}
