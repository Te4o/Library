<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras"
	prefix="tilesx"%>

<nav class="navbar navbar-default">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle collapsed"
			data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
	</div>
	<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		<ul class="nav navbar-nav">
			<sec:authorize access="hasAuthority('USER') OR hasAuthority('ADMIN')">
				<li><a href="/MyLibrary/"> <span
						class="glyphicon glyphicon-home" aria-hidden="true"></span> Home
				</a></li>
			</sec:authorize>
			<sec:authorize access="hasAuthority('USER') OR hasAuthority('ADMIN')">
				<li><a href="/MyLibrary/books/"> <span
						class="glyphicon glyphicon-book" aria-hidden="true"></span> Books
				</a></li>
			</sec:authorize>
			<sec:authorize access="hasAuthority('USER') OR hasAuthority('ADMIN')">
				<li><a href="/MyLibrary/authors/"> <span
						class="glyphicon glyphicon-star" aria-hidden="true"></span>
						Authors
				</a></li>
			</sec:authorize>
			<sec:authorize access="hasAuthority('USER') OR hasAuthority('ADMIN')">
				<li><a href="/MyLibrary/users/"> <span
						class="glyphicon glyphicon-user" aria-hidden="true"></span> Users
				</a></li>
			</sec:authorize>
			<sec:authorize access="hasAuthority('USER') OR hasAuthority('ADMIN')">
				<li><a href="/MyLibrary/messages/${currentUserID}/inbox"><span
						class="badge">${unreadMessages}</span> Inbox</a></li>
			</sec:authorize>
			<sec:authorize access="hasAuthority('USER') OR hasAuthority('ADMIN')">
				<li><a href="/MyLibrary/messages/${currentUserID}/outbox">
						<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						Outbox
				</a></li>
			</sec:authorize>
			<sec:authorize access="hasAuthority('USER')">
				<li><a href="/MyLibrary/books/${currentUserID}"> <span
						class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
						Books History
				</a></li>
			</sec:authorize>
			<sec:authorize access="hasAuthority('ADMIN')">
				<li><a href="/MyLibrary/books/loaned"> <span
						class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span>
						Loaned Books
				</a></li>
			</sec:authorize>
			<sec:authorize access="!isAuthenticated()">
				<li class="${current == 'login' ? 'active' : '' }"><a
					href="/MyLibrary/login/"> <span
						class="glyphicon glyphicon-log-in" aria-hidden="true"></span>
						Login
				</a></li>
				<li class="${current == 'register' ? 'active' : '' }"><a
					href="/MyLibrary/register/"> <span
						class="glyphicon glyphicon-copy" aria-hidden="true"></span>
						Register
				</a></li>
			</sec:authorize>
		</ul>
		<sec:authorize access="isAuthenticated()">
			<p class="text-right">
				Hello, <a href="/MyLibrary/users/${currentUserID}/editProfile/"><strong><sec:authentication
							property="principal.username" /></strong></a>
			</p>
			<form action="/MyLibrary/logout" method="post"
				class="navbar-form navbar-right">
				<button type="submit" class="btn btn-default btn-sm">
					<span class="glyphicon glyphicon-log-out"></span>Log out
				</button>
			</form>
		</sec:authorize>
	</div>
</nav>