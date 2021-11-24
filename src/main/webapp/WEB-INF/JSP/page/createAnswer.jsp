<%-- 
    Document   : createAnswer
    Created on : 15 окт. 2021 г., 21:02:46
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
        <table border="1" cellpadding="4" cellspacing="1">
			<tr>
				<th><my:Locale value="description" /></th>
				<th><my:Locale value="value" /></th>
				<th><my:Locale value="edit" /></th>
				<th><my:Locale value="delete" /></th>
			</tr>
			<c:forEach var="answer" items="${answers}">
				<tr>
					<td>
						<form action="controller" method="post">
							<input type="hidden" name="answer_id" value="${answer.id}">
							<input type="text" id="answer_name" name="answer_name" value="${answer.name}">
					</td>
					<td>
						<select name="answer_correct">
								<option value="${answer.isCorrect}"
									<c:if test="${answer.isCorrect == false }">selected</c:if>>${answer.isCorrect}
								</option>
								<option value="${!answer.isCorrect}">${!answer.isCorrect}</option>
						</select>
					</td>
					<td>
						<input type="hidden" name="question_id" value="${question_id}">
						<input type="hidden" name="command" value="updateAnswerCommand">
						<button type="submit" class="spacebtn btn btn btn-primary" value="edit">
							
						</button>
						</form>
					</td>
					<td>
						<form>
							<input type="hidden" name="question_id" value="${question_id}">
							<input type="hidden" name="answer_id" value="${answer.id}">
							<input type="hidden" name="command" value="deleteAnswerCommand">
							<button type="submit" value="delete">
								
							</button>
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
        <h1>Hello World!</h1>
    </body>
</html>
