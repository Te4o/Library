<%@ include file="../layout/taglib.jsp"%>

<form:form class="form-horizontal" role="form" modelAttribute="book"
	enctype="multipart/form-data">
	<legend>Book Details</legend>
	<form:input type="hidden" path="id" id="id" />
	<div class="form-group">
		<label for="title" class="control-label col-md-2">Title:</label>
		<div class="col-md-6">
			<form:input path="title" id="title" class="form-control" />
			<form:errors path="title" cssClass="error" />
		</div>
	</div>
	<div class="form-group">
		<label for="pages" class="control-label col-md-2">Pages:</label>
		<div class="col-md-6">
			<form:input path="pages" id="pages" class="form-control" />
			<form:errors path="pages" cssClass="error" />
		</div>
	</div>
	<div class="form-group">
		<label for="bookDescription" class="control-label col-md-2">Description:</label>
		<div class="col-md-6">
			<form:textarea path="bookDescription" id="bookDescription" rows="8"
				cols="30" class="form-control" />
			<form:errors path="bookDescription" cssClass="error" />
		</div>
	</div>
	<div class="form-group">

		<label for="fileUpload" class="control-label col-md-2">Pic:</label>
		<div class="col-md-6">
			<input id="input-21" type="file" name="fileUpload"
				class="file-loading" accept="image/*"></input>
			<form:errors path="image" cssClass="error" />
		</div>
	</div>
	<c:choose>
		<c:when test="${edit}">
			<div class="form-group">
				<div class="col-md-2"></div>
				<div class="col-md-5">
					<form:form action="${book.id}" method="POST">
						<button type="submit" class="btn btn-primary">
							<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
							Edit
						</button>
					</form:form>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="form-group">
				<div class="col-md-2"></div>
				<div class="col-md-5">
					<form:form action="new" method="POST">
						<button type="submit" class="btn btn-primary">
							<span class="glyphicon glyphicon-book" aria-hidden="true"></span>
							Add
						</button>
					</form:form>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</form:form>

