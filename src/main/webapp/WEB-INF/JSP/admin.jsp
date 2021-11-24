<%-- 
    Document   : admin
    Created on : 26 окт. 2021 г., 22:06:17
    Author     : agolu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="${language}">
    <head>
        <style><%@include file="/WEB-INF/CSS/external-css.css" %></style>
    </head>
    <body>
        <jsp:include page="page/adminheader.jsp" /> 
        <jsp:include page="${currentPage }" /> 
    </body>
</html>
