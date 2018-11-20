<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%> 
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%> 
<%@taglib prefix='spring' uri='http://www.springframework.org/tags'%> 
<%@taglib prefix='form' uri='http://www.springframework.org/tags/form'%>
<%@taglib uri='http://vdab.be/tags' prefix='vdab'%> 
<!DOCTYPE html>
<html lang='nl'>
<head>
<meta charset="UTF-8">
<vdab:head title='Prijzen'/> 
</head>
<body>
<vdab:menu/>
<h1>Prijzen</h1>
<ul>
	<c:forEach var='prijs' items='${prijzen }'>
		<c:url value='/pizzas' var='url'>
			<c:param name='prijs' value='${prijs }'/>
		</c:url>
		<li><a href='${url }'>${prijs }</a></li>
	</c:forEach>
</ul>
<c:if test='${not empty pizzas }'>
	<h2>${prijs }</h2>
	<ul>
		<c:forEach var='pizza' items='${pizzas }'>
			<spring:url var='url' value='/pizzas/{id}'>
				<spring:param name='id' value="${pizza.id }"/>
			</spring:url>
			<li><a href='${url}'><c:out value='${pizza.naam }'/></a></li>
		</c:forEach>
	</ul>
</c:if>
</body>
</html>