package cn.zhang.springmvc.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import cn.zhang.springmvc.beans.Department;
import cn.zhang.springmvc.beans.Employee;
import cn.zhang.springmvc.dao.DepartmentDao;

@Component
public class SpringToEmployeeConverter implements Converter<String, Employee> {

	@Autowired
	private DepartmentDao departmentDao;
	
	@Override
	public Employee convert(String source) {
		// 自定义的转换器需要自己去约束转换规则
		if(null != source) {
			String[] vals = source.split("-");
			if(null != vals && vals.length == 4) {
				Department department = departmentDao.getDept(Integer.valueOf(vals[3]));
				return new Employee(null, vals[0], Integer.valueOf(vals[1]), vals[2], department);
			}
		}
		return null;
	}

}
