<%@ include file="../layout/taglib.jsp"%>

<form:form modelAttribute="message" class="form-horizontal" role="form">
	<legend>
		Reply to <b>${receiver}</b>
	</legend>
	<form:input type="hidden" path="message_id" id="message_id" />
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
			<form:form action="/messages/${currentUserID}/${message_id}/reply"
				method="POST">
				<button type="submit" class="btn btn-primary form-control">
					<span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>
					Reply
				</button>
			</form:form>
		</div>
	</div>
	<table class="table table-striped">
		<c:forEach items="${parents}" var="parent">
			<tr>
				<td>
					<p>
						<b>From: </b><c:out value="${parent.sender.username}" /></p>
					<p>
						<b>To: </b><c:out value="${parent.receiver.username}" /></p>
					<p>
						<b>Date: </b>
						<fmt:formatDate pattern="yyyy-MM-dd, hh:mm a"
							value="${parent.date}" />
					</p>
					<p>
						<b>Subject: </b><c:out value="${parent.header}" /></p>
					<p>
						<b>Message: </b><c:out value="${parent.body}" /></p>
				</td>
			</tr>
		</c:forEach>
	</table>
</form:form>