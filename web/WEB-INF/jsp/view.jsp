<%@ page import="com.ivannikov.webapp.model.ListSection" %>
<%@ page import="com.ivannikov.webapp.model.OrganizationSection" %>
<%@ page import="com.ivannikov.webapp.util.HtmlUtil" %>
<%@ page import="com.ivannikov.webapp.model.TextSection" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit.png" alt=Edit></a>
    </h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.ivannikov.webapp.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%>
            <br>
        </c:forEach>
    </p>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<com.ivannikov.webapp.model.SectionType, com.ivannikov.webapp.model.Section>"/>
        <c:set var="type" value="${sectionEntry.key}"/>
        <c:set var="section" value="${sectionEntry.value}"/>
        <jsp:useBean id="section" type="com.ivannikov.webapp.model.Section"/>
        <c:if test="<%=sectionEntry.getValue() != null%>">
            <h2>
                <dl>
                    <b>
                        <dt>${type.title}</dt>
                    </b>
                </dl>
            </h2>
            <c:choose>
                <c:when test="${type eq 'OBJECTIVE'}">
                    <b>
                        <dd><label>
                            <ul>
                                <li><%=((TextSection) section).getTextSection()%></li>
                            </ul>
                        </label></dd>
                    </b>
                </c:when>
                <c:when test="${type eq 'PERSONAL'}">
                    <dd><label>
                        <ul>
                            <li><%=((TextSection) section).getTextSection()%></li>
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
                            <tr>
                                <td>
                                    <c:choose>
                                        <c:when test="${empty organization.website}">
                                            <h3>${organization.name}</h3>
                                        </c:when>
                                        <c:otherwise>
                                            <h3><a href="${organization.website}">${organization.name}</a></h3>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <c:forEach var="period" items="${organization.periods}">
                                <jsp:useBean id="period" type="com.ivannikov.webapp.model.Organization.Period"/>
                                <tr>
                                    <td>
                                        <h4>
                                            <%=HtmlUtil.formatDates(period)%>
                                        </h4>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <h4>
                                            <b>${period.name}</b>
                                        </h4>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                            ${period.description}
                                    </td>
                                </tr>
                                <hr>
                            </c:forEach>
                        </c:forEach>
                    </label></dd>
                </c:when>
            </c:choose>
        </c:if>
    </c:forEach>
    <hr>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
