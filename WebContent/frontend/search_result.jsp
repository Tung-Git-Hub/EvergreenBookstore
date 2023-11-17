<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Results for ${keyword} - Online Books Store</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div class="center">
		<c:if test="${fn:length(result) == 0}">
			<h2>No Results for "${keyword}"</h2>
		</c:if>
		<c:if test="${fn:length(result) > 0}">
			<div class="book-group">
				<center><h2>Results for "${keyword}":</h2></center>
				<c:forEach items="${result}" var="book">
					<div class="book">
						<div>
							<a href="view_book?id=${book.bookId}"> 
							<img class="book-small"
								src="data:image/jpg;base64,${book.base64Image}" />
							</a>
						</div>
						<div>
							<a href="view_book?id=${book.bookId}"> <b>${book.title}</b>
							</a>
						</div>
						<div>
							<jsp:directive.include file="book_rating.jsp" />				
						</div>
						<div>
							<i>by ${book.author}</i>
						</div>
						<div>
							<b>$${book.price}</b>
						</div>
					</div>
				</c:forEach>
			</div>
		</c:if>
	</div>

	<jsp:directive.include file="footer.jsp" />
</body>
</html>