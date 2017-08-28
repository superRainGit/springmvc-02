package cn.zhang.springmvc.handler;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.zhang.springmvc.beans.Employee;
import cn.zhang.springmvc.dao.DepartmentDao;
import cn.zhang.springmvc.dao.EmployeeDao;

@Controller
public class EmployeeHandler {

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private DepartmentDao departmentDao;
	
	@ModelAttribute
	public void getEmployee(@RequestParam(value="id", required=false) Integer id, Map<String, Object> map) {
		if(null != id) {
			map.put("employee", employeeDao.getEmp(id));
		}
	}

	@RequestMapping(value = "/emp", method = RequestMethod.PUT)
	public String update(Employee e) {
		employeeDao.save(e);
		return "redirect:/emps";
	}

	@RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
	public String input(@PathVariable("id") Integer id, Map<String, Object> map) {
		map.put("employee", employeeDao.getEmp(id));
		map.put("depts", departmentDao.getDepts());
		return "input";
	}

	@RequestMapping(value = "/emp/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id) {
		employeeDao.delete(id);
		return "redirect:/emps";
	}

	/**
	 * 可以在方法的参数中  使用 BindingResult 对象来标识 转换、格式化以及校验的错误信息
	 * 
	 * 当使用了 @Valid 注解进行 字段的校验的时候   同时需要使用 BindingResult 进行校验错误信息的
	 * 接收   那么就必须保证这两个是成对出现的   这样这两个参数之间不能加任何其他的参数
	 * @param employee
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/emp", method = RequestMethod.POST)
	public String save(@Valid Employee employee, BindingResult result, Map<String, Object> map) {
		System.out.println(employee);
		if(result.getErrorCount() > 0) {
			for(FieldError error : result.getFieldErrors()) {
				System.out.println(error.getField() + " : " + error.getDefaultMessage());
			}
			map.put("depts", departmentDao.getDepts());
			return "input";
		}
		employeeDao.save(employee);
		return "redirect:/emps";
	}

	@RequestMapping(value = "/emp", method = RequestMethod.GET)
	public String input(Map<String, Object> map) {
		map.put("depts", departmentDao.getDepts());
		map.put("employee", new Employee());
		return "input";
	}

	@RequestMapping("/emps")
	public String list(Map<String, Object> map) {
		map.put("employees", employeeDao.getEmps());
		return "list";
	}
	
	/**
	 * 由 @InitBinder 标识的方法  可以对 WebDataBinder 对象进行初始化
	 * 被 此注解修饰的方法  
	 * 1. 不能有返回值
	 * 2. 参数通常为 WebDataBinder
	 * @param binder
	 */
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		// WebDataBinder 对象可以设置某些属性  例如下面的属性指定映射数据到Bean的对象的时候  哪个属性不映射
//		binder.setDisallowedFields("employeeName");
//	}
}
