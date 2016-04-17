/**
 * Created by jovin on 15/4/16.
 */


//csrf tokens for ajax post
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(function () {

    editOrAddChecker();

    console.log($('#userList').val());
    //for enabling the validation of chosen plugin fields
    $.validator.setDefaults({
        ignore: ':not(select:hidden, input:visible, textarea:visible)'
    });

    //for real time validating the select
    $('#newProject select').on('change', function (e) {
        $('#newProject').validate().element($(this));
    });


    $.ajax({
        type: "GET",
        url: '/jit/app/project/userDropdown',
        success: function (json) {

            var $el = $("#userSelect");
            $el.empty(); // remove old options
            $el.append($("<option></option>")
            );
            $.each(json.data, function (value, key) {
                $el.append($("<option></option>")
                    .attr("value", value).text(key));

            });

            var users = formatUserArray($('#userList').val());
            jQuery('#userSelect').val(users)
            $('#userSelect').chosen({width: "95%"});
            ;
            console.log(users[1]);

        }
    });

    $('#newProject').validate({

        rules: {

            name: {
                required: true
            },
            description: {
                required: true
            },
            userSelect: {
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
            addProject();
        }


    });

});


function addProject() {

    var mySelections = [];
    $('#userSelect option').each(function (i) {
        if (this.selected == true) {
            mySelections.push(this.value);
        }
    });

    var data = {
        name: $('input[name=name]').val(),
        description: $('input[name=description]').val(),
        users: mySelections
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


        }

    });

}

function editOrAddChecker() {
    jQuery('#userSelect').val(['tester@gmail.com'])
    jQuery('#userSelect').trigger("liszt:updated");
    var path = $(location).attr('href');
    var splitted = path.split('/');

    if (splitted[6] == "add") {
        $('#submitButton').text("Add new Project")
    } else {
        $('#submitButton').text("Update Project");


    }


}

function formatUserArray(ids) {

    var convertedArray = ids.split(',');

    for (var i = convertedArray.length - 1; i >= 0; i--) {
       convertedArray[i] = convertedArray[i].replace(/[\[\] ]+/g, '');
    }

    return convertedArray
}
