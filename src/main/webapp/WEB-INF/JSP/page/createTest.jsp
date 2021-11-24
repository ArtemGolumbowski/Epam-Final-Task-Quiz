<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : createTest
    Created on : 13 окт. 2021 г., 18:51:41
    Author     : agolu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>

    <body>
        <h1>Create test</h1>
        <form action="createTest" method="POST">
            <p><b>Name</b>
                <input type="text" name="name" value="" size="45" />
            </p>
            <p><b>Description</b>
                <input type="text" name="description" value="" size="255" />
            </p>
            <p>
            <select name="level" size="1">
                <option>easy</option>
                <option>medium</option>
                <option>hard</option>
            </select>
                </p>
            <p>
                <select name="subject" size="1">
                    <c:forEach var="subject" items="${subjects}">
                        <option name = "subjectId" value="${subject.id}">${subject.name}</option>
                    </c:forEach>               
        </p>
        <p><input type="submit" value="ОK" 
                  />
        </p>
    </form>
        
</body>
</html>
