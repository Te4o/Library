<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="jumbotron">
	<h1 class="text-center">Welcome To Our Library</h1>
</div>
<div class="row">
	<div class="col-md-4 col-xs-12">
		<div class="panel panel-default">
			<div class="panel-body text-center">Number Of Authors</div>
			<div class="panel-footer text-center">${authorsCount}</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-body text-center">Number Of Books</div>
			<div class="panel-footer text-center">${booksCount}</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-body text-center">Number Of Users</div>
			<div class="panel-footer text-center">${usersCount}</div>
		</div>
	</div>
	<br />
	<div class="col-md-8 col-xs-12">
		<img class="img-responsive"
			src="<c:url value='/resources/images/reader.jpg' />"
			alt="A guy reading a book">
	</div>
</div>

