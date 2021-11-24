
<%-- 
    Document   : aside
    Created on : 25 сент. 2021 г., 0:07:49
    Author     : agolu
--%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="lang"/>
<form id="lang_panel">
        <select id="language" name="language" onchange="submit()">
            <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
            <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
        </select>
    </form> 
<html lang="${language}">
    <head>
       <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    </head>
    <div id="logout">
    <a href="/mavenproject1/logout"><fmt:message key="login.out"/></a>
    </div>
        <ul class="navbar">
            <li><a href="/mavenproject1/user" class="list-group-item ${selectedSubjectName == subject.name ? 'active' : '' }"><fmt:message key="user.tests"/></a></li>
                <c:forEach var="subject" items="${subjects}">
                <li> <a href="/mavenproject1/user?currentSubject=${subject.name}" class="list-group-item ${selectedSubjectName == subject.name ? 'active' : '' }">${subject.name } </a></li>

            </c:forEach>
                <br><br>
                <li><a href="/mavenproject1/results"><fmt:message key="user.results"/></a></li>
        </ul>
        
    
</html>


