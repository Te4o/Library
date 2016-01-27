<%@ include file="../layout/taglib.jsp"%>

<nav class="navbar navbar-default">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle collapsed"
			data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
	</div>
	<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		<ul class="nav navbar-nav">
			<li><a href="/MyLibrary/"> <span
					class="glyphicon glyphicon-home" aria-hidden="true"></span> Home
			</a></li>
			<li><a href="/MyLibrary/login/"> <span
					class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>
					Login
			</a></li>
		</ul>
	</div>
</nav>
<form:form modelAttribute="user" action="/MyLibrary/register"
	class="form-horizontal">
	<div class="register-form">
		<div>
			<form:input type="hidden" path="id" id="id" />
			<h2>
				<b>Registration Form</b>
			</h2>
			<br />
			<div class="form-group">
				<label class="control-label col-sm-2" for="username">Username:</label>
				<div class="col-sm-6 col-md-4">
					<form:input path="username" id="username" class="form-control"
						autocomplete="off" />
					<form:errors path="username" cssClass="error" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="password">Password:</label>
				<div class="col-sm-6 col-md-4">
					<form:input path="password" id="password" class="form-control"
						autocomplete="off" type="password" />
					<form:errors path="password" cssClass="error" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="firstName">First
					Name:</label>
				<div class="col-sm-6 col-md-4">
					<form:input path="firstName" id="firstName" class="form-control"
						autocomplete="off" />
					<form:errors path="firstName" cssClass="error" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="lastName">Last
					Name:</label>
				<div class="col-sm-6 col-md-4">
					<form:input path="lastName" id="lastName" class="form-control"
						autocomplete="off" />
					<form:errors path="lastName" cssClass="error" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="email">Email:</label>
				<div class="col-sm-6 col-md-4">
					<form:input path="email" id="email" class="form-control"
						autocomplete="off" type="email" />
					<form:errors path="email" cssClass="error" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-primary">Register</button>
				</div>
			</div>
		</div>
	</div>
</form:form>
