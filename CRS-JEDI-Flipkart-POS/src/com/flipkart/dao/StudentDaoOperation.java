/**
 * 
 */
package com.flipkart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

 

import com.flipkart.bean.Student;
import com.flipkart.client.CRSApplication;
import com.flipkart.constant.SQLQueriesConstants;
import com.flipkart.exception.StudentNotRegisteredException;
import com.flipkart.service.StudentOperation;
import com.flipkart.utils.DBUtils;

/**
 * 
 * @author AyushiDubey
 * Class to implement Student Dao Operations
 *
 */
public class StudentDaoOperation implements StudentDaoInterface {
	
	private static volatile StudentDaoOperation instance=null;
	
	/**
	 * Default Constructor
	 */
	private StudentDaoOperation()
	{
		
	}
	
	/**
	 * Method to make StudentDaoOperation Singleton
	 * @return
	 */
	public static StudentDaoOperation getInstance()
	{
		if(instance==null)
		{
			// This is a synchronized block, when multiple threads will access this instance
			synchronized(StudentDaoOperation.class){
				instance=new StudentDaoOperation();
			}
		}
		return instance;
	}

	/**
	 * Method to add student to database
	 * @param student: student object containing all the fields
	 * @return true if student is added, else false
	 * @throws StudentNotRegisteredException
	 */
	@Override
	public int addStudent(Student student) throws StudentNotRegisteredException{
		Connection connection=DBUtils.getConnection();
		int studentId= (int) (Math.random()*(999) +1);
		try
		{
			//open db connection
		//	System.out.println("Add user query called : ");
			PreparedStatement preparedStatement=connection.prepareStatement(SQLQueriesConstants.ADD_USER_QUERY);
			preparedStatement.setString(1, student.getUserId());
			preparedStatement.setString(2, student.getName());
			preparedStatement.setString(3, student.getPassword());
			preparedStatement.setString(4, student.getRole().toString());
			preparedStatement.setString(5, student.getGender().toString());
			preparedStatement.setString(6, student.getAddress());
			preparedStatement.setString(7, student.getCountry());
			int rowsAffected=preparedStatement.executeUpdate();
			if(rowsAffected==1)
			{
				//add the student record
				//"insert into student (userId,branchName,batch,isApproved) values (?,?,?,?)";
				PreparedStatement preparedStatementStudent;
				preparedStatementStudent=connection.prepareStatement(SQLQueriesConstants.ADD_STUDENT,Statement.RETURN_GENERATED_KEYS);
				preparedStatementStudent.setString(1,student.getUserId());
				preparedStatementStudent.setString(2, student.getBranchName());
				preparedStatementStudent.setInt(3, student.getBatch());

				preparedStatementStudent.setBoolean(4, false);
				preparedStatementStudent.setInt(5, studentId);
				preparedStatementStudent.setBoolean(6, false);
				System.out.println("Student has been added to database!");
				preparedStatementStudent.executeUpdate();
				//System.out.println("Student added to db : 2");
				ResultSet results=preparedStatementStudent.getGeneratedKeys();
				//System.out.println("Student added to db : 3");
				if(results.next()){
				//	System.out.println("Student added to db : if ");
					studentId=results.getInt(1);}
				System.out.println(" ");
			}
			
			
		}
		catch(Exception ex)
		{
			throw new StudentNotRegisteredException(student.getName());
		}
		/*finally
		{
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage()+"SQL error");
				e.printStackTrace();
			}
		}*/
		return studentId;
	}
	
	/**
	 * Method to retrieve Student Id from User Id
	 * @param userId
	 * @return Student Id
	 */
	@Override
	public int getStudentId(String userId) {
		Connection connection=DBUtils.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(SQLQueriesConstants.GET_STUDENT_ID);
			statement.setString(1, userId);
			ResultSet rs = statement.executeQuery();
			
			if(rs.next())
			{
				return rs.getInt("studentId");
			}
				
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		
		return Integer.parseInt(userId);
	}

	/**
	 * Method to retrieve Student Id from User Id
	 * @param userId
	 * @return Student Id
	 */
	public int getStudentId2(String userId) {
		Connection connection=DBUtils.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(SQLQueriesConstants.GET_STUDENT_ID_2);
			statement.setString(1, userId);
			ResultSet rs = statement.executeQuery();

			if(rs.next())
			{
				return rs.getInt("studentId");
			}

		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}

		return Integer.parseInt(userId);
	}

	/**
	 * Method to check if Student is approved
	 * @param studentId
	 * @return boolean indicating if student is approved
	 */
	@Override
	public boolean isApproved(int userId) {
		Connection connection=DBUtils.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(SQLQueriesConstants.IS_APPROVED);
		//	System.out.println(userId + " ~````~~~~~```~~~~ ");
			statement.setInt(1, userId);
			ResultSet rs = statement.executeQuery();
			
			if(rs.next())
			{
				return rs.getBoolean("isApproved");
			}
				
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		
		return false;
	}

}
