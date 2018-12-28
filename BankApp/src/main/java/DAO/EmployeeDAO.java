package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.DBConnection;
import database.DBUtil;
import employees.Employee;
import utility.Helpers;

public class EmployeeDAO implements EmployeeDAOInterface{
	private Connection connection;
	private PreparedStatement ps;
	private DBUtil util;
	private Helpers helper;
	
	public EmployeeDAO() {
		util = new DBUtil();
		helper = new Helpers();
	}
	
	@Override
	public boolean addEmployee(Employee employee) {
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO employees VALUES(?,?,?,?,?)";
			ps = connection.prepareStatement(sql);
			ps.setInt(1,  employee.getEmployeeID());
			ps.setString(2, employee.getUsername());
			ps.setString(3, employee.getPassword());
			ps.setBoolean(4, employee.getIsAdmin());
			ps.setInt(5,  employee.getAdminID());
		
			if (ps.executeUpdate() != 0) {
				ps.close();
				return true;
			} else {
				ps.close();
				return false;
			} 
		} catch (SQLException e) {
			//System.out.println("SQLException in EmployeeDAO#addEmployee");
			return false;
		}
	}
	@Override
	public boolean addSampleEmployee(Employee employee) {
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO sample_employees VALUES(?,?,?,?,?)";
			ps = connection.prepareStatement(sql);
			ps.setInt(1,  employee.getEmployeeID());
			ps.setString(2, employee.getUsername());
			ps.setString(3, employee.getPassword());
			ps.setBoolean(4, employee.getIsAdmin());
			ps.setInt(5, employee.getAdminID());
		
			if (ps.executeUpdate() != 0) {
				ps.close();
				return true;
			} else {
				ps.close();
				return false;
			} 
		} catch (SQLException e) {
			//System.out.println("SQLException in EmployeeDAO#addSampleEmployee");
			return false;
		}
	}
	@Override
	public int getNumEmployees() {
		Connection connection;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT COUNT(*) AS count FROM employees;";
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			rs.next();
			int count = rs.getInt("count");
			rs.close();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public int getNumSampleEmployees() {
		Connection connection;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT COUNT(*) AS count FROM sample_employees;";
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			rs.next();
			int count = rs.getInt("count");
			rs.close();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public ArrayList<String> getActualRecordsAsStrings() {
		Connection connection;
		ArrayList<String> records = new ArrayList<String>();
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM employees ORDER BY employee_id;";
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			String record = null;
			while (rs.next()) {
				record = String.format("%-10d%-20s%-20s%-10s%-10d",
						rs.getInt(1), rs.getString(2), rs.getString(3), 
						helper.boolToString(rs.getBoolean(4)), rs.getInt(5));
				records.add(record);
				record = null;
			}
			return records;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return records;
	}
	public ArrayList<String> getSampleRecordsAsStrings() {
		Connection connection;
		ArrayList<String> records = new ArrayList<String>();
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM sample_employees ORDER BY employee_id;";
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			String record = null;
			while (rs.next()) {
				record = String.format("%-10d%-20s%-20s%-10s%-10d",
						rs.getInt(1), rs.getString(2), rs.getString(3), 
						helper.boolToString(rs.getBoolean(4)), rs.getInt(5));
				records.add(record);
				record = null;
			}
			return records;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return records;
	}
	
}
