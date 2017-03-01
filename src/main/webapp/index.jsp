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
        <div id="dialog" style="display:none;" title="Создать документ">
            <p>Выберите тип документа</p>
            <select name="doctype" id="doctype">
                <option id="gos" value="gos">Протокол по приему гос экзамена</option>
                <option id="vcr" value="vcr">Протокол по защите ВКР</option>
            </select>
        </div>
    </div>

    <script src="scripts/script.js"></script>
    <script src="scripts/tables.js"></script>
</body>
</html>
