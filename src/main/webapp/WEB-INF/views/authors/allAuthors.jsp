<%@ include file="../layout/taglib.jsp"%>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>

<div class="jumbotron">
	<h1 class="text-center">Authors</h1>
</div>

<form:form action="search" method="GET">
	<div class="row">
		<div class="col-md-12">
			<div class="input-group">
				<span class="input-group-btn">
					<button class="btn btn-default" type="submit">Go!</button>
				</span> <input type="text" class="form-control" name="author_name"
					placeholder="Search for Author by Author's Name" />
			</div>
		</div>
	</div>
</form:form>
<c:choose>
	<c:when test="${emptyListOfAuthors}">
		<h3 class="text-center">The list is empty!</h3>
	</c:when>
	<c:when test="${noSuchAuthorFound}">
		<h3 class="text-center">No such author was found!</h3>
	</c:when>
	<c:otherwise>
		<div class="row">
			<div class="col-md-4 col-xs-4 text-center">
				<h2>Name</h2>
			</div>
			<div class="col-md-4 col-xs-4 text-center">
				<h2>Country</h2>
			</div>
		</div>
		<c:forEach items="${authors}" var="author">
			<div class="row">
				<div class="col-md-4 col-xs-4 text-center">
					<h4>
						<c:out value="${author.name}" />
					</h4>
				</div>
				<div class="col-md-4 col-xs-4 text-center">
					<h4>${author.country}</h4>
				</div>
				<div class="col-md-4 col-xs-4">
					<div class="btn-toolbar">
						<sec:authorize access="hasAuthority('ADMIN')">
							<div class="btn-group">
								<form:form action="${author.id}" method="GET">
									<button type="submit" class="btn btn-default">
										<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
										Edit
									</button>
								</form:form>
							</div>
						</sec:authorize>
						<sec:authorize access="hasAuthority('ADMIN')">
							<div class="btn-group">
								<form:form action="${author.id}" method="DELETE">
									<button
										onclick="if (confirm('Are you sure you want to delete this author?'))
							{ form.action='${author.id}'; } else { return false; }"
										type="submit" class="btn btn-default">
										<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
										Delete
									</button>
								</form:form>
							</div>
						</sec:authorize>
						<div class="btn-group">
							<form:form action="${author.id}/books/" method="GET">
								<button type="submit" class="btn btn-info">
									<span class="glyphicon glyphicon-book" aria-hidden="true"></span>
									Books
								</button>
							</form:form>
						</div>
					</div>
				</div>
			</div>
			<br />
		</c:forEach>
		<br />
		<sec:authorize access="hasAuthority('ADMIN')">
			<div class="row">
				<div class="col-md-offset-4 col-md-4">

					<form:form action="/MyLibrary/authors/new/" method="GET">
						<button type="submit" class="btn btn-primary btn-block">Add
							New Author</button>
					</form:form>

				</div>
			</div>
		</sec:authorize>
		<!-- pagination -->
		<c:choose>
			<c:when test="${numberOfAuthors > 5}">
				<div class="text-center">
					<tag:paginate max="15" offset="${offset}"
						count="${numberOfAuthors}" uri="../authors/" next="&raquo;"
						previous="&laquo;" />
				</div>
			</c:when>
		</c:choose>
		<!-- end of pagination -->
	</c:otherwise>
</c:choose>