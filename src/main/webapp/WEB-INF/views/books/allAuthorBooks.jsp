<%@ include file="../layout/taglib.jsp" %>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>			
		
<div class="jumbotron">
	<h1 class="text-center">${author.name}'s Books</h1>
</div>
		<form:form action="search" method="GET">
			<div class="row">
				<div class="col-md-12">
					<div class="input-group">
						<span class="input-group-btn">
							<button class="btn btn-default" type="submit">Go!</button>
						</span> 
						<input type="text" class="form-control" name="bookTitle"
							placeholder="Search for Book by Book Title"/>
					</div>
				</div>
			</div>
		</form:form>
		<c:choose>
			<c:when test="${emptyListOfAuthorBooks}">
				<h3 class="text-center">The list of <b><c:out value="${author.name}" /></b>'s books is empty!</h3>
			</c:when>
			<c:when test="${noSuchBookFound}">
				<h3 class="text-center">No such book from <c:out value="${author.name}" /> was found!</h3>
			</c:when>
		<c:otherwise>
		<div class="row">						
			<div class="col-md-4 col-xs-4 text-center">
				<h2>Title</h2>
			</div>
			<div class="col-md-4 col-xs-4 text-center">
				<h2>Status</h2>
			</div>
		</div>
		<c:forEach items="${books}" var="book">
			<div class="row">																					
				<div class="col-md-4 col-xs-4 text-center">					
					<h4>
						<a href="<c:url value='../books/${book.id}/preview' />" ><c:out value="${book.title}" /></a>
					</h4>
				</div>
				<div class="col-md-4 col-xs-4 text-center">
					<h4>${book.status}</h4>
				</div>
				<div class="col-md-4 col-xs-4">
					<div class="btn-toolbar">	
						<sec:authorize access="hasAuthority('ADMIN')">
							<div class="btn-group">
								<form:form action="${book.id}" method="GET">
									<button type="submit" class="btn btn-default">
									<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Edit</button>
								</form:form>
							</div>
							<div class="btn-group">
								<form:form action="${book.id}" method="DELETE" >
									<button
									onclick="if (confirm('Are you sure you want to delete this book?')) { form.action='${book.id}'; } else { return false; }"
									id="deleteForm" type="submit" class="btn btn-default">
									<span class="glyphicon glyphicon-trash" aria-hidden="true"></span> Delete</button>
								</form:form>
							</div>
							</sec:authorize>					
					</div>
				</div>
			</div>
		</c:forEach>
		<br />
	</c:otherwise>
</c:choose>			
<br />
<sec:authorize access="hasAuthority('ADMIN')">
	<div class="row">
		<div class="col-md-offset-4 col-md-4">
			<form:form action="new" method="GET">
				<button type="submit" class="btn btn-primary btn-block">
				<span class="glyphicon glyphicon-book" aria-hidden="true"></span> Add New Book</button>
			</form:form>
		</div>
	</div>
</sec:authorize>

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