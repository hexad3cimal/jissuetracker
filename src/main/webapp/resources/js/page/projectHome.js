/**
 * Created by jovin on 17/2/16.
 */
$(function(){

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
            document.getElementById("testers").innerHTML = json.data.Testers;


        }

    });

});