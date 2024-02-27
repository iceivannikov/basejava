<%@ page import="com.ivannikov.webapp.model.ContactType" %>
<%@ page import="com.ivannikov.webapp.model.SectionType" %>
<%@ page import="com.ivannikov.webapp.model.ListSection" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
<%--    <jsp:useBean id="resume" type="com.ivannikov.webapp.model.Resume" scope="request"/>--%>
    <title>New resume</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
<form method="post" action="newResume" enctype="application/x-www-form-urlencoded">
    <!-- Удаляем атрибут uuid -->
    <dl>
        <dt>Name</dt>
        <dd><label>
            <!-- Очищаем поле для имени -->
            <input type="text" name="fullName" size="50" value="">
        </label></dd>
    </dl>
    <h3>Contacts:</h3>
    <c:forEach var="type" items="<%=ContactType.values()%>">
        <dl>
            <dt>${type.title}</dt>
            <dd><label>
                <!-- Очищаем поля контактов -->
                <input type="text" name="${type.name()}" size="30" value="">
            </label></dd>
        </dl>
    </c:forEach>
    <h3>Sections:</h3>
    <c:forEach var="type" items="<%=SectionType.values()%>">
        <dl>
            <dt>${type.title}</dt>
        </dl>
        <c:choose>
            <c:when test="${type eq 'PERSONAL' || type eq 'OBJECTIVE'}">
                <dd><label>
                    <!-- Очищаем текстовые поля секций -->
                    <textarea rows="5" cols="75" name="${type}"></textarea>
                </label></dd>
            </c:when>
            <c:when test="${type eq 'ACHIEVEMENT' || type eq 'QUALIFICATIONS'}">
                <dd><label>
                    <!-- Очищаем текстовые поля секций -->
                    <textarea rows="5" cols="75" name="${type}"></textarea>
                </label></dd>
            </c:when>
        </c:choose>
    </c:forEach>
    <hr>
    <!-- Изменяем текст кнопки -->
    <button type="submit">Create</button>
    <button onclick="window.history.back()">Cansel</button>
</form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>