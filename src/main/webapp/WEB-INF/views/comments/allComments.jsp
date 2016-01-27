<%@ include file="../layout/taglib.jsp"%>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>
<div class="jumbotron">
	<h2 class="text-center">
		<b>${author.name}/${book.title}/List of Comments</b>
	</h2>
</div>
<table class="table table-striped">
	<tr>
		<c:choose>
			<c:when test="${isEmpty}">
				<h3 class="text-center">There are no comments posted for this
					book yet !</h3>
			</c:when>
			<c:otherwise>
				<c:forEach items="${comments}" var="comment">
					<tr>
						<td>"<c:out value="${comment.comment}" />"
						</td>
						<c:choose>
							<c:when test="${currentUserID == comment.user.id}">
								<td>posted by <a
									href="<c:url value='/users/${comment.user.id}/editProfile' />"><b><c:out
												value="${comment.user.username}" /></b></a></td>
							</c:when>
							<c:otherwise>
								<td>posted by <a
									href="<c:url value='/users/${comment.user.id}/showProfile' />"><b><c:out
												value="${comment.user.username}" /></b></a></td>
							</c:otherwise>
						</c:choose>
						<sec:authorize access="hasAuthority('ADMIN')">
							<td><form:form action="comments/${comment.comment_id}"
									method="DELETE">
									<button
										onclick="if (confirm('Are you sure you want to delete this comment?')) { form.action='comments/${comment.comment_id}'; } else { return false; }"
										type="submit" class="btn btn-default">
										<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
										Delete
									</button>
								</form:form></td>
						</sec:authorize>
					</tr>
					<br />
				</c:forEach>
				<br />
			</c:otherwise>
		</c:choose>
	</tr>
</table>
<div class="row">
	<div class="col-md-offset-4 col-md-4">
		<form:form action="comments/new" method="GET">
			<button type="submit" class="btn btn-primary btn-block">Add
				New Comment</button>
		</form:form>
	</div>
</div>
<br />
<div>
	<div class="btn-group">
		<a href="/MyLibrary/authors/${author.id}/books/${book.id}/preview"
			class="btn btn-primary" role="button"> <span
			class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span>
			Back
		</a>
	</div>
</div>
