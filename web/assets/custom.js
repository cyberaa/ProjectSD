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
