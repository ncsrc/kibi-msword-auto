
// Custom functionality



// globals
// needs to be global to be accessible from table
var dialog = $("#dialog");


// prepare dialog window

$(document).ready(function () {

    // prepares dialog
    dialog.dialog({
        autoOpen: false,
        height: 300,
        width: 350,
        modal: true,
        buttons: {
            "Отмена": function () {
                dialog.dialog("close");
            },
            "Отправить": getFile
        }
    });

    // sets opener button
    $('#opener').click(function () {
        dialog.dialog("open");
    });

    // used in dialog if 'OK' clicked
    function getFile() {
        var id = $('#dialog').data('id');
        var type = $('#doctype').val();

        window.location = 'docBuilder?id=' + id + '&type=' + type;
    }

});


// Observer for for fio create/update dialog appearance
// needed to set event listeners for autocompletion

var observer = new MutationObserver(function (nodes) {

    var editFirstName = $('#Edit-firstNameI')[0];   // [0] required to extract actual Node type from wrapper, otherwise contains() will throw exception

    if(nodes[0].addedNodes) {

        if(document.body.contains(editFirstName)) {

            $('#Edit-firstNameI').on('change', function (event) {
                var input = event.target.value;
                $('#Edit-firstNameR').val(input);
                $('#Edit-firstNameT').val(input);
            });

            $('#Edit-lastNameI').on('change', function (event) {
                var input = event.target.value;
                $('#Edit-lastNameR').val(input);
                $('#Edit-lastNameT').val(input);
            });

            $('#Edit-middleNameI').on('change', function (event) {
                var input = event.target.value;
                $('#Edit-middleNameR').val(input);
                $('#Edit-middleNameT').val(input);
            });

        }
    }

});

observer.observe(document.body, { childList: true });






















