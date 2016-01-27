<%@ include file="../layout/taglib.jsp"%>

<form:form modelAttribute="comment" class="form-horizontal" role="form">
	<legend>
		Add New Comment for Book <b><i>"${book.title}"</i></b>:
	</legend>
	<form:input type="hidden" path="comment_id" id="comment_id" />
	<div class="form-group">
		<label for="comment" class="control-label col-sm-2">Comment:</label>
		<div class="col-sm-5">
			<form:textarea path="comment" id="comment" rows="5" cols="30"
				class="form-control" />
			<form:errors path="comment" cssClass="error" />
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-2"></div>
		<div class="col-sm-2">
			<form:form action="comments/new" method="POST">
				<button type="submit" class="btn btn-primary form-control">Add</button>
			</form:form>
		</div>
	</div>
	<br />
<div class="row">
	<div class="col-md-offset-2 col-md-6 col-xs-12">
		<div class="btn-toolbar">
			<div class="btn-group">
				<a
					href="/MyLibrary/authors/${book.author.id}/books/${book.id}/comments"
					class="btn btn-primary" role="button"> <span
					class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span>
					Back
				</a>
			</div>
		</div>
	</div>
</div>
</form:form>