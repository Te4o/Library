<%@ include file="../../layout/taglib.jsp"%>

<form:form modelAttribute="user" class="form-horizontal" role="form">
	<div>
		<div class="change-password-form">
			<form:input type="hidden" path="id" id="id" />
			<h2>
				<b>${username}'s Password</b>
			</h2>
			<br />
			<form action="/changePassword" method="POST" class="form-horizontal">
				<form:input type="hidden" path="id" id="id" />
				<div class="form-group">
					<label class="control-label col-md-3" for="password">Current
						Password: </label>
					<div class="col-md-4">
						<form:input path="password" id="password" class="form-control"
							autocomplete="off" type="password" />
						<form:errors path="password" cssClass="error" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3" for="newPassword">New
						Password:</label>
					<div class="col-md-4">
						<form:input path="newPassword" id="newPassword"
							class="form-control" autocomplete="off" type="password" />
						<form:errors path="newPassword" cssClass="error" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3" for="newPassword2">Confirm
						New Password:</label>
					<div class="col-md-4">
						<form:input path="newPassword2" id="newPassword2"
							class="form-control" autocomplete="off" type="password" />
						<form:errors path="newPassword2" cssClass="error" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-offset-3 col-md-4">
						<button type="submit" class="btn btn-primary btn-block">
							<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
							Change Password
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</form:form>


