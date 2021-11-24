<%-- 
    Document   : amintests
    Created on : 26 окт. 2021 г., 20:56:15
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
    <body>
        <a href="/mavenproject1/admin/get/tests?currentSubject=${currentSubject}&sort=popularity"><fmt:message key="admin.sort.popularity"/></a>
        <a href="/mavenproject1/admin/get/tests?currentSubject=${currentSubject}&sort=level"><fmt:message key="admin.sort.level"/></a>
        <a href="/mavenproject1/admin/get/tests?currentSubject=${currentSubject}&sort=name"><fmt:message key="admin.sort.name"/></a>
        <table border="1" width="5" cellspacing="1" cellpadding="1">
            <thead>
                <tr>
                    <th><fmt:message key="admin.test.name"/></th>
                    <th><fmt:message key="admin.description"/></th>
                    <th><fmt:message key="admin.level"/></th>
                    <th><fmt:message key="admin.subject"/></th>
                    <th><fmt:message key="admin.edit"/></th>
                    <th><fmt:message key="admin.delete"/></th>
                </tr>
            </thead>
            <tbody>

                <c:forEach var="test" items="${tests}">  
                    <tr>
                        <td><a href="/mavenproject1/admin/questions/${test.id}">${test.name}</a></td>
                        <td>${test.description}</td>
                        <td>${test.level}</td>
                        <td>${test.subject.name}</td>
                        <td><form action="/mavenproject1/admin/edit/test" method="GET">
                                <input type="hidden"  name="testId" value = "${test.id}">
                                <input type="hidden"  name="currentSubject" value = "${currentSubject}">
                                <input type="hidden"  name="page" value ="${currentPageCount}" >
                                <input type="hidden"  name="sort" value ="${sort}">
                                <input type="submit" value="<fmt:message key="admin.edit"/>" />
                            </form>
                        </td>
                        <td><form action="/mavenproject1/admin/delete/test" method="POST">
                                <input type="hidden"  name="testId" value = "${test.id}">
                                <input type="submit" value="<fmt:message key="admin.delete"/>" />
                            </form>
                        </td>
                        <c:if test = "${test.id == testEditId}">

                            <td> <form action="/mavenproject1/admin/edit/test" method="POST"> 
                                    <input type="text" name="name" required minlength="4" maxlength="30" value="${test.name}" />
                                    <input type="text" name="description" required minlength="10" maxlength="255" value="${test.description}" />
                                    <select name="level" size="1">
                                        <option value="easy"><fmt:message key="admin.test.easy"/></option>
                                        <option value="medium"><fmt:message key="admin.test.medium"/></option>
                                        <option value="hard"><fmt:message key="admin.test.hard"/></option>
                                    </select>
                                    <input type="hidden" name="testId" value="${test.id}" />
                                    <input type="submit" value="<fmt:message key="admin.edit"/>" />

                                </form>  
                                <form action="/mavenproject1/admin/get/tests" method="GET">
                                    <input type="submit" value="<fmt:message key="admin.cancel"/>"/>
                                </form>
                            </td>


                        </c:if>
                    </tr>



                </c:forEach>
            </tbody>
        </table>


        <%--For displaying Page numbers. 
        The when condition does not display a link for the current page--%>
        <table border="1" cellpadding="5" cellspacing="5">
            <tr>
                <%--For displaying Previous link except for the 1st page --%>
                <c:if test="${currentPageCount != 1}">
                    <td><a href="/mavenproject1/admin/get/tests?currentSubject=${currentSubject}&&page=${currentPageCount - 1}"><fmt:message key="link.previous"/></a></td>
                        </c:if>
                        <c:forEach begin="1" end="${noOfPages}" var="i">
                            <c:choose>
                                <c:when test="${currentPageCount eq i}">
                            <td>${i}</td>
                        </c:when>
                        <c:otherwise>
                            <td><a href="/mavenproject1/admin/get/tests?currentSubject=${currentSubject}&sort=${sort}&page=${i}">${i}</a></td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <%--For displaying Next link --%>
                    <c:if test="${currentPageCount lt noOfPages}">
                    <td><a href="/mavenproject1/admin/get/tests?currentSubject=${currentSubject}&sort=${sort}&page=${currentPageCount + 1}"><fmt:message key="link.next"/></a></td>
                    </c:if>
            </tr>
        </table>


        <h1><fmt:message key="admin.test.create"/></h1>
        <form action="/mavenproject1/createtest" method="POST">
            <p><b><fmt:message key="admin.name"/></b>
                <input type="text" name="name" value="" size="25" />
            </p>
            <p><b><fmt:message key="admin.description"/></b>
                <input type="text" name="description" value="" size="25" height="100px" />
            </p>
            <b><fmt:message key="admin.duration"/></b>
            <input type="number" name="duration" value="5" min="5" max="120">
            <p><b><fmt:message key="admin.level"/></b>
                <select name="level" size="1">
                    <option value="easy"><fmt:message key="admin.test.easy"/></option>
                    <option value="medium"><fmt:message key="admin.test.medium"/></option>
                    <option value="hard"><fmt:message key="admin.test.hard"/></option>
                </select>
            </p>
            <p>
                <b><fmt:message key="admin.subject"/></b>
                <select name="subject" size="1">
                    <c:forEach var="subject" items="${subjects}">
                        <option value="${subject.id}">${subject.name}</option>
                    </c:forEach>               
            </p>
            <p><input type="submit" value="ОK"/>
            </p>
        </form>

    </body>

</html>
