<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 引用SpringMVC提供的From表单标签: 更快的开发页面  更方便的回显数据 -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<!--  
		当form表单直接提交数据的时候   有什么样的问题需要我们解决呢?
		1. 数据的格式化
		2. 数据的转换
		3. 数据合法性的校验
		这是就需要一个类型转换器和校验的组件来帮助我们进行数据的转换
		数据合法性的校验
			1) 导入实现了JSR 303 规范的Jar包
			2) 在 SpringMVC 配置文件中添加 <mvc:annotation-driven />
			3) 需要在 bean 的属性上添加对应的注解
			4) 在目标方法 bean 类型的前面添加 @Valid 注解
	-->
	<form action="testConversionService" method="post">
		<!-- 此时我们可以将字符串直接转为 Employee 的Bean对象 -->
		<input type="text" name="employee" />
		<br>
		<input type="submit" value="Submit" />
	</form>

	<!--  
		Neither BindingResult nor plain target object for bean name 'command' available as request attribute
		直接使用该便签会报一个这样的错误
		原因:
			因为SpringMVC默认表单肯定会回显的  所以它在页面做显示的时候  会从表单标签的modelAttribute属性中取值  然后去
			填充页面的属性   默认取值是从  command 的bean中取值  因为没有在handler中放入这样的值  因此会报这样的错误
		解决办法:
			在SpringMVC标签中  使用modelAttribute属性指定从哪个bean中取值
	-->
	<!-- 此处需要使用  绝对路径来代替相对路径   并且项目开发的时候使用绝对路径  而不希望使用相对路径  -->
	<form:form action="${pageContext.request.contextPath }/emp" method="post" modelAttribute="employee">
		<c:if test="${employee.id == null }">
			<!-- path:与普通标签的name属性一致  表示从对象的哪个属性中取值 -->
			LastName: <form:input path="employeeName"/>
			<form:errors path="employeeName"></form:errors>
			<br>
		</c:if>
		<c:if test="${employee.id != null }">
			<form:hidden path="id"/>
			<%--  
				此处不能使用SpringMVC的 from 标签    <form:hidden path="_method" />
				原因:
					1. 本身该标签不支持 value 属性
					2. 该标签的 path 需要 modelAttribute所代表的bean对象中有该属性  而一般的bean是不需要_method属性的
			--%>
			<input type="hidden" name="_method" value="PUT" />
		</c:if>
		Email: <form:input path="email"/>
		<form:errors path="email"></form:errors>
		<br>
		<%
			Map<String, String> gender = new HashMap<>();
			gender.put("1", "女");
			gender.put("0", "男");
			// 切记  要放入作用域中
			request.setAttribute("genders", gender);
		%>
		<!-- items:表示从哪个域对象中遍历   同时该标签可以指明 多个单选按钮之间的分隔符 -->
		<!-- 注:此处的items属性需要数据从 request 域中获取   所以需要使用EL表达式 -->
		Gender: <form:radiobuttons path="gender" items="${genders }" />
		<br>
		DeprtmentName: <form:select path="department.id" items="${depts }" 
			itemLabel="departmentName" itemValue="id"></form:select>
		<br>
		<!--  
			输入框中的数据不能直接映射到时间以及特殊的例如金额字段上   因为SpringMVC 不知道具体的映射规则
		-->
		Birth: <form:input path="birth"/>
		<form:errors path="birth"></form:errors>
		<br>
		Salary: <form:input path="salary"/>
		<br>
		<input type="submit" value="Submit" />
	</form:form>
	
</body>
</html>