package com.sebi.service;

import com.sebi.adapters.SensorAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServerService {

    SensorAdapter sensorAdapter;

    public ServerService(){
        Connection connection = null;
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ExamModel", "postgres", "pass");
            sensorAdapter = new SensorAdapter(connection);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void add(String sensorName, int measurment, int timeMiliseconds){
        this.sensorAdapter.add(sensorName, measurment, timeMiliseconds);
    }
}
