package cn.zhang.springmvc.beans;

import java.util.Date;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

public class Employee {

	private Integer id;
	@NotEmpty
	private String employeeName;
	private Integer gender;
	@Email
	private String email;
	private Department department;
	/**
	 * 可以使用 @DateTimeFormat 进行字符串到时间格式的映射
	 * 此注解能起作用的基本要求是  在 spring 的配置文件中写入 <mvc:annotation-driven />配置
	 * 因为配置了 <mvc:annotaion-driven /> 配置会向 WebDataBinder 的conversionService 
	 * 属性赋值一个 DefaultFormattingConversionService  此Bean会进行数据的转换和格式化
	 */
	@Past
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date birth;
	/**
	 * 可以使用 @NumberFormat 进行字符串到时间格式的映射
	 * 使用的基本要求和使用方式同 @dDateTimeFormat
	 */
	@NumberFormat(pattern="#,###.#")
	private float salary;
	
	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Employee(Integer id, String employeeName, Integer gender, String email, Department department) {
		super();
		this.id = id;
		this.employeeName = employeeName;
		this.gender = gender;
		this.email = email;
		this.department = department;
	}

	public Employee() {
		super();
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", employeeName=" + employeeName + ", gender=" + gender + ", email=" + email
				+ ", department=" + department + ", birth=" + birth + ", salary=" + salary + "]";
	}

}
