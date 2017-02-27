<%@ page contentType="text/html" pageEncoding="utf-8" %>
<html lang="en">
<head>
    <script src="scripts/jquery-3.1.1.min.js" type="text/javascript"></script>
    <script src="scripts/jquery-ui/jquery-ui.js" type="text/javascript"></script>
    <script src="scripts/jtable/jquery.jtable.min.js" type="text/javascript"></script>
    <script src="scripts/jtable/localization/jquery.jtable.ru.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="scripts/jtable/themes/metro/blue/jtable.css">
    <link rel="stylesheet" type="text/css" href="scripts/jquery-ui/jquery-ui.css">
    <link rel="stylesheet" type="text/css" href="style.css">
    <link rel="icon" href="favicon.png">

    <meta charset="UTF-8">
    <title>MSWord-auto</title>
</head>
<body>

<div class="wrapper">
    <div class="header"></div>
    <div id="DateTable"></div>
    <div id="GekTable"></div>
    <div id="StudentsTable"></div>
    <div id="dialog" title="Создать документ">
        <p>Выберите тип документа</p>
        <select name="doctype" id="doctype">
            <option id="gos" value="proto_1">Протокол по приему гос экзамена</option>
            <option id="vcr" value="proto_2">Протокол по защите ВКР</option>
        </select>
    </div>
</div>

