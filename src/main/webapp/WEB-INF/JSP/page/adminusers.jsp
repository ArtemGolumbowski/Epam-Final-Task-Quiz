<%-- 
    Document   : adminusers
    Created on : 27 окт. 2021 г., 0:13:23
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
                    <th><fmt:message key="admin.user.name"/></th>
                    <th><fmt:message key="admin.user.surname"/></th>
                    <th><fmt:message key="user.login"/></th>
                    <th><fmt:message key="user.role"/></th>
                    <th><fmt:message key="user.password"/></th>
                    <th><fmt:message key="admin.blocked"/></th>
                    <th><fmt:message key="admin.actions"/></th>
                    
                    
                    
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}">  
                    <tr>
                        <td>${user.name}</td>
                        <td>${user.surname}</td>  
                        <td>${user.login}</td>
                        <td>${user.role}</td>
                        <td>${user.password}</td>
                        <td>${user.isBanned}</td>
                        <td><form action="/mavenproject1/admin/edit/user" method="GET">
                                <input type="hidden"  name="userEditId" value = "${user.id}">
                                <input type="submit" value="<fmt:message key="admin.edit"/>" />
                            </form>
                        
                        <form action="/mavenproject1/admin/delete/user" method="POST">
                                <input type="hidden"  name="userDeleteId" value = "${user.id}">
                                <input type="submit" value="<fmt:message key="admin.delete"/>" />
                            </form>
                        </td>


                        <c:if test = "${user.id == userEditId}">

                            <td> <form action="/mavenproject1/admin/edit/user" method="POST"> 
                                    <input type="text" name="name" required required minlength="2" maxlength="18" value="${user.name}" />
                                    <input type="text" name="surname" required minlength="2" maxlength="30" value="${user.surname}" />
                                    <input type="text" name="login" value="${user.login}" />
                                    <input type="text" name="password" required minlength="6" maxlength="18" value="${user.password}" />
                                    <select name="role" size="1">
                                        <option value="USER"><fmt:message key="user.user"/></option>
                                        <option value="ADMIN"><fmt:message key="user.admin"/></option>
                                    </select>
                                    <select name="isBanned" size="1">
                                        <option value="TRUE"><fmt:message key="user.block"/></option>
                                        <option value="FALSE"><fmt:message key="user.unblock"/></option>
                                    </select>
                                    <input type="hidden" name="userEditId" value="${user.id}" />
                                    <input type="submit" value="<fmt:message key="admin.edit"/>" />
                                </form>  
                                <form action="/mavenproject1/admin/get/users">
                                    <input type="submit" value="<fmt:message key="admin.cancel"/>" />
                                </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
