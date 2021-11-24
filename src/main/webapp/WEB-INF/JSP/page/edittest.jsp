<%-- 
    Document   : edittest
    Created on : 27 окт. 2021 г., 22:14:17
    Author     : agolu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="${language}">
  
    <body>
        <form action="edit/test" method="POST"> 
            <input type="text" name="testDescription" value="" />
            <input type="hidden" name="testId" value="${test.id}" />
            <input type="submit" value="edit" />
        </form>
    </body>
</html>
