<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<c:if test="${empty requestScope.employees }">
		没有员工信息!
	</c:if>
	
	<c:if test="${!empty requestScope.employees}">
		<table border="1" cellpadding="10" cellspacing="0">
			<tr>
				<th>ID</th>			
				<th>LastName</th>			
				<th>Email</th>			
				<th>Gender</th>			
				<th>DepartName</th>			
				<th>Edit</th>			
				<th>Delete</th>			
			</tr>
			<c:forEach items="${requestScope.employees }" var="emp">
				<tr>
					<td>${emp.id }</td>
					<td>${emp.employeeName }</td>
					<td>${emp.email }</td>
					<td>${emp.gender == 0 ? '男' : '女' }</td>
					<td>${emp.department.departmentName }</td>
					<td><a href="emp/${emp.id }">Edit</a></td>
					<td><a class="delete" href="emp/${emp.id }">Delete</a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

	<a href="emp">Add New Emp</a>
	
	<!-- 超链接肯定是get请求  需要将GET请求通过 JS 的功能将请求进行转换 -->
	<form action="" method="post">
		<input type="hidden" name="_method" value="DELETE" />
	</form>
</body>
<!--  
	此处会有一个请求静态资源请求不到的问题
	原因:
		优雅的REST风格的URL请求不希望带.html或者.do
		此时如果将DispatcherServlet的请求映射设置为 /
		则SpringMVC将捕获所有的请求   包括静态资源的请求  SpringMVC 会将其作为普通请求进行处理
		此时请求静态资源肯定会报错  而且请求不到
	解决办法:
		在SpringMVC的配置文件中配置 <mvc:default-serlvet-handler />
-->
<script type="text/javascript" src="scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		$(".delete").click(function() {
			var href = $(this).attr("href");
			$("form").attr("action", href).submit();
			return false;
		});
	})
</script>
</html>