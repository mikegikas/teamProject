
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Log in</title>
<%@include file="links.jsp" %>
</head>

<body>
        <div class="d-md-flex flex-column justify-content-center align-items-center border border-dark p-2" >
            <h1>Route Planning</h1>
            <!--
                a jsp file is included that comes from the controller
                It's name depends if the user is logged in or not
            -->
            <jsp:include page = "${welcomeInclude}.jsp"/>
        </div>
</body>
</html>
  