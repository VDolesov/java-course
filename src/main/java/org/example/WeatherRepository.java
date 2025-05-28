package org.example;

import java.sql.*;
import java.util.*;

public class WeatherRepository {
    private static final String URL = "jdbc:postgresql://localhost:5432/weather";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1944";

    private Connection connect() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC Driver не найден. Убедитесь, что он добавлен в classpath.", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void saveForecast(String cityName, List<Integer> temperatures) {
        String sql = "INSERT INTO weather_forecasts (city_name, temperatures) " +
                "VALUES (?, ?) " +
                "ON CONFLICT (city_name) DO UPDATE SET temperatures = ?, updated_at = CURRENT_TIMESTAMP";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Array sqlArray = conn.createArrayOf("INTEGER", temperatures.toArray());
            stmt.setString(1, cityName);
            stmt.setArray(2, sqlArray);
            stmt.setArray(3, sqlArray);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> getForecast(String cityName) {
        String selectSql = "SELECT temperatures FROM weather_forecasts WHERE city_name = ?";
        String updateSql = "UPDATE weather_forecasts SET last_accessed_at = CURRENT_TIMESTAMP, request_count = request_count + 1 WHERE city_name = ?";
        List<Integer> temperatures = new ArrayList<>();

        try (Connection conn = connect();
             PreparedStatement selectStmt = conn.prepareStatement(selectSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            selectStmt.setString(1, cityName);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                Array sqlArray = rs.getArray("temperatures");
                Integer[] tempArray = (Integer[]) sqlArray.getArray();
                temperatures = Arrays.asList(tempArray);

                updateStmt.setString(1, cityName);
                updateStmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return temperatures;
    }

    public boolean hasForecast(String cityName) {
        String sql = "SELECT 1 FROM weather_forecasts WHERE city_name = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cityName);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
