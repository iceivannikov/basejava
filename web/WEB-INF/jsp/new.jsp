<%@ page import="com.ivannikov.webapp.model.ContactType" %>
<%@ page import="com.ivannikov.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>New resume</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
<form method="post" action="resume" enctype="application/x-www-form-urlencoded">
    <dl>
        <dt>Имя:</dt>
        <dd><label>
            <input type="text" name="fullName" size="50" value="" required >
        </label></dd>
    </dl>
    <h3>Контакты:</h3>
    <c:forEach var="type" items="<%=ContactType.values()%>">
        <dl>
            <dt>${type.title}</dt>
            <dd><label>
                <input type="text" name="${type.name()}" size="30" value="" >
            </label></dd>
        </dl>
    </c:forEach>
    <c:forEach var="type" items="<%=SectionType.values()%>">
        <h3>
            <dl>
                <dt>${type.title}</dt>
            </dl>
        </h3>
        <c:choose>
            <c:when test="${type eq 'PERSONAL' || type eq 'OBJECTIVE'}">
                <dd><label>
                    <textarea rows="5" cols="75" name="${type}" ></textarea>
                </label></dd>
            </c:when>
            <c:when test="${type eq 'ACHIEVEMENT' || type eq 'QUALIFICATIONS'}">
                <dd><label>
                    <textarea rows="5" cols="75" name="${type}" ></textarea>
                </label></dd>
            </c:when>
        </c:choose>
    </c:forEach>
    <hr>
    <button type="submit">Create</button>
</form>
    <button onclick="window.history.back()">Cancel</button>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>