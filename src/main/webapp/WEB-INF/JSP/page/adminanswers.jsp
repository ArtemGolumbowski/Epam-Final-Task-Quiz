<%-- 
    Document   : adminanswers
    Created on : 27 окт. 2021 г., 0:50:42
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
        <table border="1" width="5" cellspacing="1" cellpadding="1">
            <thead>
                <tr>
                    <th><fmt:message key="answer.description"/></th>
                    <th><fmt:message key="answer.value"/></th>
                    <th><fmt:message key="admin.edit"/></th>
                    <th><fmt:message key="admin.delete"/></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="answer" items="${answers}">  
                    <tr>    
                        <td>${answer.description}</td>
                        <td>${answer.value}</td>
                        <td><form action="/mavenproject1/admin/edit/answer" method="GET">
                                <input type="hidden"  name="answerId" value = "${answer.id}">
                                <input type="submit" value="<fmt:message key="admin.edit"/>" />
                            </form>
                        </td>
                        <td><form action="/mavenproject1/admin/delete/answer" method="POST">
                                <input type="hidden"  name="answerId" value = "${answer.id}">
                                <input type="submit" value="<fmt:message key="admin.delete"/>" />
                            </form>
                        </td>
                        <c:if test = "${answer.id == answerEditId}">
                            <td> <form action="/mavenproject1/admin/edit/answer" method="POST"> 
                                    <input type="text" name="description" required maxlength="70" value="${answer.description}" />
                                    <input type="hidden" name="answerId" value="${answer.id}" />
                                    <select name="value" size="1">
                                        <option value="true"><fmt:message key="answer.true"/></option>
                                        <option value="false"><fmt:message key="answer.false"/></option>
                                    </select>
                                    <input type="submit" value="<fmt:message key="admin.edit"/>" />
                                </form>
                                    <form action="/mavenproject1/admin/answers/${currentQuestionId}" method="GET">
                                        <input type="submit" value="<fmt:message key="admin.cancel"/>" />
                                    </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <h1><fmt:message key="answer.create"/></h1>
        <form action="createanswer" method="POST">
            <p><b></b>
                <input type="text" name="description" required maxlength="70" value="" size="25" height="100px" />
                <select name="value" size="1">
                    <option value = "TRUE"><fmt:message key="answer.true"/></option>
                    <option value="FALSE"><fmt:message key="answer.false"/></option>
                </select>
            </p>
            <p>
                <input type="submit" value="ОK"/>
            </p>
        </form>
    </body>
</html>
