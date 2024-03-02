<%@ page import="com.ivannikov.webapp.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded" autocomplete="off">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><label>
                <input type="text" name="fullName" size="50" value="${resume.fullName}" required>
            </label></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><label>
                    <input type="text" name="${type.name()}" size="30" value="${resume.getContact(type)}">
                </label></dd>
            </dl>
        </c:forEach>
        <hr>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.ivannikov.webapp.model.SectionType, com.ivannikov.webapp.model.Section>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.ivannikov.webapp.model.Section"/>
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
                        <textarea rows="5" cols="75"
                                  name="${type}"><%=String.join("\n", ((ListSection) section).getListSections())%></textarea>
                    </label></dd>
                </c:when>
                <c:when test="${type eq 'EXPERIENCE' || type eq 'EDUCATION'}">
                    <c:forEach var="organozation" items="<%=((OrganizationSection) section).getOrganizations()%>">
                        <dl>
                            <dt>Название организации:</dt>
                            <dd><label>
                                <input type="text" name="${type}" size="30" value="${organozation.name}">
                            </label></dd>
                        </dl>
                        <dl>
                            <dt>Сайт организации:</dt>
                            <dd><label>
                                <input type="text" name="${type}" size="30" value="${organozation.website}">
                            </label></dd>
                        </dl>
                        <c:forEach var="period" items="${organozation.periods}">
                            <dl>
                                <dt>Должность:</dt>
                                <dd><label>
                                    <input type="text" name="${type}" size="30" value="${period.name}">
                                </label></dd>
                            </dl>
                            <dl>
                                <dt>Описание:</dt>
                                <dd><label>
                                    <textarea rows="5" cols="75" name="${type}">${period.description}</textarea>
                                </label></dd>
                            </dl>
                            <dl>
                                <dt>Дата начала:</dt>
                                <dd><label>
                                    <input type="text" name="${type}" size="30" value="${period.startDate}">
                                </label></dd>
                            </dl>
                            <dl>
                                <dt>Дата завершения:</dt>
                                <dd><label>
                                    <input type="text" name="${type}" size="30" value="${period.endDate}">
                                </label></dd>
                            </dl>
                            <b><hr></b>
                            <br>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Save</button>
    </form>
        <button onclick="window.history.back()">Cancel</button>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>