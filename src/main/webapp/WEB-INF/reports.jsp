<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
  <link href="assets/css/bootstrap.css" rel="stylesheet">
  <link href="assets/css/report.css" rel="stylesheet">
  <style>
    body {
      padding-top: 60px;
      /* 60px to make the container go all the way to the bottom of the topbar */
    }
  </style>
  <link href="assets/css/bootstrap-responsive.css" rel="stylesheet">
</head>

<body>

<div class="navbar navbar-inverse navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container">
      <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> 
      	<span class="icon-bar"></span> 
      	<span class="icon-bar"></span> 
      	<span class="icon-bar"></span>
      </a> 
      <a class="brand" href="/">Home</a>
       <c:choose>
		<c:when test="${initialized}">
			<a class="brand" href="setup">Refresh</a>
		</c:when>    
		<c:otherwise>
			<a class="brand" href="setup">Load</a>
		</c:otherwise>
  	  </c:choose>
  	  <c:if test="${initialized}">
	  		<a class="brand" href="artifacts">Reports</a>
	  </c:if>
      <!--/.nav-collapse -->
    </div>
  </div>
</div>

<div class="container">

<c:choose>
	<c:when test="${initialized}">
	  <h3>Reports</h3>
	  <h4>GroupId: <c:out value="${groupid}"/></h4>
	  <h4>ArtifactId: <c:out value="${artifactid}"/></h4>
	
	  <table class="table table-striped table-bordered">
	    <thead>
	    <tr>
	      <th>Environment</th>
	      <th>Date</th>
	      <th>Version</th>
	      <th>Status</th>
	      <th>View</th>
	    </tr>
	    </thead>
	    <tbody>
	    <c:forEach items="${reports}" var="report">
	      <tr>
	        <td><c:out value="${report.environment}"/></td>
	        <td><c:out value="${report.date}"/></td>
	        <td><c:out value="${report.version}"/></td>
	        <c:choose>
	        	<c:when test="${report.fake}">
					<td>Successfull - Fake</td>        
			    </c:when> 
			    <c:when test="${report.successful}">
					<td>Successfull</td>        
			    </c:when>    
			    <c:otherwise>
			        <td>Failed</td> 
			    </c:otherwise>
			</c:choose>
	        <td><a href="${report.url}" target="_blank"><i class="icon-eye-open"></i></a></td>
	      </tr>
	    </c:forEach>
	    </tbody>
	  </table>
	  <c:if test="${count > 0}">
	    <c:if test="${page > 1}">
	      <a href="<c:url value="reports"><c:param name="page" value="${page - 1}"/><c:param name="artifactid" value="${artifactid}"/><c:param name="groupid" value="${groupid}"/></c:url>">&lt; Prev</a>&nbsp;
	    </c:if>
	    Showing records ${start} to ${end} of ${count}
	    <c:if test="${page < pageCount}">
	      &nbsp;<a href="<c:url value="reports"><c:param name="page" value="${page + 1}"/><c:param name="artifactid" value="${artifactid}"/><c:param name="groupid" value="${groupid}"/></c:url>">Next &gt;</a>
	    </c:if>
	  </c:if>
	</c:when>    
	<c:otherwise>
		<p>Click the link <b>"Load"</b> to read the reports from nexus</p>
	</c:otherwise>
</c:choose>
</div>
<!-- /container -->
</body>
</html>
