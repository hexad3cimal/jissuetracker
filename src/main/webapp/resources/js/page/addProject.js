/**
 * Created by jovin on 15/4/16.
 */


//csrf tokens for ajax post
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(function () {

    editOrAddChecker();

    //for enabling the validation of chosen plugin fields
    $.validator.setDefaults({ignore: ":hidden:not(select)"}) //for all select


    jQuery.validator.addMethod("notEqual", function (value, element, param) {
        return this.optional(element) || value != param;
    }, "Please select at least one user");

    //for real time validating the select
    $('#newProject select').on('change', function (e) {
        $('#newProject').validate().element($(this));
    });


    //for populating user dropdown
    $.ajax({
        type: "GET",
        url: '/jit/app/project/userDropdown',
        success: function (json) {

            var $el = $("#userSelect");
            $el.empty(); // remove old options
            $el.append($("<option></option>")
            );
            if (json.data != "Null") {
                $.each(json.data, function (value, key) {
                    $el.append($("<option></option>")
                        .attr("value", value).text(key));

                });
            }

            var users = formatUserArray($('#userList').val());
            $('#userSelect').val(users)
            $('#userSelect').chosen({width: "95%"});


        }
    });


    //for validating project form
    $('#newProject').validate({

        rules: {

            name: {
                required: true
            },
            description: {
                required: true
            },
            userSelect: {
                required: true,
                notEqual: ""
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
            if (element.next().hasClass('chosen-container')) {

                error.insertAfter(element.next());
            }
            else if (element.parent('.bmd-input').length) {
                error.insertAfter(element.parent());
            }

            else {
                error.insertAfter(element);
            }
        },

        submitHandler: function (form) {

            addProject();
        }


    });

});


//function to add/update project
function addProject() {


    var data = {
        name: $('input[name=name]').val(),
        description: $('#description').val(),
        id: $('input[name=projectId]').val(),
        users: $('#userSelect').chosen().val()
    };

    $.ajax({

        url: "/jit/app/project/addNew",
        type: 'POST',
        contentType: "application/json",
        dataType: 'json',
        data: JSON.stringify(data),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {

            if (data.data == "Success") {

                $.dialog({
                    title: 'Success!',
                    content: 'Project have been added successfully',
                    onClose: function(){
                        window.location.href = ctx+"/app/project/";

                    },

                });
            }else {
                $.dialog({
                    title: 'Oops!',
                    content: 'Some error occured',

                });

        }
        }

    });

}

//function to format texts in the dom
function editOrAddChecker() {

    //jQuery('#userSelect').val(['tester@gmail.com'])
    //jQuery('#userSelect').trigger("liszt:updated");
    var path = $(location).attr('href');
    var splitted = path.split('/');

    if (splitted[6] == "add") {
        $('#submitButton').text("Add new Project")
        document.title = "Add new Project";

    } else {
        $('#submitButton').text("Update Project");
        $('.panel-title').text("Update Project");
        document.title = "Update Project";

    }


}

//function to format the values that is to be populated to the
// multi select box in case of edit
function formatUserArray(ids) {
    if (ids != null) {
        var convertedArray = ids.split(',');

        for (var i = convertedArray.length - 1; i >= 0; i--) {
            convertedArray[i] = convertedArray[i].replace(/[\[\] ]+/g, '');
        }
    }
    return convertedArray
}