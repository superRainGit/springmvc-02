package cn.zhang.springmvc.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.zhang.springmvc.beans.Employee;
import cn.zhang.springmvc.dao.EmployeeDao;
import cn.zhang.springmvc.exceptions.UserNotMatchPassException;

@Controller
public class SpringMVCTest {
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private ResourceBundleMessageSource messageResource;
	
	/**
	 * 测试使用 SimpleMappingExceptionResolver 进行异常的解析
	 * 使用此异常解析器可以 如同Struts2 一样对具体的映射进行页面的转发
	 * @param id
	 * @return
	 */
	@RequestMapping("/testSimpleMappingExceptionResolver")
	public String testSimpleMappingExceptionResolver(@RequestParam("id") Integer id) {
		String[] vals = new String[10];
		System.out.println(vals[id]);
		return "success";
	}
	
	/**
	 * DefaultHandlerExceptionResolver 使用来处理 Spring 的一些特殊异常的类
	 * 具体 DefaultHandlerExceptionResolver 可以处理哪些异常  可以参考其源代码
	 * @return
	 */
	@RequestMapping(value="/testDefaultHandlerExceptionResolver", method=RequestMethod.POST)
	public String testDefaultHandlerExceptionResolver() {
		return "success";
	}
	
	/**
	 * 同样的 @ResponseStatus 注解也可以用来修饰方法   此时该方法就相当于作为一种简单的
	 * 抛出异常的方法   并且在页面上显示对应的 错误码和 错误原因
	 * 这不同于使用  @ResponseStatus 修饰的异常类  只有在发生异常的时候才会被调用
	 * 被 @ResponseStatus 修饰的方法  会直接视为抛出异常  但是方法是完全正常执行的
	 * @param id
	 * @return
	 */
	@ResponseStatus(reason="简单的测试", value=HttpStatus.NOT_FOUND)
	@RequestMapping("/testResponseStatusExceptionResolver")
	public String testResponseStatusExceptionResolver(@RequestParam("id") Integer id) {
		if(id == 13) {
			throw new UserNotMatchPassException();
		}
		System.out.println("testResponseStatusExceptionResolver...");
		return "success";
	}
	
	/**
	 * 1. 使用 @ExceptionHandler 修饰的方法  入参可以传入一个Exception 对象  该对象标识的就是目标方法抛出的异常对象
	 * 2. 使用 @ExceptionHandler 修饰的方法不能传入 Map 来将异常对象传入到页面上,如果想要将异常对象进行传递,需要将返回值声明为ModelAndView类型
	 * 3. 使用 @ExceptionHandler 修饰的方法对异常的处理有优先级的问题: 与异常对象类型最接近的 @ExceptionHandler 匹配的方法会被执行
	 * 4. 如果当前Handler所在的类中没有 @ExceptionHandler 修饰的方法  那么就会在 IOC 容器中寻找有没有 @ControllerAdvise 修饰的
	 * 类  如果有  则寻找其中的使用 @ExceptionHandler 修饰的方法   并执行其中的处理异常的代码  如果IOC容器中没有使用 @ControllerAdvise 修饰
	 * 的类  那么就会简单的将异常抛向页面
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({ArithmeticException.class})
	public ModelAndView handleException(Exception ex, Map<String, Object> map) {
		System.out.println("发生了异常:" + ex);
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("exception", ex);
		return mv;
	}
	
	@RequestMapping("/testExceptionResolver")
	public String testExceptionHandlerExceptionResolver(@RequestParam("id") Integer id) {
		System.out.println(10 / id);
		return "success";
	}
	
	/**
	 * 使用  CommonsMultipartResolver 可以实现文件的上传
	 * 虽然  使用 @RequestBody 注解也可以实现文件的上传  但是
	 * 使用注解是将整个上传的  域对象进行了转换  需要自己手动去获取对应的文件的信息
	 * 
	 * 但是使用 CommonsMultipartResolver 就可以使用 MultipartFile
	 * 对象将上传上来的文件进行表示  从该对象中就可以获取到文件上传的详细信息
	 * @param desc
	 * @param file
	 * @return
	 * @See testHttpMessageConverter()
	 * @throws IOException 
	 */
	@RequestMapping("/testFileUpload")
	public String testFileUpload(@RequestParam("desc") String desc,
			@RequestParam("file") MultipartFile file) throws IOException {
		String fileName = file.getOriginalFilename();
		long size = file.getSize();
		InputStream in = file.getInputStream();
		System.out.println("fileNme : " + fileName);
		System.out.println("size : " + size);
		System.out.println("in : " + in);
		return "success";
	}
	
	@RequestMapping("/i18n")
	public String testI18n(Locale locale) {
		String val = messageResource.getMessage("i18n.username", null, locale);
		System.out.println(val);
		return "i18n";
	}
	
	/**
	 * 与 @ResponseBody 和 @RequestBody 注解相对应的是
	 * ResponseEntity<T> 和 HttpEntity<T>
	 * 这两者的使用方式是一样的   注解是根据返回值的类型来区分
	 * 而ResponseEntity和HttpEntity是根据<T>泛型来区分的
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/testResponseEntity")
	public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
		byte[] body = null;
		ServletContext context = session.getServletContext();
		InputStream in = context.getResourceAsStream("/files/sql1.png");
		body = new byte[in.available()];
		in.read(body);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename=sql1.png");
		HttpStatus status = HttpStatus.OK;
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, status);
		return response;
	}
	
	/**
	 * @ResponseBody 和 @RequestBody 注解  使用的是HttpMessageConverter
	 * SpringMVC 会根据  方法的返回值获取 入参的类型来选择不同的 HttpMessageConverter类型
	 * 注 : @ResponseBody 和 @RequestBody 不要求同时出现
	 * @param body
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/testHttpMessageConverter")
	public String testHttpMessageConverter(@RequestBody String body) {
		System.out.println(body);
		return "HelloWorld! " + new Date();
	}
	
	@RequestMapping("/testConversionService")
	public String testConversionService(Employee employee) {
		employeeDao.save(employee);
		return "redirect:/emps";
	}
}
