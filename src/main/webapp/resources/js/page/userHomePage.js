/**
 * Created by jovin on 19/2/16.
 */
$(function(){

    $.ajax({
        type:"GET",
        url:'/jit/app/user/homepageData/'+getLoggedInUserId(),
        success:function(json){
            if(json.data.issueCount != null){
                $('#allIssuesNo').text(json.data.issueCount)
            }
            if(json.data.unreadIssueCount != null){
                $('#newIssuesNo').text(json.data.unreadIssueCount)
            }
        }

    });


    $.ajax({
        type: "GET",
        url: '/jit/app/project/'+getLoggedInUserId()+'/projects',
        success:function(json) {
            if(json.data !=null) {
                $('#projectTable').append('<table></table>');
                var table = $('#projectTable').children();
                $.each(json.data, function (key, value) {
                    table.append('<tr><td><a href=' + value + '>' + key + '</a></td></tr>');
                });
            }

        }
    });
});



//function which returns current project name
function getLoggedInUserId() {

    return $('#loggedinUserId').val();
}