<script>
    // add event listeners to options in dialog

    $('#gos').addEventListener('click', function (event) {
        var docType = event.target.id;
        dialog.data('type', docType);
    });

    $('#vcr').addEventListener('click', function (event) {
        var docType = event.target.id;
        dialog.data('type', docType);
    });


    // prepare dialog

    $(document).ready(function () {
        dialog = $("#dialog").dialog({
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

        $('#opener').click(function () {
            dialog.dialog("open");
        });

        function getFile() {
            var id = $('#dialog').data('id');
            var type = $('#dialog').data('type');

            window.location = 'docBuilder?id=' + id + '&type=' + type;

//            $.ajax({
//                url: 'docBuilder?id=' + id,
//                type: 'GET',
//                success: function () {
//                    window.location = 'docBuilder';
//                },
//                // TODO set back
//                error: $("<div title='Ошибка'>Ошибка сервера</div>").dialog() // TODO get error response from server
////                error: alert(id)
//            });
        }

    });
</script>


<%-- tables --%>

<script type="text/javascript">

    $(document).ready(function () {

        //--Prepare jTable--
        // Table with info about dates
        $('#DateTable').jtable({
            title: 'Даты',
            actions: {
                listAction: 'dateHandler?table=date&action=list',
                createAction: 'dateHandler?table=date&action=create',
                updateAction: 'dateHandler?table=date&action=update',
                deleteAction: 'dateHandler?table=date&action=delete'
            },
            fields: {
                id: {
                    title: 'id',
                    key: true,
                    create: false,
                    edit: false,
                    list: false // true for debug
                },
                gosDate: {
                    title: 'По приему гос экзамена',
                    width: '50%',
                    type: 'date'
                },
                vcrDate: {
                    title: 'По защите ВКР',
                    width: '50%',
                    type: 'date'
                }
            }
        });


//         Table about GEK members
            $('#GekTable').jtable({
                title: 'Государственная экзаменационная комиссия',
                actions: {
                    listAction: 'gekHeadHandler?action=list',
                    createAction: 'gekHeadHandler?action=create',
                    updateAction: 'gekHeadHandler?action=update',
                    deleteAction: 'gekHeadHandler?action=delete'
                },
                fields: {
                    head: {
                        title: 'Председатель',
                        key: true,
                        create: true,
                        list: true,
                        edit: true
                    },
                    subhead: {
                        title: 'Заместитель'
                    },
                    secretary: {
                        title: 'Секретарь'
                    },
                    members: {
                        title: 'Члены ГЭК',
                        width: '5%',
                        sorting: false,
                        edit: false,
                        create: false,
                        display: function (data) {
                            //Create an image that will be used to open child table
                            var $img = $('<img src="scripts/jtable/themes/lightcolor/blue/list_metro.png" title="Список членов ГЭК" />');
                            //Open child table when user clicks the image
                            $img.click(function () {
                                $('#GekTable').jtable('openChildTable',
                                    $img.closest('tr'),
                                    {
                                        title: 'Члены ГЭК',
                                        actions: {
                                            listAction: 'gekMembersHandler?action=list',
                                            createAction: 'gekMembersHandler?action=create',
                                            updateAction: 'gekMembersHandler?action=update',
                                            deleteAction: 'gekMembersHandler?action=delete'
                                        },
                                        fields: {
                                            head: {     // TODO remove it
                                                create: false,
                                                edit: false,
                                                list: false,
                                                type: 'hidden'
                                            },
                                            member: {
                                                key: true,
                                                create: true,
                                                edit: true,
                                                list: true,
                                                title: 'Фамилия И.О.'
                                            }
                                        }
                                    }, function (data) { //opened handler
                                        data.childTable.jtable('load');
                                    });
                            });
                            //Return image to show on the person row
                            return $img;
                        }
                    }
                }

            });

            // todo DON'T FORGET TO MAP FIELD NAMES TO NEW NAMES IN JAVA CLASSES, AND ADD NEW FIELDS
            // Table about students
            $('#StudentsTable').jtable({
                title: 'Студенты',
                actions: {
                    listAction: 'studentHandler?action=list',
                    createAction: 'studentHandler?action=create',
                    updateAction: 'studentHandler?action=update',
                    deleteAction: 'studentHandler?action=delete'
                },
                fields: {
                    id: {
                        key: true,
                        list: true,
                        create: false,
                        update: false,
                        width: '5%'
                    },
                    firstNameI: {
                        title: 'Имя И.П.',
                        width: '20%'
                    },
                    lastNameI: {
                        title: 'Фамилия И.П.',
                        width: '20%'
                    },
                    middleNameI: {
                        title: 'Отчество И.П.',
                        width: '20%'
                    },
                    firstNameR: {
                        title: 'Имя Р.П.',
                        list: false
                    },
                    lastNameR: {
                        title: 'Фамилия Р.П.',
                        list: false
                    },
                    middleNameR: {
                        title: 'Отчество Р.П.',
                        list: false
                    },
                    firstNameT: {
                        title: 'Имя Т.П.',
                        list: false
                    },
                    lastNameT: {
                        title: 'Фамилия Т.П.',
                        list: false
                    },
                    middleNameT: {
                        title: 'Отчество Т.П.',
                        list: false
                    },
                    course: {
                        title: 'Программа обучения', // Образовательная программа
                        width: '25%',
                        sorting: false,
                        edit: false,
                        create: false,
                        display: function (studentData) {
                            //Create an image that will be used to open child table
                            var $img = $('<img src="scripts/jtable/themes/lightcolor/blue/list_metro.png" title="Образовательная программа" />');
                            //Open child table when user clicks the image
                            $img.click(function () {
                                // for passing in listing operation
                                var studentId = studentData.record.id;

                                $('#StudentsTable').jtable('openChildTable',
                                    $img.closest('tr'),
                                    {
                                        title: studentData.record.lName + ' ' + studentData.record.fName + ' - образовательная программа',
                                        actions: {
                                            listAction: 'courseHandler?action=list&studentId='+studentId,
                                            createAction: 'courseHandler?action=create&studentId='+studentId,
                                            updateAction: 'courseHandler?action=update&studentId='+studentId,
                                            deleteAction: 'courseHandler?action=delete&studentId='+studentId,
                                        },
                                        fields: {
                                            name: {
                                                title: 'Направление/специальность',
                                                options: {
                                                    'Бизнес-информатика':'Бизнес-информатика',
                                                    'Торговое дело':'Торговое дело'
                                                }
                                            },
                                            profile: {
                                                title: 'Профиль',
                                                dependsOn: 'name',
                                                options: function(data) {
                                                    return 'courseHandler?options=' + data.dependedValues.name;
                                                }
                                            },
                                            qualification: {
                                                title: 'Квалификация',
                                                options: {'Бакалавр':'Бакалавр', 'Магистр':'Магистр'}
                                            },
                                            code: {
                                                create: false,
                                                edit: false,
                                                list: false
                                                // TODO set hidden
                                            }
                                        }
                                    }, function (data) { //opened handler
                                        data.childTable.jtable('load');
                                    });
                            });
                            //Return image to show on the person row
                            return $img;
                        }
                    },
                    vcr: {
                        title: 'ВКР',
                        width: '5%',
                        sorting: false,
                        edit: false,
                        create: false,
                        display: function (studentData) {
                            //Create an image that will be used to open child table
                            var $img = $('<img src="scripts/jtable/themes/lightcolor/blue/list_metro.png" title="Выпускная квалификационная работа" />');
                            //Open child table when user clicks the image
                            $img.click(function () {
                                // for passing in listing operation
                                var studentId = studentData.record.id;

                                $('#StudentsTable').jtable('openChildTable',
                                    $img.closest('tr'),
                                    {
                                        title: studentData.record.lName + ' ' + studentData.record.fName + ' - выпускная квалификационная работа',
                                        actions: {
                                            listAction: 'vcrHandler?action=list&studentId=' + studentId,
                                            createAction: 'vcrHandler?action=create&studentId=' + studentId,
                                            updateAction: 'vcrHandler?action=update',
                                            deleteAction: 'vcrHandler?action=delete'
                                        },
                                        fields: {
                                            name: {
                                                title: 'Тема',
                                                key: true,
                                                list: true,
                                                create: true,
                                                edit: true
                                            },
                                            head: {
                                                title: 'Руководитель'
                                            },
                                            reviewer: {
                                                title: 'Рецензент'
                                            }
                                        }
                                    }, function (data) { //opened handler
                                        data.childTable.jtable('load');
                                    });
                            });
                            //Return image to show on the person row
                            return $img;
                        }
                    },


                    buildDoc: {     // TODO instead add custom action
                        title: '',
                        width: '5%',
                        sorting: false,
                        edit: false,
                        create: false,
                        display: function (data) {
                            var id = data.record.id;

                            var $button = $('<button id="opener" title="Создать документ" class="jtable-command-button jtable-build-doc-command-button"><span>Создать документ</span></button>');

                            $button.click(function() {
                                dialog.data('id', id); // test
                                dialog.dialog("open");
                            });

                            return $button;
                        }

                    }

                }

            });



        //Load tables data from server
        $('#DateTable').jtable('load');
        $('#GekTable').jtable('load');
        $('#StudentsTable').jtable('load');



    });

</script>





<%--
    servlet handling: request.setAttribute("date", model)
    request.getRequestDispatcher("view").forward(request, response)
    jsp: ${date.getDay()}, ${date.getMonth}, etc
--%>


<%--
    Expandable table:
    http://www.w3schools.com/jsref/tryit.asp?filename=tryjsref_table_insertrow

    <p>Click the button to add a new row at the first position of the table and then add cells and content.</p>

    <table id="myTable" contentEditable="true">
      <tr>
        <td>Row1 cell1</td>
        <td>Row1 cell2</td>
      </tr>
      <tr>
        <td>Row2 cell1</td>
        <td>Row2 cell2</td>
      </tr>
      <tr onclick="myFunction()">
        <td>Row3 cell1</td>
        <td>Row3 cell2</td>
      </tr>
    </table>
    <br>

    <button onclick="myFunction()">Try it</button>

    <script>
    function myFunction() {
        var table = document.getElementById("myTable");
        var newRowNumber = table.rows.length;
        table.rows[newRowNumber-1].removeAttribute("onclick");
        var row = table.insertRow(newRowNumber);
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        cell1.innerHTML = newRowNumber;
        cell2.innerHTML = newRowNumber;
        row.setAttribute("onclick", "myFunction()");

    }
    </script>

    </body>
    </html>
--%>




<%--<input id="sendServer" name="sendServer" type="button" value="Send" />--%>


<%--<script language="JavaScript" type="text/javascript">--%>
<%--$(function(){--%>
<%--var dataArr = [];--%>
<%--$("td").each(function(){--%>
<%--dataArr.push($(this).html());--%>
<%--});--%>
<%--$('#sendServer').click(function(){--%>
<%--$.ajax({--%>
<%--type : "POST",--%>
<%--url : 'table',--%>
<%--data : "content="+dataArr,--%>
<%--success: function(data) {--%>
<%--alert(data); // alert the data from the server--%>
<%--},--%>
<%--error : function() {--%>
<%--alert("error");--%>
<%--}--%>
<%--});--%>
<%--});--%>
<%--});--%>
<%--</script>--%>



</body>
</html>
