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
			<li><a href="/MyLibrary/register/"> <span
					class="glyphicon glyphicon-copy" aria-hidden="true"></span>
					Register
			</a></li>
		</ul>
	</div>
</nav>
<div class="login-form">
	<div>
		<c:url var="loginUrl" value="/login" />
		<form action="${loginUrl}" method="POST" class="form-horizontal">
			<c:if test="${param.error != null}">
				<div class="alert alert-danger">
					<p>Invalid username OR password.</p>
				</div>
			</c:if>
			<h2>
				<b>Login Form</b>
			</h2>
			<br />
			<div class="form-group">
				<label class="control-label col-sm-2" for="username">Username:</label>
				<div class="col-sm-6 col-md-4">
					<input type="text" class="form-control" id="username"
						name="username" placeholder="Enter Username">
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="password">Password:</label>
				<div class="col-sm-6 col-md-4">
					<input type="password" class="form-control" id="password"
						name="password" placeholder="Enter Password">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-primary">Log in</button>
				</div>
			</div>
		</form>
	</div>
</div>