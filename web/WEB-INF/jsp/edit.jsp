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
    <jsp:useBean id="resume" type="com.ivannikov.webapp.model.Resume" scope="request"/>
    <title>Resumes ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Name</dt>
            <dd><label>
                <input type="text" name="fullName" size="50" value="${resume.fullName}">
            </label></dd>
        </dl>
        <h3>Contacts:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><label>
                    <input type="text" name="${type.name()}" size="30" value="${resume.getContact(type)}">
                </label></dd>
            </dl>
        </c:forEach>
        <h3>Sections:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <dl>
                <dt>${type.title}</dt>
            </dl>
            <c:choose>
                <c:when test="${type eq 'PERSONAL' || type eq 'OBJECTIVE'}">
                    <dd><label>
                        <textarea rows="5" cols="75" name="${type}">${section}</textarea>
                    </label></dd>
                </c:when>
                <c:when test="${type eq 'ACHIEVEMENT' || type eq 'QUALIFICATIONS'}">
                    <dd><label>
                        <textarea rows="5" cols="75" name="${type}">${section}</textarea>
                    </label></dd>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Save</button>
        <button onclick="window.history.back()">Cancel</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>