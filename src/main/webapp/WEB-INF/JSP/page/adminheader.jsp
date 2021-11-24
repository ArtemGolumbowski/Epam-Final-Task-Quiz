<%-- 
    Document   : adminheader
    Created on : 31 окт. 2021 г., 20:13:37
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
    <head>
       <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    </head>

    <form id="lang_panel">
        <select id="language" name="language" onchange="submit()">
            <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
            <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
        </select>
    </form> 
    <a id="logout" href="/mavenproject1/logout"><fmt:message key="login.out"/></a>
    <ul class="navbar">
        <li><a href="/mavenproject1/admin/get/tests"><fmt:message key="admin.tests"/></a></li>
        <li><a href="/mavenproject1/admin/get/users"><fmt:message key="admin.users"/></a></li>
        <li><a href="/mavenproject1/admin/get/subjects"><fmt:message key="admin.subjects"/></a></li>
    </ul>
</html>
