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
        <a href="/mavenproject1/user?currentSubject=${currentSubject}&sort=popularity"><fmt:message key="admin.sort.popularity"/></a>
        <a href="/mavenproject1/user?currentSubject=${currentSubject}&sort=level"><fmt:message key="admin.sort.level"/></a>
        <a href="/mavenproject1/user?currentSubject=${currentSubject}&sort=name"><fmt:message key="admin.sort.name"/></a>
        <br>
        ${errorMessage}
        <table border="1" width="5" cellspacing="1" cellpadding="1">
            <thead>
                <tr>
                    <th><fmt:message key="admin.test.name"/></th>
                    <th><fmt:message key="admin.description"/></th>
                    <th><fmt:message key="admin.level"/></th>
                    <th><fmt:message key="admin.subject"/></th>
                    <th><fmt:message key="user.start"/></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="test" items="${tests}">  
                    <tr>
                        <td>${test.name}</td>
                        <td>${test.description}</td>
                        <td>${test.level}</td>
                        <td>${test.subject.name}</td>
                        <td><form action="/mavenproject1/start" method="POST">
                                <input type="hidden"  name="testId" value = "${test.id}">
                                <input type="submit" value="<fmt:message key="user.start"/>" />
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
<table border="1" cellpadding="5" cellspacing="5">
            <tr>
                <%--For displaying Previous link except for the 1st page --%>
                <c:if test="${currentPageCount != 1}">
                    <td><a href="/mavenproject1/user?currentSubject=${currentSubject}&&page=${currentPageCount - 1}"><fmt:message key="link.previous"/></a></td>
                </c:if>
                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPageCount eq i}">
                            <td>${i}</td>
                        </c:when>
                        <c:otherwise>
                            <td><a href="/mavenproject1/user?currentSubject=${currentSubject}&sort=${sort}&page=${i}">${i}</a></td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <%--For displaying Next link --%>
                    <c:if test="${currentPageCount lt noOfPages}">
                    <td><a href="/mavenproject1/user?currentSubject=${currentSubject}&sort=${sort}&page=${currentPageCount + 1}"><fmt:message key="link.next"/></a></td>
                </c:if>
            </tr>
        </table>

    </body>
</html>
