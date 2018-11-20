<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%> 
<%@taglib prefix='spring' uri='http://www.springframework.org/tags'%> 
<%@taglib uri='http://vdab.be/tags' prefix='vdab'%> 
<!DOCTYPE html>
<html lang="nl">
<head>
<meta charset="UTF-8">
<vdab:head title='Pizza Luigi'/> 
</head>
<body>
<vdab:menu/>
	<h1>Pizza Luigi</h1>
	<img src='<c:url value="/images/pizza.jpg"/>' alt="pizza" class="fullwidth">
	<h2>${boodschap}</h2>
		 <h2>De zaakvoerder</h2>
	 <dl>
	 	<dt>Naam</dt><dd>${zaakvoerder.naam}</dd>
	 	<dt>Aantal kinderen</dt><dd>${zaakvoerder.aantalKinderen}</dd>
	 	<dt>Gehuwd</dt><dd>${zaakvoerder.gehuwd ? 'Ja' : 'Nee'}</dd>
	 	<dt>Adres</dt><dd>${zaakvoerder.adres.straat } ${zaakvoerder.adres.huisnr }<br>
	 					  ${zaakvoerder.adres.postcode } ${zaakvoerder.adres.gemeente }</dd>
	 </dl>
<c:if test='${not empty laatstBezocht }'>
	<p>Je bezocht deze pagina laatst op  <spring:eval expression='laatstBezocht.waarde'/></p>
</c:if>
<p>Deze pagina werd ${aantalKeerBekeken} keer bekeken </p> 
<p>${identificatie.emailAdres}</p>
</body>
</html>