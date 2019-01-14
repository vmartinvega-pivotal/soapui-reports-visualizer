<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="language" value="${pageContext.request.locale}"/>
<fmt:setLocale value="${language}"/>

<!DOCTYPE html>
<html lang="${language}">
<head>
  <meta charset="utf-8">
  <title>SoapUi Reports</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">

  <!-- Le styles -->
  <link href="../assets/css/bootstrap.css" rel="stylesheet">
  <link href="../assets/css/report.css" rel="stylesheet">
  <style>
    body {
      padding-top: 60px;
      /* 60px to make the container go all the way to the bottom of the topbar */
    }
  </style>
  <link href="../assets/css/bootstrap-responsive.css" rel="stylesheet">
</head>

<body>

<div class="navbar navbar-inverse navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container">
      <a class="btn btn-navbar" data-toggle="collapse"
         data-target=".nav-collapse"> <span class="icon-bar"></span> <span
          class="icon-bar"></span> <span class="icon-bar"></span>
      </a> 
      <a class="brand" href="#">Home</a>
      <c:choose>
		<c:when test="${requestScope.initialized}">
			<a class="brand" href="setup">Refresh</a>
		</c:when>    
		<c:otherwise>
			<a class="brand" href="setup">Load</a>
		</c:otherwise>
  	  </c:choose>
  	  <c:if test="${requestScope.initialized}">
	  		<a class="brand" href="artifacts">Reports</a>
	  </c:if>
      <!--/.nav-collapse -->
    </div>
  </div>
</div>

<div class="container">

  <h3>SoapUi Reports</h3>

  <p>Welcome to the web to navigate the soapui reports created by the pipelines.</p><br/>
  <c:choose>
	<c:when test="${requestScope.initialized}">
		<p>Click the <b>"Refresh"</b> link to clean the reports previously loaded and reads them again from nexus</p>
	</c:when>    
	<c:otherwise>
		<p>Click the <b>"Load"</b> link to read all reports from nexus</p>
	</c:otherwise>
  </c:choose>
  <c:if test="${requestScope.initialized}">
	<p>Click the <b>"Reports"</b> link to navigate the loaded reports from nexus</p>
  </c:if>
</div>
<!-- /container -->
</body>
</html>
