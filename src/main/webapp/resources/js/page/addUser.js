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
        messages: {

            email: {
                required: "Enter an email",
                remote: "Looks like you have already registered"
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

    //remove remote email validation if it is edit action
    if ($('#id').val() !== '')
        $('#email').rules('remove', 'remote');
});

function addUser() {


    var JsonData = {
        email: $('#email').val(),
        id: $('#id').val(),
        name: $('#name').val(),
        roles: $('#role').val(),
        password: $('#password').val()

    };


    $.ajax({
        url: '/jit/app/user/add/ajax',
        type: 'POST',
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify(JsonData),
        success: function (data) {

            if (data.data == "Success") {

                $.dialog({
                    title: 'Success!',
                    content: 'User have been added successfully',
                    onClose: function(){
                        window.location.href = ctx+"/app/user/";
                    },

                });
            }else {
                $.dialog({
                    title: 'Oops!',
                    content: 'Some error occured',
                    onClose: function(){
                        window.location.href = ctx+"/app/user/";
                    },

                });

            }


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
