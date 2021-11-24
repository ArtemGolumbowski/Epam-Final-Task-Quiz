<%-- 
    Document   : adminquestions
    Created on : 27 окт. 2021 г., 0:50:25
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
                    <th><fmt:message key="question.description"/></th>
                    <th><fmt:message key="admin.edit"/></th>
                    <th><fmt:message key="admin.delete"/></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="question" items="${questions}">  
                    <tr>    
                        <td><a href="/mavenproject1/admin/answers/${question.id}">${question.description}</a></td>
                        <td><form action="/mavenproject1/admin/edit/question" method="GET">
                                <input type="hidden"  name="questionId" value = "${question.id}">
                                <input type="submit" value="<fmt:message key="admin.edit"/>" />
                            </form>
                        </td>
                        <td><form action="/mavenproject1/admin/delete/question" method="POST">
                                <input type="hidden"  name="testId" value = "${question.id}">
                                <input type="submit" value="<fmt:message key="admin.delete"/>" />
                            </form>
                        </td>
                        <c:if test = "${question.id == questionEditId}">
                            <td> <form action="/mavenproject1/admin/edit/question" method="POST"> 
                                    <input type="text" name="description" required minlength="20" maxlength="100" value="${question.description}" />
                                    <input type="hidden" name="questionId" value="${question.id}" />
                                    <input type="submit" value="<fmt:message key="admin.edit"/>" />
                                </form>  
                                    <form action="/mavenproject1/admin/questions/${currentTestId}">
                                        <input type="submit" value="<fmt:message key="admin.cancel"/>"/>
                                    </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <form action="createquestion" method="POST">
            <p><b><fmt:message key="question.create"/></b>
            </p>
            <p>
                <input type="text" name="description" required minlength="20" maxlength="100" value="" size="25" height="100px" />
            </p>
            <p>
                <input type="submit" value="ОK"/>
            </p>
        </form>
    </body>
</html>

