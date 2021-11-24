<%-- 
    Document   : index
    Created on : 28 сент. 2021 г., 17:13:49
    Author     : agolu
--%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="lang"/>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>

    </select>
</form> 
<html lang="${language}">

<!--    <style><%@include file="/WEB-INF/CSS/style.css" %></style>-->

    <!--<link rel="stylesheet" type= "text/css" href="/mavenproject1/resources/style.css">-->
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type= "text/css" href="CSS/style.css"> 
    </head>
    <body>


        <!DOCTYPE html>
    <div class="container">
        <section id="content">

            <form method="post" action="/mavenproject1/login">
                <h1><fmt:message key="login.label.login"/>:</h1>
                ${errorMessage}
                <div>
                    <input type="text" required placeholder="Login" name="login" id="login">
                </div>
                <div>
                    <input type="password" required minlength="6" maxlength="18" placeholder="Password" name="password" id="password">
                </div>
                <div>
                    <input  type="submit" value="<fmt:message key="login.enter"/>">
                </div>
                <div>
                    <a href="registration"><fmt:message key="login.registration"/>?</a>
                </div>
            </form>


        </section>
    </div>
</body>
</html>
