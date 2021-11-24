<%-- 
    Document   : userTest
    Created on : 25 окт. 2021 г., 18:13:11
    Author     : agolu
--%>

<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="lang"/>
<html lang="${language}">

    <body>
        <table border="1">
            <thead>
                <tr>
                    <th><fmt:message key="user.test"/></th>
                    <th><fmt:message key="admin.subject"/></th>
                    <th><fmt:message key="admin.level"/></th>
                    <th><fmt:message key="user.score"/></th>
                    <th><fmt:message key="user.date"/></th>
                    <th><fmt:message key="test.time"/></th>
                    <th><fmt:message key="user.time"/></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="userTest" items="${userTests}">    
                    <tr>
                        <td>${userTest.testName}</td>
                        <td>${userTest.subjectName}</td>
                        <td>${userTest.level}</td>
                        <td>${userTest.score}</td>
                        <fmt:parseDate value="${userTest.userPassDate}" pattern="y-M-dd'T'H:m"
                                       var="myParseDate"></fmt:parseDate>
                        <fmt:formatDate value="${myParseDate}" 
                                        pattern="yyyy.MM.dd HH:mm" 
                                        var="userPassDate"></fmt:formatDate>
                        <td>${userPassDate}</td>
                        <td>${userTest.testDuration}</td>
                        <td>${userTest.userQuizTime}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div id="logout">
            <table border="1">
                <thead>
                    <tr>
                        <th><fmt:message key="user.details"/></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><fmt:message key="registration.name"/>: ${user.name}</td>
                    </tr>
                    
                    <tr>
                        <td><fmt:message key="registration.surname"/>: ${user.surname}</td>
                    </tr>
                   
                    <tr>
                        <td><fmt:message key="registration.login"/>: ${user.login}</td>
                    </tr>
                </tbody>
            </table>

        </div>

    </body>
