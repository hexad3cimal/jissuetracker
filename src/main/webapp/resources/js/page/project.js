/**
 * Created by jovin on 14/2/16.
 */
$(function(){

    $('#userSelect').selectpicker();



    $.ajax({
        type: "GET",
        url: '/jit/app/project/userDropdown',
        success: function(json) {

            var $el = $("#userSelect");
            $el.empty(); // remove old options
            $el.append($("<option></option>")
            );
            $.each(json.data, function(value, key) {
                $el.append($("<option></option>")
                    .attr("value", value).text(key));
                $('#userSelect').selectpicker('refresh');

            });

        }
    });


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

    var mySelections = [];
    $('#userSelect option').each(function(i) {
        if (this.selected == true) {
            mySelections.push(this.value);
        }
    });

    console.log(mySelections);
    var data ={
        name:$('input[name=name]').val(),
        description:$('input[name=description]').val(),
        users:mySelections
    };

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({

        url : "/jit/app/project/addNew",
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