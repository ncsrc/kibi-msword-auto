
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

    var person = {
        first: '',
        middle: '',
        last: ''
    };


    var firstNameI = $('#Edit-firstNameI');
    var lastNameI = $('#Edit-lastNameI');
    var middleNameI = $('#Edit-middleNameI');

    var firstNameR = $('#Edit-firstNameR');
    var lastNameR = $('#Edit-lastNameR');
    var middleNameR = $('#Edit-middleNameR');

    var firstNameD = $('#Edit-firstNameD');
    var lastNameD = $('#Edit-lastNameD');
    var middleNameD = $('#Edit-middleNameD');

    if(nodes[0].addedNodes) {

        if(document.body.contains(firstNameI[0])) {  // [0] required to extract actual Node type from wrapper, otherwise contains() will throw exception

            firstNameI.on('change', function (event) {
                person.first = event.target.value;

                var last = lastNameI.val();
                var middle = middleNameI.val();
                if(last.length > 0 && middle.length > 0) {
                    person.last = last;
                    person.middle = middle;
                    var personR = petrovich(person, 'genitive');
                    var personD = petrovich(person, 'dative');
                    setFio(personR, personD);
                }
            });

            lastNameI.on('change', function (event) {
                person.last = event.target.value;

                var first = firstNameI.val();
                var middle = middleNameI.val();
                if(first.length > 0 && middle.length > 0) {
                    person.first = first;
                    person.middle = middle;
                    var personR = petrovich(person, 'genitive');
                    var personD = petrovich(person, 'dative');
                    setFio(personR, personD);
                }
            });

            middleNameI.on('change', function (event) {
                person.middle = event.target.value;

                var first = firstNameI.val();
                var last = lastNameI.val();
                if(first.length > 0 && last.length > 0) {
                    person.first = first;
                    person.last = last;
                    var personR = petrovich(person, 'genitive');
                    var personD = petrovich(person, 'dative');
                    setFio(personR, personD);
                }
            });
        }
    }


    function setFio(personR, personD) {
        firstNameR.val(personR.first);
        firstNameD.val(personD.first);

        lastNameR.val(personR.last);
        lastNameD.val(personD.last);

        middleNameR.val(personR.middle);
        middleNameD.val(personD.middle);
    }

});

observer.observe(document.body, { childList: true });





















