package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import db.DBException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		String sql = "INSERT INTO department " + "(Name) " + "VALUES(?)";
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					obj.setId(rs.getInt(1));
				}
			}else {
				throw new DBException("Unexpected error: ");
			}

		} catch (SQLException e) {
			throw new DBException("Error: "+ e.getMessage());
		}

	}

	@Override
	public void update(Department obj) {
		String sql = "UPDATE department " + 
				"SET Name= ? " + 
				"WHERE Id= ?";
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			st.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new DBException("Error: "+ e.getMessage());
		}

	}

	@Override
	public void deleteById(Integer id) {
		String sql = "DELETE FROM department "
				+"WHERE Id= ?";

		PreparedStatement st = null;

		try {
			st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, id);
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DBException("Error: "+ e.getMessage());
		}


	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from department where id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			Department dep = new Department();

			if (rs.next()) {

				dep.setId(rs.getInt("Id"));
				dep.setName(rs.getString("Name"));

				return dep;
			}

			return null;

		} catch (SQLException e) {
			throw new DBException("Error: " + e.getMessage());
		}

	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from department");
			rs = st.executeQuery();
			List<Department> list = new ArrayList<>();

			while (rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("Id"));
				dep.setName(rs.getString("Name"));

				list.add(dep);
			}

			return list;

		} catch (SQLException e) {
			throw new DBException("Error: " + e.getMessage());
		}

	}

}
