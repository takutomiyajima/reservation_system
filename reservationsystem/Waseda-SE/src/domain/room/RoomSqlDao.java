package domain.room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.DateUtil;

public class RoomSqlDao implements RoomDao {

	private static final String ID = "sa";
	private static final String PASSWORD = "";
	private static final String DRIVER_NAME = "org.hsqldb.jdbcDriver";
	private static final String URL = "jdbc:hsqldb:hsql://localhost/mydb";
	private static final String TABLE_NAME = "ROOM";
	private static final String COL_TYPE = "type";

	public List getRooms() throws RoomException {
		StringBuffer sql = new StringBuffer();
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		List roomList = new ArrayList();
		try {
			connection = getConnection();
			statement = connection.createStatement();
			sql.append("SELECT roomnumber FROM ").append(TABLE_NAME).append(";");
			resultSet = statement.executeQuery(sql.toString());
			while (resultSet.next()) {
				roomList.add(resultSet.getString("roomnumber"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			RoomException exception = new RoomException(RoomException.CODE_DB_EXEC_QUERY_ERROR, e);
			exception.getDetailMessages().add("getRooms()");
			throw exception;
		} finally {
			close(resultSet, statement, connection);
		}
		return roomList;
	}

	public Room getRoom(String roomNumber) throws RoomException {
		StringBuffer sql = new StringBuffer();
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		Room room = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			sql.append("SELECT roomnumber, stayingdate, ").append(COL_TYPE)
				.append(" FROM ").append(TABLE_NAME)
				.append(" WHERE ROOMNUMBER='").append(roomNumber).append("';");
			resultSet = statement.executeQuery(sql.toString());
			if (resultSet.next()) {
				room = new Room();
				room.setRoomNumber(roomNumber);
				room.setStayingDate(DateUtil.convertToDate(resultSet.getString("stayingDate")));
				room.setType(resultSet.getString(COL_TYPE));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			RoomException exception = new RoomException(RoomException.CODE_DB_EXEC_QUERY_ERROR, e);
			exception.getDetailMessages().add("getRoom()");
			throw exception;
		} finally {
			close(resultSet, statement, connection);
		}
		return room;
	}

	public List getEmptyRooms() throws RoomException {
		return getEmptyRooms(null);
	}

	public List getEmptyRooms(String type) throws RoomException {
		StringBuffer sql = new StringBuffer();
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		List emptyRoomList = new ArrayList();
		try {
			connection = getConnection();
			statement = connection.createStatement();
			sql.append("SELECT roomnumber, ").append(COL_TYPE)
				.append(" FROM ").append(TABLE_NAME)
				.append(" WHERE stayingdate=''");
			if (type != null) {
				sql.append(" AND ").append(COL_TYPE).append("='").append(type).append("'");
			}
			sql.append(";");
			resultSet = statement.executeQuery(sql.toString());
			while (resultSet.next()) {
				Room room = new Room();
				room.setRoomNumber(resultSet.getString("roomnumber"));
				room.setType(resultSet.getString(COL_TYPE));
				emptyRoomList.add(room);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			RoomException exception = new RoomException(RoomException.CODE_DB_EXEC_QUERY_ERROR, e);
			exception.getDetailMessages().add("getEmptyRooms()");
			throw exception;
		} finally {
			close(resultSet, statement, connection);
		}
		return emptyRoomList;
	}

	public void updateRoom(Room room) throws RoomException {
		StringBuffer sql = new StringBuffer();
		Statement statement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			sql.append("UPDATE ").append(TABLE_NAME)
				.append(" SET stayingdate =");
			if (room.getStayingDate() == null) {
				sql.append("''");
			} else {
				sql.append("'").append(DateUtil.convertToString(room.getStayingDate())).append("'");
			}
			sql.append(" WHERE roomnumber='").append(room.getRoomNumber()).append("';");
			resultSet = statement.executeQuery(sql.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			RoomException exception = new RoomException(RoomException.CODE_DB_EXEC_QUERY_ERROR, e);
			exception.getDetailMessages().add("updateRoom()");
			throw exception;
		} finally {
			close(resultSet, statement, connection);
		}
	}

	public void createRoom(Room room) throws RoomException {
		if (getRoom(room.getRoomNumber()) != null) {
			System.out.println("Room " + room.getRoomNumber() + " already exists. Skipping INSERT.");
			return;
		}
		StringBuffer sql = new StringBuffer();
		Statement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			sql.append("INSERT INTO ").append(TABLE_NAME)
				.append(" (roomnumber, stayingdate) VALUES ('")
				.append(room.getRoomNumber()).append("', '');");
			statement.executeUpdate(sql.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			RoomException exception = new RoomException(RoomException.CODE_DB_EXEC_QUERY_ERROR, e);
			exception.getDetailMessages().add("createRoom()");
			throw exception;
		} finally {
			close(null, statement, connection);
		}
	}

	private Connection getConnection() throws RoomException {
		Connection connection = null;
		try {
			Class.forName(DRIVER_NAME);
			connection = DriverManager.getConnection(URL, ID, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RoomException(RoomException.CODE_DB_CONNECT_ERROR, e);
		}
		return connection;
	}

	private void close(ResultSet resultSet, Statement statement, Connection connection) throws RoomException {
		try {
			if (resultSet != null) resultSet.close();
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RoomException(RoomException.CODE_DB_CLOSE_ERROR, e);
		}
	}
}
