package com.example.parcial3.repository;

import com.example.parcial3.model.Event;
import com.example.parcial3.repository.connector.DbConnector;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {
    private DbConnector bdConnector;

    public List<Event> getEvents() {
        bdConnector = new DbConnector();
        CallableStatement sp= null;
        List<Event> events = new ArrayList<>();
        String result = "";
        int f = 0;

        try {
            if (bdConnector.startConnection() != null) {
                String SQL = "{call getEvents (?)}";
                sp = bdConnector.conexionMySQL.prepareCall(SQL);
                sp.setEscapeProcessing(true);
                sp.setQueryTimeout(20);
                sp.registerOutParameter(1, Types.VARCHAR);
                ResultSet rs = sp.executeQuery();
                result = sp.getString(1);

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
                            Event event = new Event(
                                    rs.getInt(1),
                                    rs.getBytes(2),
                                    rs.getString(3),
                                    rs.getString(4),
                                    rs.getString(5),
                                    rs.getString(6),
                                    rs.getInt(7)
                            );
                            events.add(event);
                        }
                        return events;
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
        return events;
    }

    public String saveEvent(Event event) {
        bdConnector = new DbConnector();
        CallableStatement sp = null;
        String result = "";

        try {
            if (bdConnector.startConnection() != null) {
                String SQL = "{call insertEvent (?,?,?,?,?,?,?)}";
                sp = bdConnector.conexionMySQL.prepareCall(SQL);
                sp.setEscapeProcessing(true);
                sp.setQueryTimeout(20);
                sp.setBytes(1, event.getImg());
                sp.setString(2, event.getDate());
                sp.setString(3,event.getDescription());
                sp.setString(4, event.getLatitude());
                sp.setString(5, event.getLongitude());
                sp.setInt(6, event.getUserId());
                sp.registerOutParameter(7, Types.VARCHAR);
                sp.execute();
                result = sp.getString(7);
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
        return result;
    }
}
