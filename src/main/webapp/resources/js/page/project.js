/**
 * Created by jovin on 14/2/16.
 */
$(function(){

    var token = $('input[name=csrf-token]').val()

    $('#newProject').validate({

        rules:{

            name:{
                required:true
            },
            description:{
                required:true
            }

        },

        highlight: function(element) {
            $(element).closest('.form-group').addClass('has-error');

        },
        unhighlight: function(element) {
            $(element).closest('.form-group').removeClass('has-error');
        },
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function(error, element) {
            if(element.parent('.input-group').length) {
                error.insertAfter(element.parent());
            } else {
                error.insertAfter(element);
            }
        },

        submitHandler: function(form) {
            addProject();
        }


    });

});

//var _ctx = $("meta[name='_ctx']").attr("content");
//
//// Prepend context path to all jQuery AJAX requests
//$.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
//    if (!options.crossDomain) {
//        options.url = _ctx + options.url;
//    }
//});

function addProject(){

    var data ={
        name:$('input[name=name]').val(),
        description:$('input[name=description]').val()

    };

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({

        url : "/webapp/app/project/addNew",
        type :'POST',
        contentType: "application/json",
        dataType: 'json',
        data:JSON.stringify(data),
        beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success: function(data) {


        }

    });

}
