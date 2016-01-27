<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/taglib.jsp"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras"
	prefix="tilesx"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><tiles:getAsString name="title"></tiles:getAsString></title>
<link rel="shortcut icon" href="/MyLibrary/resources/book.ico" />
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.css" />
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css"
	href="<spring:url value="/resources/rating-plugin/css/star-rating.min.css"/>"
	media="all">
<link rel="stylesheet" type="text/css"
	href="<spring:url value="/resources/file-input-plugin/css/fileinput.min.css"/>"
	media="all">

<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script
	src="<spring:url value="/resources/rating-plugin/js/star-rating.min.js"/>"></script>

</head>
<body style="background-color: rgba(245, 245, 245, 0.5);">
	<tilesx:useAttribute name="current" />
	<div class="container">
		<tiles:insertAttribute name="header" />
		<tiles:insertAttribute name="body" />
		<tiles:insertAttribute name="footer" />
	</div>
	<script
		src="<spring:url value="/resources/file-input-plugin/js/plugins/canvas-to-blob.min.js"/>"
		type="text/javascript"></script>
	<script
		src="<spring:url value="/resources/file-input-plugin/js/fileinput.min.js"/>"></script>
	<script
		src="<spring:url value="/resources/file-input-plugin/js/fileinput-custom.js"/>"></script>
</body>
</html>



