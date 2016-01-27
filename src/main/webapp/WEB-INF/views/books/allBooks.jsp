<%@ include file="../layout/taglib.jsp"%>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>

<div class="jumbotron">
	<h1 class="text-center">Books</h1>
</div>
		<form:form action="search" method="GET">
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
						<span class="input-group-btn">
							<button class="btn btn-default" type="submit">Go!</button>
						</span> <input type="text" class="form-control" name="bookTitle"
							placeholder="Search for Book by Title">
					</div>
				</div>
			</div>
		</form:form>
		<c:choose>
		<c:when test="${emptyListOfBooks}">
				<h3 class="text-center">The list is empty!</h3>
			</c:when>
			<c:when test="${noSuchBookFound}">
				<h3 class="text-center">No such book was found!</h3>
			</c:when>
			<c:otherwise>
		<div class="row">
			<div class="col-md-4 col-xs-4 text-center">
				<h2>Title</h2>
			</div>
			<div class="col-md-4 col-xs-4 text-center">
				<h2>Author</h2>
			</div>
			<div class="col-md-4 col-xs-4 text-center">
				<h2>Status</h2>
			</div>
		</div>
		<c:forEach items="${books}" var="book">
			<div class="row">
				<div class="col-md-4 col-xs-4 text-center">
					<h4>
						<a
							href="<c:url value='/authors/${book.author.id}/books/${book.id}/preview' />">${book.title}</a>
					</h4>
				</div>
				<div class="col-md-4 col-xs-4 text-center">
					<h4>
						<a href="<c:url value='/authors/${book.author.id}/books/' />">${book.author.name}</a>
					</h4>
				</div>
				<div class="col-md-4 col-xs-4 text-center">
					<h4>${book.status}</h4>
				</div>
			</div>
			<br />
		</c:forEach>
		<!-- pagination -->
		<c:choose>
			<c:when test="${numberOfBooks > 5}">
				<div class="text-center">
					<tag:paginate max="15" offset="${offset}" count="${numberOfBooks}"
						uri="../books/" next="&raquo;" previous="&laquo;" />
				</div>
			</c:when>
		</c:choose>
		<!-- end of pagination -->
	</c:otherwise>
</c:choose>
