
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


// event listeners to fio fields, for autocompletion

$('#Edit-firstNameI').on('change', function (event) {
    var input = event.target.value;
    $('Edit-firstNameR').val(input);
});

$('#Edit-lastNameI').on('change', function (event) {
    var input = event.target.value;
    $('Edit-lastNameR').val(input);
});

$('#Edit-middleNameI').on('change', function (event) {
    var input = event.target.value;
    $('Edit-middleNameR').val(input);
});



/*
    ids:
    Edit-firstNameI
    Edit-lastNameI
    Edit-middleNameI

    Edit-firstNameR
    Edit-lastNameR
    Edit-middleNameR
    Edit-firstNameT
    Edit-lastNameT
    Edit-middleNameT

 */




















