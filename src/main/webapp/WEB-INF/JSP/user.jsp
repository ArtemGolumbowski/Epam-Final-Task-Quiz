<%-- 
    Document   : user
    Created on : 31 окт. 2021 г., 22:53:38
    Author     : agolu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="${language}">
    <head>
        <style><%@include file="/WEB-INF/CSS/external-css.css" %></style>
    </head>
    <body>
        <jsp:include page="fragment/aside.jsp" />
       <jsp:include page="${currentPage }" /> 
    </body>
     
</html>
