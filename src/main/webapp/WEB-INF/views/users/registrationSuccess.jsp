<%@ include file="../layout/taglib.jsp"%>

<h2>Confirmation message:</h2>
<br />
<h4>
	User <strong><c:out value="${newUser}" /></strong> has been registered
	successfully.
</h4>
<h5>
	Now you can <a href="<c:url value="/login" />">log in</a> or Go back to
	<a href="<c:url value='/' />">Home</a>
</h5>