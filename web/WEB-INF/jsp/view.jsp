<%@ page import="com.ivannikov.webapp.model.ListSection" %>
<%@ page import="com.ivannikov.webapp.model.OrganizationSection" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit.png" alt=Edit></a>
    </h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.ivannikov.webapp.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br>
        </c:forEach>
    </p>
    <h3>Sections:</h3>
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
                    <ul>
                        <li>${section}</li>
                    </ul>
                </label></dd>
            </c:when>
            <c:when test="${type eq 'ACHIEVEMENT' || type eq 'QUALIFICATIONS'}">
                <dd><label>
                    <c:forEach var="text" items="<%=((ListSection) section).getListSections()%>">
                        <ul>
                            <li>${text}</li>
                        </ul>
                    </c:forEach>
                </label></dd>
            </c:when>
            <c:when test="${type eq 'EXPERIENCE' || type eq 'EDUCATION'}">
                <dd><label>
                    <c:set var="organizations" value="<%=((OrganizationSection) section).getOrganizations()%>"/>
                    <c:forEach var="organization" items="${organizations}">
                        <ul>
                            <li>${organization}</li>
                        </ul>
                        <c:forEach var="period" items="${organization.periods}">
                            <ul>
                                <li>${period}</li>
                            </ul>
                        </c:forEach>
                    </c:forEach>
                </label></dd>
            </c:when>
        </c:choose>
    </c:forEach>
    <hr>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
