<%@ include file="../layout/taglib.jsp"%>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>

<div class="jumbotron">
	<h1 class="text-center">Users</h1>
</div>
<form:form action="search" method="GET">
	<div class="row">
		<div class="col-md-12">
			<div class="input-group">
				<span class="input-group-btn">
					<button class="btn btn-default" type="submit">Go!</button>
				</span> <input type="text" class="form-control" name="username"
					placeholder="Search for User by Username">
			</div>
		</div>
	</div>
</form:form>
<c:choose>
	<c:when test="${emptyListOfUsers}">
		<h3 class="text-center">The list is empty!</h3>
	</c:when>
	<c:when test="${noSuchUserFound}">
		<h3 class="text-center">No such user was found!</h3>
	</c:when>
	<c:otherwise>
		<div class="row">
			<div class="col-md-2 col-xs-4 text-center">
				<h3>Name</h3>
			</div>
			<div class="col-md-2 col-xs-1 text-center"></div>

			<div class="col-md-2 col-xs-4 text-center">
				<h3>Status</h3>
			</div>
			<div class="col-md-2 col-xs-4 text-center">
				<h3>Role</h3>
			</div>

		</div>
		<c:forEach items="${allUsers}" var="user">
			<div class="row">
				<div class="col-md-2 col-xs-4 text-center">
					<c:choose>
						<c:when test="${currentUserID == user.id}">
							<h5>
								<a href="<c:url value='/users/${user.id}/editProfile' />"><c:out
										value="${user.username}" /></a>
							</h5>
						</c:when>
						<c:otherwise>
							<a href="<c:url value='/users/${user.id}/showProfile' />"><c:out
									value="${user.username}" /></a>
						</c:otherwise>
					</c:choose>

				</div>

				<c:choose>
					<c:when test="${currentUserID == user.id}">

						<div class="col-md-2 col-xs-1 text-center">
							<a href="#" class="btn btn-primary btn-md disabled" role="button">
								<span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
							</a>
						</div>
					</c:when>
					<c:otherwise>
						<div class="col-md-2 col-xs-1 text-center">
							<a href="/MyLibrary/messages/${currentUserID}/new/${user.id}"
								class="btn btn-primary btn-md active" role="button"> <span
								class="glyphicon glyphicon-envelope" aria-hidden="true"></span></a>
						</div>
					</c:otherwise>
				</c:choose>

				<div class="col-md-2 col-xs-4 text-center">
					<h5>
						<c:out value="${user.userStatus}" />
					</h5>
				</div>
				<div class="col-md-2 col-xs-4 text-center">
					<h5>
						<c:out value="${user.userRole}" />
					</h5>
				</div>
				<sec:authorize access="hasAuthority('ADMIN')">
					<c:choose>
						<c:when test="${ user.userRole == 'ADMIN'}">
							<div class="col-md-2">
								<div class="btn-group btn-group-justified">
									<div class="btn-group">
										<button type="submit" class="btn btn-primary disabled">Change
											Status</button>
									</div>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="col-md-2">
								<div class="btn-group btn-group-justified">
									<sec:authorize access="hasAuthority('ADMIN')">
										<div class="btn-group">
											<form:form action="${user.id}" method="PUT">
												<button type="submit" class="btn btn-primary">Change
													Status</button>
											</form:form>
										</div>
									</sec:authorize>

								</div>
							</div>
						</c:otherwise>
					</c:choose>
				</sec:authorize>
			</div>
			<br />
		</c:forEach>
		<!-- pagination -->
		<c:choose>
			<c:when test="${numberOfUsers > 5}">
				<div class="text-center">
					<tag:paginate max="15" offset="${offset}" count="${numberOfUsers}"
						uri="../users/" next="&raquo;" previous="&laquo;" />
				</div>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
		<!-- end of pagination -->
	</c:otherwise>
</c:choose>