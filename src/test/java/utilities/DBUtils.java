package utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DBUtils {
    static Connection conn = null;
    static Statement statement = null;
    private static ResultSet rSet;
    private static ResultSetMetaData rSetMetaData;

    public static ResultSet getResultSet(String sqlQuery) {
        try {
            conn = DriverManager.getConnection(ConfigReader.getPropertyValue("urlDB"),
                    ConfigReader.getPropertyValue("usernameDB"),
                    ConfigReader.getPropertyValue("passwordDB"));
            statement = conn.createStatement();
            rSet = statement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rSet;
    }

    public static ResultSetMetaData getrSetMetaData(String query){
        rSet = getResultSet(query);
        rSetMetaData = null;

        try {
            rSetMetaData = rSet.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rSetMetaData;
    }

    public static List<Map<String,String>> getListOfMapsFromSet(String query) {
        rSetMetaData = getrSetMetaData(query);
        List<Map<String, String>> listFromRset = new ArrayList<>();
        try {
            while (rSet.next()) {
                Map<String, String> map = new LinkedHashMap<>();
                for (int i = 1; i <= rSetMetaData.getColumnCount(); i++) {
                    String key = rSetMetaData.getColumnName(i);
                    String value = rSet.getString(key);
                    map.put(key, value);
                }
                System.out.println(map);
                listFromRset.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.closeResultSet(rSet);
            DBUtils.closeStatement(statement);
            DBUtils.closeConnection(conn);
        }
        return listFromRset;
    }

    public static  void closeResultSet(ResultSet rset){
        if(rset != null){
            try {
                rset.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void closeStatement(Statement statement){
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void closeConnection(Connection conn){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
