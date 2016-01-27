<%@ include file="../layout/taglib.jsp"%>

<form:form modelAttribute="message" class="form-horizontal" role="form">
	<legend>
		Send new Message to <b>${receiver.username}</b>
	</legend>
	<form:input type="hidden" path="message_id" id="message_id" />
	<div class="form-group">
		<label for="header" class="control-label col-sm-1">Subject:</label>
		<div class="col-sm-5">
			<form:input path="header" id="header" class="form-control" />
			<form:errors path="header" />
		</div>
	</div>
	<div class="form-group">
		<label for="body" class="control-label col-sm-1">Message:</label>
		<div class="col-sm-5">
			<form:textarea path="body" rows="5" cols="30" id="body"
				class="form-control" />
			<form:errors path="body" />
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-1"></div>
		<div class="col-sm-2">
			<form:form action="/messages/${currentUserID}/new/${receiver.id}"
				method="POST">
				<button type="submit" class="btn btn-primary form-control">Send</button>
			</form:form>
		</div>
	</div>
</form:form>
