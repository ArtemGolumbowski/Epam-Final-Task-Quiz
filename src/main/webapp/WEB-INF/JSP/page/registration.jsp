<%@ page language="java" contentType="text/html; charset=UTF-8" 
         pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <head>
        <meta charset="UTF-8">
        <title><fmt:message key="registration.registrationpage"/></title>

        <link href="/mavenproject1/CSS/style.css" rel="stylesheet" type= "text/css" >
    </head>
    <body>
        <div class="container">
            <section id="content">
<h2><fmt:message key="registration.registrationpage"/></h2>
                <form action="registration" method="POST">
                    
                    <h1><fmt:message key="registration.login"/>:</h1>
                    <h1>${errorMessage}</h1>
                    <div>
                        <input type="email" name="login" required placeholder="<fmt:message key="registration.login"/>"id="login">
                    </div>

                    <h1><fmt:message key="registration.password"/>:</h1>
                    <div>
                        <input type="password" name="password" required minlength="6" maxlength="18" placeholder="<fmt:message key="registration.password"/>"id="password">
                    </div>
                    <h1><fmt:message key="registration.name"/></h1>
                    <div>
                        <input type="text" name="name" required required minlength="2" maxlength="18" placeholder="<fmt:message key="registration.name"/>" id="login">
                    </div>
                    <h1><fmt:message key="registration.surname"/></h1>
                    <div>
                        <input type="text" name="surname" required minlength="2" maxlength="30" placeholder="<fmt:message key="registration.surname"/>" id="login">
                    </div>

                    <div>
                        <fmt:message key="registration.as"/>: <select name="role">
                            <option value="USER"><fmt:message key="registration.user"/></option>                   
                            <option value="ADMIN"><fmt:message key="registration.admin"/></option>
                            
                        </select>
                    </div>
                    <div>   
                        <input type="submit" value="ОK" />
                    </div>
                    <div>
                        <a href="login"><fmt:message key="registration.registered"/>?</a>
                    </div>
                </form>

            </section>
        </div>
    </body>
</html>
