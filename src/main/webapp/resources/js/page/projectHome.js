/**
 * Created by jovin on 17/2/16.
 */
$(function(){

    $('#issueBlock').hide();

    $("#completionDate").datetimepicker();

    $('#tracker').selectpicker();


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
                $('#tracker').selectpicker('refresh');

            });

        }
    });

    $('#issueBlock').show();


    $('#projectBlock').hide();



});

    var path = $(location).attr('href');
    var splitted = path.split('/');
    document.getElementById("projectTitle").innerHTML = splitted[6];
    $.ajax({
        type: "GET",
        url: '/jit/app/project/projectHomeList?projectName='+splitted[6],
        success: function(json) {

            document.getElementById("projectDescription").innerHTML = json.data.Description;
            document.getElementById("manager").innerHTML = json.data.Manager;
            document.getElementById("developers").innerHTML = json.data.Developers;
            $("#testers").text(json.data.Testers);


        }

    });


});