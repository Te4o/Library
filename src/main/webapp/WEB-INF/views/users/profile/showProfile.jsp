<%@ include file="../../layout/taglib.jsp"%>

<div class="col-md-offset-2 col-xs-offset-4">
	<div class="row">
		<h3>
			<strong>${username}'s Profile</strong>
		</h3>
	</div>
	<div class="row">
		<div class="col-md-2 col-xs-4"><b>First Name:</b></div>
		<div class="col-md-2 col-xs-4">
			<c:out value="${user.firstName}" />
		</div>
	</div>
	<div class="row">
		<div class="col-md-2 col-xs-4"><b>Last Name:</b></div>
		<div class="col-md-2 col-xs-4">
			<c:out value="${user.lastName}" />
		</div>
	</div>
	<div class="row">
		<div class="col-md-2 col-xs-4"><b>Email:</b></div>
		<div class="col-md-2 col-xs-4">
			<c:out value="${user.email}" />
		</div>
	</div>
	<div class="row">
		<div class="col-md-2 col-xs-4"><b>Status:</b></div>
		<div class="col-md-2 col-xs-4">
			<c:out value="${user.userStatus}" />
		</div>
	</div>
	<div class="row">
		<div class="col-md-2 col-xs-4"><b>Role:</b></div>
		<div class="col-md-2 col-xs-4">
			<c:out value="${user.userRole}" />
		</div>
	</div>
</div>
<br />
<div class="row">
	<div class="col-md-offset-2 col-xs-offset-4">
		<div class="btn-group">
			<a href="/MyLibrary/messages/${currentUserID}/new/${user.id}"
				class="btn btn-info" role="button"> <span
				class="glyphicon glyphicon-envelope" aria-hidden="true"></span> Send
				Message
			</a>
		</div>
		<div class="btn-group">
			<sec:authorize access="hasAuthority('ADMIN')">
				<div class="btn-group">
					<form:form action="/MyLibrary/users/${user.id}" method="PUT">
						<button type="submit" class="btn btn-primary">Change
							Status</button>
					</form:form>
				</div>
			</sec:authorize>
		</div>
	</div>
</div>