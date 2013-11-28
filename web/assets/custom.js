/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/24/13
 * Time: 7:45 PM
 * To change this template use File | Settings | File Templates.
 */


function togglePanel(pID) {
    if ($('#'+pID).css('display') == 'none') {
        $("#"+pID).show();
    }
    else {
        $("#"+pID).hide();
    }
}

/*$("#submitIdea").click(function() {
    alert(1);
    $.ajax({
        type: "POST",
        url: "submitIdeaAction.action",
        data: {
            "topic" : $("#topic").val(),
            "text" :  $("#ideaText").val(),
            "investment" :  $("#investment").val()
        },
        success: function() {
            var str = "";
            str += "<div class='alert alert-success alert-dismissable'>";
            str += "   <button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button>";
            str += "   <strong>Success!</strong> You're idea was submited successfully";
            str += "</div>";

            $("#alertDiv").html(str);
        },
        error: function() {
            var str = "";
            str += "<div class='alert alert-danger alert-dismissable'>";
            str += "   <button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button>";
            str += "   <strong>Error!</strong> An error ocurred during operation";
            str += "</div>";
                                       s
            $("#alertDiv").html(str);
        }
    });
});*/
