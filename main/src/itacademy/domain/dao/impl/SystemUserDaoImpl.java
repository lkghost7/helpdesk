package itacademy.domain.dao.impl;

import itacademy.connection.ConnectionManager;
import itacademy.domain.dao.interfaces.SystemUserDao;
import itacademy.domain.entity.Branch;
import itacademy.domain.entity.Subdivision;
import itacademy.domain.entity.SystemUser;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SystemUserDaoImpl implements SystemUserDao {
  private static final Object LOCK = new Object();
  private static SystemUserDaoImpl INSTANCE = null;
  private static String SYSTEM_USERS = "system_users";
  private static String BRANCHES = "branches";
  private static String SUBDIVISIONS = "subdivisions";

  public static SystemUserDaoImpl getInstance() {
    if (INSTANCE == null) {
      synchronized (LOCK) {
        if (INSTANCE == null) {
          INSTANCE = new SystemUserDaoImpl();
        }
      }
    }
    return INSTANCE;
  }

  private SystemUser createSystemUserFromResultSet(ResultSet resultSet) throws SQLException {
    return new SystemUser(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("family"),
            resultSet.getString("e_mail"),
            resultSet.getString("password"),
            new Branch(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("adress")),
            new Subdivision(
                    resultSet.getLong("id"),
                    resultSet.getString("name")));
  }

  @Override
  public Long save(SystemUser entity) {
    String sql = "insert into  system_users (name, family, e_mail, password, branch_id, subdivision_id)\n" +
            "values (?, ?, ?, ?, ?, ?);";
    return null;
  }

  @Override
  public void delete(Long id) {

    String sql = "delete from system_users where id = ? ;";
  }

  @Override
  public List<SystemUser> findAll() {
    List<SystemUser> systemUsersList = new ArrayList<>();
    try (Connection connection = ConnectionManager.getConnection()) {
      String sql = "select\n" +
              "  su.id,\n" +
              "  su.name,\n" +
              "  su.family,\n" +
              "  su.e_mail,\n" +
              "  su.password,\n" +
              "  b.id,\n" +
              "  b.name,\n" +
              "  b.adress,\n" +
              "  s.id,\n" +
              "  s.name\n" +
              "from system_users su, branches b, subdivisions s\n" +
              "where su.branch_id = b.id\n" +
              "      and su.subdivision_id = s.id;";
      try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
          while (resultSet.next()) {
            systemUsersList.add(createSystemUserFromResultSet(resultSet));
          }
        }
      }
    } catch (SQLException e1) {
      e1.printStackTrace();
    }
    return systemUsersList;
  }


  @Override
  public Optional<SystemUser> findById(Long id) {
    String sql = "select\n" +
            "  su.id,\n" +
            "  su.name,\n" +
            "  su.family,\n" +
            "  su.e_mail,\n" +
            "  su.password,\n" +
            "  b.id,\n" +
            "  b.name,\n" +
            "  b.adress,\n" +
            "  s.id,\n" +
            "  s.name\n" +
            "from system_users su, branches b, subdivisions s\n" +
            "where su.branch_id = b.id\n" +
            "      and su.subdivision_id = s.id" +
            "      and su.id = ? ;";
    return Optional.empty();
  }


  public List<SystemUser> findAllPrc() {
    List<SystemUser> systemUsersList = new ArrayList<>();
    String sql = "{ ? = call system_user_findbyid(?)}";
    try (Connection connection = ConnectionManager.getConnection();
         CallableStatement proc = connection.prepareCall(sql)) {
      connection.setAutoCommit(false);
      proc.registerOutParameter(1, Types.OTHER);
      proc.setInt(2, 23);
      proc.execute();
      ResultSet resultSet = (ResultSet) proc.getObject(1);
      while (resultSet.next()) {
        systemUsersList.add(createSystemUserFromResultSet(resultSet));
      }
      resultSet.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return systemUsersList;
  }
}
