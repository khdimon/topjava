<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Edit meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Edit meal</h2>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<form method="post" action="mealEdit">
    <table>
        <tr>
            <td>id</td>
            <td><input type="number" value="${meal.id}" readonly name="id"/></td>
        </tr>
        <tr>
            <td>Дата</td>
            <td><input type="date" value="${meal.date}" name="date"/></td>
        </tr>
        <tr>
            <td>Время</td>
            <td><input type="time" value="${meal.time}" name="time"/></td>
        </tr>
        <tr>
            <td>Наименование</td>
            <td><input type="text" value="${meal.description}" name="description"/></td>
        </tr>
        <tr>
            <td>Калории</td>
            <td><input type="number" value="${meal.calories}" name="calories"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Сохранить"/></td>
        </tr>
    </table>
</form>
</body>
</html>
