/**
 * Created by jovin on 19/2/16.
 */
$(function(){

    var path = $(location).attr('href');
    var splitted = path.split('/');
    console.log(splitted);


    $.ajax({
        type: "GET",
        url: '/jit/app/project/'+splitted[6]+'/projects',
        success:function(json) {
            $('#projectTable').append('<table></table>');
            var table = $('#projectTable').children();
            $.each(json.data, function (key, value) {
                table.append( '<tr><td><a href=' + value + '>' + key + '</a></td></tr>' );
            console.log(key + " " + value)
        });

    }
    });
});