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
      <a class="brand" href="/artifacts">Reports</a>
      <!--/.nav-collapse -->
    </div>
  </div>
</div>

<div class="container">

  <h3>Reports for <c:out value="${groupid}"/>  <c:out value="${artifactid}"/></h3>

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
      <a href="<c:url value="reports"><c:param name="page" value="${page - 1}"/><c:param name="field" value="${field}"/><c:param name="key" value="${key}"/></c:url>">&lt; Prev</a>&nbsp;
    </c:if>
    Showing records ${start} to ${end} of ${count}
    <c:if test="${page < pageCount}">
      &nbsp;<a href="<c:url value="reports"><c:param name="page" value="${page + 1}"/><c:param name="field" value="${field}"/><c:param name="key" value="${key}"/></c:url>">Next &gt;</a>
    </c:if>
  </c:if>
</div>
<!-- /container -->
</body>
</html>
