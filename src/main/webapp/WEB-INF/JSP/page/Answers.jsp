<%-- 
    Document   : Answers
    Created on : 23 окт. 2021 г., 22:00:39
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>

    <form>
        <select id="language" name="language" onchange="submit()">
            <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
            <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
            <option value="es" ${language == 'es' ? 'selected' : ''}>Español</option>
        </select>
    </form> 

    <p>${currentQuestionNumber+1} of ${questions.size()}</p>
    <p>${currentQuestion.description}</p>
    <form action="/mavenproject1/testing" method="POST">


        <ol type="1">
            <c:forEach var="answer" items="${answers}">  
                <li>${answer.description}<input type="checkbox"  name="userAnswer" value = "${answer.description}"></li>
                </c:forEach>
        </ol><br>
        <c:if test="${currentQuestionNumber+1 != questions.size()}">
            <input type="submit" value="<fmt:message key="button.next"/>" /> 
        </c:if>

        <input type="submit" name="finish" value="<fmt:message key="button.finish"/>" />
    </form>
</div>

</html>
