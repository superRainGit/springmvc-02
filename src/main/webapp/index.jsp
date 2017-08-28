<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<a href="emps">List All Emps</a>
	
	<br><br>
	
	<form action="testHttpMessageConverter" method="post" enctype="multipart/form-data">
		File: <input type="file" name="file" />
		Desc: <input type="text" name="desc" />
		<input type="submit" value="Submit" />
	</form>

	<br><br>
	<a href="testResponseEntity">Test ResponseEntity</a>
	
	<!--  
		关于国际化:
		1. 在页面上能够根据浏览器语言设置的情况对文本(不是内容, 而是简单的文本字段), 时间, 数值进行本地化处理
		2. 可以在 bean 中获取国际化资源文件 Locale 对应的消息
		3. 可以通过超链接切换 Locale, 而不再依赖于浏览器的语音设置情况
		
		解决之道:
		1. 使用 JSTL fmt 标签就可以了
		2. 在 bean 中注入 ResourceBundleMessageSource 实例 , 使用其对应的 getMessage() 方法即可
		3. 配置  LocaleResolver 和  LocaleChangeInterceptor
	-->
	<br><br>
	<a href="i18n">I18N PAGE</a>
	
	<br><br>
	<form action="testFileUpload" method="post" enctype="multipart/form-data">
		File: <input type="file" name="file" />
		Desc: <input type="text" name="desc" />
		<input type="submit" value="Submit" />
	</form>
	
	<br><br>
	<a href="testExceptionResolver?id=10">Test ExceptionResolver</a>
	
	<br><br>
	<a href="testResponseStatusExceptionResolver?id=10">Test ResponseStatusExceptionResolver</a>

	<br><br>
	<a href="testDefaultHandlerExceptionResolver">Test DefaultHandlerExceptionResolver</a>

	<br><br>
	<a href="testSimpleMappingExceptionResolver?id=2">Test SimpleMappingExceptionResolver</a>
</body>
</html>