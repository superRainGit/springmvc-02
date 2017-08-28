package cn.zhang.springmvc.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.zhang.springmvc.beans.Department;
import cn.zhang.springmvc.beans.Employee;

@Repository
public class EmployeeDao {

	public static Map<Integer, Employee> employees = new HashMap<>();
	
	static {
		employees.put(1, new Employee(1, "E-AA", 0, "AA@163.com", new Department(1, "D-01")));
		employees.put(2, new Employee(2, "E-BB", 1, "BB@163.com", new Department(2, "D-02")));
		employees.put(3, new Employee(3, "E-CC", 1, "CC@163.com", new Department(3, "D-03")));
		employees.put(4, new Employee(4, "E-DD", 0, "DD@163.com", new Department(4, "D-04")));
		employees.put(5, new Employee(5, "E-EE", 1, "EE@163.com", new Department(5, "D-05")));
	}
	
	private static int nextIndex = 1006;
	
	public List<Employee> getEmps() {
		return new ArrayList<>(employees.values());
	}
	
	public Employee getEmp(Integer id) {
		return employees.get(id);
	}
	
	public void save(Employee e) {
		if(null == e.getId()) {
			e.setId(nextIndex++);
		}
		e.getDepartment().setDepartmentName(DepartmentDao.departments.get(e.getDepartment().getId()).getDepartmentName());
		employees.put(e.getId(), e);
	}
	
	public void delete(Integer id) {
		employees.remove(id);
	}
	
}
