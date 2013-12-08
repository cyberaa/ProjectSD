/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/24/13
 * Time: 7:45 PM
 * To change this template use File | Settings | File Templates.
 */


function togglePanel(pID) {
    var options = {};
    if ($('#'+pID).css('display') == 'none') {
        $("#"+pID).show("blind", options, 500);
    }
    else {
        $("#"+pID).hide("blind", options, 500);
    }
    return false;
}

function initializeWS() {
    connect('ws://' + window.location.host + '/chat');
}

function connect(host) {
    if ('WebSocket' in window)
        websocket = new WebSocket(host);
    else if ('MozWebSocket' in window)
        websocket = new MozWebSocket(host);
    else {
        return;
    }

    websocket.onopen    = onOpen; // set the event listeners below
    websocket.onclose   = onClose;
    websocket.onmessage = onMessage;
    websocket.onerror   = onError;
}

function onOpen(event) {
    console.log('Connected to ' + window.location.host + '.');
    makeNotification("Hello World!", "A Mariana é linda!!!!")
}

function onClose(event) {
    //FIXME: CLosed, shit
    console.log("Websocket closed!");
}

function onMessage(message) {
    not = $.parseJSON(message.data);
    if(not.notif == true) {
       alert(not.message);
    }
    else {
        alert("Stock Market: "+ not.message);
    }
}

function onError(event) {
    console.log('WebSocket error (' + event.data + ').');
}
