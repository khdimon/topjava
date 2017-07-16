<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h3><a href="mealAdd">Добавить</a></h3>
<h2>Meals</h2>
<table>
    <tr>
        <th>Дата</th>
        <th>Время</th>
        <th>Наименование</th>
        <th>Калории</th>
        <th>Превышение</th>
        <th>Редактировать</th>
    </tr>
    <jsp:useBean id="meals" scope="request" type="java.util.List"/>
    <c:forEach var="meal" items="${meals}">
        <tr style="color:
        <c:if test="${meal.exceed}">RED</c:if>
        <c:if test="${!meal.exceed}">GREEN</c:if>
                ">
            <td>${meal.date}</td>
            <td>${meal.time}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>${meal.exceed}</td>
            <td><a href="<c:url value='mealEdit?action=edit&id=${meal.id}'/>">Редактировать</a></td>
            <td><a href="<c:url value='mealEdit?action=delete&id=${meal.id}'/>">Удалить</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
