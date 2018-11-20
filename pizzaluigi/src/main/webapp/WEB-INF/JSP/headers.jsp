<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%> 
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@taglib uri='http://vdab.be/tags' prefix='vdab'%>  
<!DOCTYPE html>
<html lang='nl'>
<head>
<vdab:head title='Headers'/> 
</head>
<body>
<vdab:menu/>
je brower wordt uitgevoerd op
${opWindows ? "Windows" : "een niet Windows besturingssysteem" }.
</body>
</html>