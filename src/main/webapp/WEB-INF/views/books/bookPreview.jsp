<%@ include file="../layout/taglib.jsp"%>

<div class="row">
	<c:if test="${largeSizeOfImage eq true}">
		<p class="btn btn-danger">Large SIZE</p>
	</c:if>
	<c:choose>
		<c:when test="${emptyListOfAuthorBooks}">
			<div class="col-xs-12 col-md-4">
				<div class="thumbnail">
					<img src="<c:url value='/resources/images/noCoverImage.jpg' />"
						alt="no book cover">
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="col-xs-12 col-md-4">
				<div class="thumbnail">
					<img src="${image}" alt="book cover" />
				</div>
			</div>
		</c:otherwise>
	</c:choose>
	<div class="col-xs-12 col-md-8">
		<div>
			<h3>
				<strong>Book Title: <c:out value="${book.title}" /></strong>
			</h3>
		</div>
		<dl class="dl-horizontal">
			<dt>Author:</dt>
			<dd>
				<a href="<c:url value='/authors/${book.author.id}/books/' />"> <c:out
						value="${book.author.name}" /></a>
			</dd>
			<dt>Pages:</dt>
			<dd>${book.pages}</dd>
			<dt>Description:</dt>
			<dd>
				<c:out value="${book.bookDescription}" />
			</dd>
			<dt>Status:</dt>
			<c:choose>
				<c:when
					test="${isBookLoaned && currentUserID == currentBookLoaner.id}">
					<dd>${book.status}
						by <a
							href="<c:url value='/users/${currentBookLoaner.id}/editProfile' />">
							<c:out value="${currentBookLoaner.username}" />
						</a>
					</dd>
				</c:when>
				<c:when test="${isBookLoaned}">
					<dd>${book.status}
						by <a
							href="<c:url value='/users/${currentBookLoaner.id}/showProfile' />">
							<c:out value="${currentBookLoaner.username}" />
						</a>
					</dd>
				</c:when>
				<c:otherwise>
					<dd>${book.status}</dd>
				</c:otherwise>
			</c:choose>
		</dl>
	</div>
</div>
<div class="row">
	<div class="col-md-offset-4 col-xs-12">
		<div class="btn-group">
			<a
				href="/MyLibrary/authors/${book.author.id}/books/${book.id}/comments"
				class="btn btn-info" role="button"> <span
				class="glyphicon glyphicon-comment" aria-hidden="true"></span> Show
				Comments
			</a>
		</div>
		<sec:authorize access="hasAuthority('ADMIN')">
			<div class="btn-group">
				<a href="/MyLibrary/authors/${book.author.id}/books/${book.id}"
					class="btn btn-default" role="button"> <span
					class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Edit
				</a>
			</div>
			<div class="btn-group">
				<form:form method="DELETE"
					action="/MyLibrary/authors/${book.author.id}/books/${book.id}">
					<button
						onclick="if (confirm('Are you sure you want to delete this book?')) { return true; } else { return false; }"
						id="deleteForm" type="submit" class="btn btn-default">
						<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
						Delete
					</button>
				</form:form>
			</div>
		</sec:authorize>
		<sec:authorize access="hasAuthority('USER')">
			<c:if test="${book.isRated == false}">
				<div class="btn-group">
					<a
						href="/MyLibrary/authors/${book.author.id}/books/${book.id}/rating"
						class="btn btn-info" role="button">Add Rating</a>
				</div>
			</c:if>
			<c:choose>
				<c:when test="${book.status =='Available'}">
					<div class="btn-group">
						<a
							href="/MyLibrary/books/${book.id}/${currentUserID}/addToHistory"
							class="btn btn-success" role="button">Get Book</a>
					</div>
				</c:when>
			</c:choose>
		</sec:authorize>
	</div>
</div>
<br />
<div class="row">
	<input id="input-6c" class="rating form-control hide" data-min="0"
		data-max="5" data-step="0.1" data-size="md">
</div>
<br />
<div>
	<div class="btn-group">
		<a href="../../books/" class="btn btn-primary" role="button"> <span
			class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span>
			Back
		</a>
	</div>
</div>


<!-- rating script -->
<script type="text/javascript">
	$('#input-6c').rating('create', {
		disabled : true
	});
	$('#input-6c').rating('update', '${book.averageRating}');
</script>
<!--end rating script -->
