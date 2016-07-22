<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<h2>Hello World!1 ${val}</h2>
<span>
   aa <c:out value="${val}"/>
   <c:set var="age" value="2" scope="request"/>
   ads
    <c:if test="${age<18}">
       abc
    </c:if>

</span>
<h3><span>appId:</span>${app.authorizerAppid}</h3>
<h3><span>tokenExpiresOut:</span>${app.tokenExpiresOut}</h3>
</body>
</html>
