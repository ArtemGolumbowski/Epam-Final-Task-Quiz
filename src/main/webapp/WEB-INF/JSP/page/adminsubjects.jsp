<%-- 
    Document   : adminsubjects
    Created on : 27 окт. 2021 г., 1:26:15
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
    <body>
        <table border="1" width="5" cellspacing="1" cellpadding="1">
            <thead>
                <tr>
                    <th><fmt:message key="admin.subject"/></th>
                    <th><fmt:message key="admin.description"/></th>
                    <th><fmt:message key="admin.edit"/></th>
                    <th><fmt:message key="admin.delete"/></th>
                </tr>

            </thead>
            <tbody>
                <c:forEach var="subject" items="${subjects}">  
                    <tr>
                        <td>${subject.name}</td>
                        <td>${subject.description}</td>
                        <td><form action="/mavenproject1/admin/edit/subject" method="GET">
                                <input type="hidden"  name="subjectId" value = "${subject.id}">
                                <input type="submit" value="<fmt:message key="admin.edit"/>" />
                            </form>
                        </td>
                        <td><form action="deletesubject" method="POST">
                                <input type="hidden"  name="subjectId" value = "${subject.id}">
                                <input type="submit" value="<fmt:message key="admin.delete"/>" />
                            </form>
                        </td>


                        <c:if test = "${subject.id == subjectEditId}">

                            <td> <form action="/mavenproject1/admin/edit/subject" method="POST"> 
                                    <input type="text" name="name" required minlength="3" maxlength="30" value="${subject.name}" />
                                    <input type="text" name="description" required minlength="10" maxlength="100" value="${subject.description}" />

                                    <input type="hidden" name="subjectId" value="${subject.id}" />
                                    <input type="submit" value="<fmt:message key="admin.edit"/>" />

                                </form>  
                                    <form action="/mavenproject1/admin/get/subjects">
                                        <input type="submit" value="<fmt:message key="admin.cancel" />"/>
                                    </form>  
                            </td>


                        </c:if>
                    </c:forEach>

                </tr>
            </tbody>
        </table>
        <h1><fmt:message key="subject.create"/></h1>
        <form action="createsubject" method="POST">
            <p><b><fmt:message key="admin.name"/></b>
                <input type="text" name="name" required minlength="3" maxlength="30" value="" size="25" />
            </p>
            <p><b><fmt:message key="admin.description"/></b>
                <input type="text" name="description" required minlength="10" maxlength="100" value="" size="25" height="100px" />
            </p>                        
            <p><input type="submit" value="ОK" 
                      />
            </p>
        </form>
    </body>
</html>
