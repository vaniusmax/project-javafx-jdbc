package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {
	
	private DepartmentDao dao = DaoFactory.createDepDao();
	
	public List<Department> findAll(){
		
		return dao.findAll();
	}
	
	public void insertDepartment(Department dep) {
		dao.insert(dep);
	}

}
