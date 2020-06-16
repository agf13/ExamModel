package com.sebi.adapters;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class SensorAdapter {
    Connection connection;

    public SensorAdapter(Connection connection){
        this.connection = connection;
    }

    public void add(String sensorName, int measurment, int timeMiliseconds){
        try {
            String sqlCommand = "INSERT INTO sensor(\"sensor-name\",\"measurment\",\"time\") VALUES (?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sqlCommand);

            stmt.setString(1, sensorName);
            stmt.setInt(2, measurment);
            stmt.setInt(3, timeMiliseconds);

            stmt.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }
}
