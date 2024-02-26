<%@ page import="com.ivannikov.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>List of all resumes</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Delete</th>
            <th>Edit</th>
        </tr>
        <jsp:useBean id="resumes" scope="request" type="java.util.List"/>
        <c:forEach var="resume" items="${resumes}">
            <jsp:useBean id="resume" type="com.ivannikov.webapp.model.Resume"/>
        <tr>
            <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
            <td>${resume.getContact(ContactType.EMAIL)}</td>
            <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png" alt=Delete></a></td>
            <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit.png" alt=Edit></a></td>
        </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
