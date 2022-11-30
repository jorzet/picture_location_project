package com.example.parcial3.repository;

import com.example.parcial3.model.User;
import com.example.parcial3.repository.connector.DbConnector;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class UserRepository {
    private DbConnector bdConnector;

    public User getUser(String email, String password) {
        bdConnector = new DbConnector();
        CallableStatement sp= null;
        User user = null;
        String result = "";
        int f = 0;

        try {
            if (bdConnector.startConnection() != null) {
                String SQL = "{call getUser (?,?,?)}";
                sp = bdConnector.conexionMySQL.prepareCall(SQL);
                sp.setEscapeProcessing(true);
                sp.setQueryTimeout(20);
                sp.setString(1, email);
                sp.setString(2, password);
                sp.registerOutParameter(3, Types.VARCHAR);
                ResultSet rs = sp.executeQuery();
                result = sp.getString(3);

                if (result.equals("OK")) {
                    while (rs.next()) {
                        f += 1;
                    }
                    if (f == 0) {
                        System.out.println("Es nulo$rs");
                    } else {
                        rs.beforeFirst();
                        System.out.println("No es nulo$rs");
                        while (rs.next()) {
                            user = new User(
                                    rs.getInt(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4),
                                    rs.getString(5),
                                    rs.getString(6)
                            );
                        }
                        return user;
                    }
                } else return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                bdConnector.closeConnection();
                sp.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public User saveUser(User user) {
        bdConnector = new DbConnector();
        CallableStatement sp = null;
        String result = "";

        try {
            if (bdConnector.startConnection() != null) {
                String SQL = "{call insertUser (?,?,?,?,?,?)}";
                sp = bdConnector.conexionMySQL.prepareCall(SQL);
                sp.setEscapeProcessing(true);
                sp.setQueryTimeout(20);
                sp.setString(1, user.getName());
                sp.setString(2, user.getLastName());
                sp.setString(3, user.getEmail());
                sp.setString(4, user.getPhone());
                sp.setString(5, user.getPassword());
                sp.registerOutParameter(6, Types.VARCHAR);
                sp.execute();
                result = sp.getString(6);
                if (result.equals("OK")) {
                    return user;
                } else return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                bdConnector.closeConnection();
                sp.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }
}
