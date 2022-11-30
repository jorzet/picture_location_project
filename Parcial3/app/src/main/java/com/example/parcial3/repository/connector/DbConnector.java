package com.example.parcial3.repository.connector;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnector {

    private final static String TAG = "BDConnection";
    private final static String driver = "com.mysql.jdbc.Driver";
    private final static String ip = "192.168.100.66";
    private final static String port = "3306";
    private final static String dataBase = "localizacion";
    private final static String user = "jorge";
    private final static String password = "jorge";

    public Connection conexionMySQL;

    public Connection startConnection() {
        //Construímos la url para establecer la conexión
        try {
            Class.forName(driver).newInstance();
            String url = "jdbc:mysql://"+ip+":"+port+"/"+dataBase+"?user="+user+"&password="+password+"&?useSSL=false&useJDBCComplaintTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&jdbcCompliantTruncation=false";
            conexionMySQL = DriverManager.getConnection(url);
            //Comprobamos que la conexión se ha establecido
            if (!conexionMySQL.isClosed()) {
                Log.d(TAG, "Conexion establecida:");
            }
        } catch (Exception e) {
            Log.d(TAG, "Error al comprobar las credenciales:" + e.getMessage());
            e.printStackTrace();
            return null;
        }

        return conexionMySQL;
    }

    public void closeConnection() {
        try {
            conexionMySQL.close();
            Log.d(TAG, "Conexion cerrada:");
        } catch (Exception e) {
            Log.d(TAG, "Error al cerrar conexion:" + e.getMessage());
        }
    }
}
