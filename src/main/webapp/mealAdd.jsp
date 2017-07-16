<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Add meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Add meal</h2>
<form method="post" action="mealAdd">
    <table>
        <tr>
            <td>Дата</td>
            <td><input type="date" name="date"/></td>
        </tr>
        <tr>
            <td>Время</td>
            <td><input type="time" name="time"/></td>
        </tr>
        <tr>
            <td>Наименование</td>
            <td><input type="text" name="description"/></td>
        </tr>
        <tr>
            <td>Калории</td>
            <td><input type="number" name="calories"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Сохранить"/></td>
        </tr>
    </table>
</form>
</body>
</html>
