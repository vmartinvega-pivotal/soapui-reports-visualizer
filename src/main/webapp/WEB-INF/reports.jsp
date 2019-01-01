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
      <form class="navbar-form pull-right">
        <select name="field">
          <option value="title">Title</option>
          <option value="director">Director</option>
          <option value="genre">Genre</option>
        </select>
        <select name="env">
          <option value="coll">Collaudo Evolutivo</option>
          <option value="collcons">Collaudo Consolidato</option>
          <option value="dev1">Dev1</option>
        </select> 
        <input type="text" name="key" size="20">
        <button type="submit" class="btn">Filter</button>
      </form>
      <!--/.nav-collapse -->
    </div>
  </div>
</div>

<div class="container">

  <h1>SoapUi Reports</h1>

  <table class="table table-striped table-bordered">
    <thead>
    <tr>
      <th>Artifact Id</th>
      <th>Group Id</th>
      <th>Environment</th>
      <th>Url</th>
      <th>&nbsp;</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${reports}" var="report">
      <tr>
        <td><c:out value="${report.artifactId}"/></td>
        <td><c:out value="${report.groupId}"/></td>
        <td><c:out value="${report.environment}"/></td>
        <td><c:out value="${report.url}"/></td>
        <td><a href="?action=Remove&id=${report.url}"><i class="icon-trash"></i></a></td>
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
