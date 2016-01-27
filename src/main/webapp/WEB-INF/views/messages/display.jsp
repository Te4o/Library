<%@ include file="../layout/taglib.jsp"%>

<table class="table table-striped">
	<c:forEach items="${parents}" var="parent">
		<tr>
			<td>
				<p>
					<b>From: </b>${parent.sender.username}</p>
				<p>
					<b>To: </b>${parent.receiver.username}</p>
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
<input type="hidden" name="${_csrf.parameterName}"
	value="${_csrf.token}" />
