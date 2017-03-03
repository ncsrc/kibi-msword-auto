
// jtable tables

$(document).ready(function () {

    // vars with dom roots of tables
    var dateTable = $('#DateTable');
    var gekTable = $('#GekTable');
    var studentsTable = $('#StudentsTable');


    // Table with info about dates
    dateTable.jtable({
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


//  Table about GEK
    gekTable.jtable({
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
                        gekTable.jtable('openChildTable',
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


    // Table about students
    studentsTable.jtable({
        title: 'Студенты',
        // paging: true,
        actions: {
            listAction: 'studentHandler?action=list',
            createAction: 'studentHandler?action=create',
            updateAction: 'studentHandler?action=update',
            deleteAction: 'studentHandler?action=delete'
        },
        fields: {
            id: {
                key: true,
                list: false,
                create: false,
                update: false,
                width: '5%'
            },
            firstNameI: {
                title: 'Имя',
                width: '20%'
            },
            lastNameI: {
                title: 'Фамилия',
                width: '20%'
            },
            middleNameI: {
                title: 'Отчество',
                width: '20%'
            },
            firstNameR: {
                title: 'Имя Р.П. (Кого/Чего)',
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
            firstNameD: {
                title: 'Имя Д.П. (Кому/Чему)',
                list: false
            },
            lastNameD: {
                title: 'Фамилия Д.П.',
                list: false
            },
            middleNameD: {
                title: 'Отчество Д.П.',
                list: false
            },

            // subtable with course
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

                        studentsTable.jtable('openChildTable',
                            $img.closest('tr'),
                            {
                                title: studentData.record.lastNameI + ' ' + studentData.record.firstNameI + ' - образовательная программа',
                                actions: {
                                    listAction: 'courseHandler?action=list&studentId='+studentId,
                                    createAction: 'courseHandler?action=create&studentId='+studentId,
                                    updateAction: 'courseHandler?action=update&studentId='+studentId,
                                    deleteAction: 'courseHandler?action=delete&studentId='+studentId,
                                },
                                fields: {
                                    name: {
                                        title: 'Направление',
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

            // subtable with vcr
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

                        studentsTable.jtable('openChildTable',
                            $img.closest('tr'),
                            {
                                title: studentData.record.lastNameI + ' ' + studentData.record.firstNameI + ' - выпускная квалификационная работа',
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

            // custom button for making document
            buildDoc: {
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
    dateTable.jtable('load');
    gekTable.jtable('load');
    studentsTable.jtable('load');



});