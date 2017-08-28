package cn.zhang.springmvc.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.zhang.springmvc.beans.Department;

@Repository
public class DepartmentDao {

	public static Map<Integer, Department> departments = new HashMap<>();
	
	static {
		departments.put(1, new Department(1, "D-01"));
		departments.put(2, new Department(2, "D-02"));
		departments.put(3, new Department(3, "D-03"));
		departments.put(4, new Department(4, "D-04"));
		departments.put(5, new Department(5, "D-05"));
	}
	
	public List<Department> getDepts() {
		return new ArrayList<>(departments.values());
	}
	
	public Department getDept(Integer id) {
		return departments.get(id);
	}
}
