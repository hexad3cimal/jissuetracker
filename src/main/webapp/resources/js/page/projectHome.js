/**
 * Created by jovin on 17/2/16.
 */
$(function(){

    var path = $(location).attr('href');
    var splitted = path.split('/');
    document.getElementById("projectTitle").innerHTML = splitted[6];

    $('#issueBlock').hide();

    $("#completionDate").datetimepicker();

    //$('#tracker').selectpicker();
    //$('#assigned').selectpicker();
    //$('#status').selectpicker();




    $('#addIssue').click(function(){



    $.ajax({
        type: "GET",
        url: '/jit/app/tracker/list',
        success: function(json) {

            var $el = $("#tracker");
            $el.empty(); // remove old options
            $el.append($("<option></option>")
            );
            $.each(json.data, function(value, key) {
                $el.append($("<option></option>")
                    .attr("value", value).text(key));
            });
            $('#tracker').chosen({width: "95%"});
        }
    });


        $.ajax({
            type: "GET",
            url: '/jit/app/status/list',
            success: function(json) {

                var $el = $("#status");
                $el.empty(); // remove old options
                $el.append($("<option></option>")
                );
                $.each(json.data, function(value, key) {
                    $el.append($("<option></option>")
                        .attr("value", value).text(key));
                });
                $('#status').chosen({width: "95%"});


            }
        });




        $.ajax({
            type: "GET",
            url: '/jit/app/project/'+splitted[6]+'/userList',
            success: function(json) {

                var $el = $("#assigned");
                $el.empty(); // remove old options
                $el.append($("<option></option>"));
                $el.append($("<option></option>")
                    .attr("value", " ").text("Choose One"));
                $.each(json.data, function(value, key) {
                    $el.append($("<option></option>")
                        .attr("value", value).text(key));
                });

                $('#assigned').chosen({width: "95%"});


            }
        });

    $('#issueBlock').show();


    $('#projectBlock').hide();



});


    $.ajax({
        type: "GET",
        url: '/jit/app/project/projectHomeList?projectName='+splitted[6],
        success: function(json) {

            $("#projectDescription").text(json.data.Description);
            $("#manager").text(json.data.Manager);
            $("#developers").text(json.data.Developers);
            $("#testers").text(json.data.Testers);


        }

    });

    $.validator.setDefaults({

        ignore: ':not(select:hidden, input:visible, textarea:visible)'



    });

    $('#issue select').on('change', function(e) {
        $('#issue').validate().element($(this));
    });

    $('#issue').validate({
        rules:{

            title:{
                required:true
            },
            description:{
                required:true
            },
            status:{
                required:true
            },
            tracker:{
                required:true
            },
            assigned:{
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