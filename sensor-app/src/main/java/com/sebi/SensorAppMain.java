package com.sebi;

import com.sebi.entities.Sensor;
import com.sebi.sensorExceptions.SensorIntervalException;
import com.sebi.sensorExceptions.SensorExceptionID;
import com.sebi.TcpClient.TcpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SensorAppMain {
    private static ArrayList<Sensor> sensorsList = new ArrayList<Sensor>();

    public static void println(Object msg){
        System.out.println(msg.toString());
    }
    public static void println(String msg){
        System.out.println(msg);
    }
    public static void println(int msg){
        System.out.println(Integer.toString(msg));
    }

    public static void main(String[] args){
        Scanner consoleReader = new Scanner(System.in);
        while(true){
            String line = consoleReader.nextLine();
            if(line.equals("exit") || line.equals("quit")){
                for(Sensor s: sensorsList){
                    s.turnOff();
                }
                break;
            }
            String[] arguments = line.split(" ");
            if(arguments.length != 4){
                println("Wrong syntax! Expected <sensroName> <sensorId> <lowerBound> <upperBund>");
                continue;
            }
            try {
                String sensorName = arguments[0];
                int sensorID = Integer.parseInt(arguments[1]);
                int lowerBound = Integer.parseInt(arguments[2]);
                int upperBound = Integer.parseInt(arguments[3]);
                Sensor newSensor = new Sensor(sensorName, sensorID, lowerBound, upperBound);
                sensorsList.add(newSensor);
                newSensor.turnOn();
            }
            catch (SensorIntervalException | SensorExceptionID e){
                println(e.getMessage());
            }
            catch(NumberFormatException e){
                println("exception: every argument except the sensorName should be an integer");
            }
            catch(Exception e){
                println("Application stopped forcefully!");
                for(Sensor s: sensorsList){
                    s.turnOff();
                }
            }
        }
    }
}
