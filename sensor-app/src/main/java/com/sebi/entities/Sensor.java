package com.sebi.entities;

import com.sebi.TcpClient.TcpClient;
import com.sebi.sensorExceptions.SensorExceptionID;
import com.sebi.sensorExceptions.SensorIntervalException;

import java.io.IOException;
import java.util.Random;

public class Sensor implements Runnable{
    private TcpClient clientSocket;

    Thread sensorThread;
    int maximumSleepTime = 3000;

    String sensorName;
    int sensorID;
    int lowerBound, upperBound;
    int intervalLength;
    boolean isOn; //this boolean tells if the sensro should be running (isOn = true), or if it is stopped(isOn = false)
    Random rand;

    public Sensor(String sensorName, int sensorID, int lowerBound, int upperBound) throws SensorExceptionID, SensorIntervalException{
        if(sensorID <= 1023){
            throw new SensorExceptionID("exception: ID should be greater than 1023!");
        }
        this.sensorName = sensorName;
        this.sensorID = sensorID;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;

        if(lowerBound > upperBound){
            throw new SensorIntervalException("exception: the upperBound should be greater or equal with the lowerBound!");
        }
        this.intervalLength = this.upperBound - this.lowerBound;

        this.rand = new Random();
    }

    public String getSensorName() {
        return this.sensorName;
    }

    public int getSensorID() {
        return sensorID;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public void setIsOn(boolean isOnValue){
        this.isOn = isOnValue;
    }
    /*
        def: returns a number between lowerBound and upperBound, by generating a random number between 0 and
        intervalLength = upperBound - lowerBound. Then it adds this number to the lowerBound; This will give a result
        in the interval [lowerBound, upperBound).
     */
    public int measure(){
        return (rand.nextInt(this.intervalLength) + this.lowerBound);
    }

    @Override
    public void run() {
        try {
            this.clientSocket = new TcpClient();
            this.clientSocket.startClient();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            this.isOn = false;
        }

        while(this.isOn){
            try{
                String message = this.sensorName + " " + Integer.toString(this.sensorID) + " " + Integer.toString(this.measure());
                clientSocket.sendMessage(message);

                Thread.sleep(rand.nextInt(this.maximumSleepTime));
            }
            catch(InterruptedException e){
                //System.out.println("exception: Sensor: " + this.sensorName + " with ID = " + Integer.toString(this.sensorID) + " interrupted!");
                this.isOn = false;
            }
        }
        System.out.println("Sensor: " + this.sensorName + " with ID = " + Integer.toString(this.sensorID) + " stopped.");
    }

    public void turnOn(){
        this.isOn = true;
        this.start();
    }

    public void turnOff(){
        this.isOn = false;
        sensorThread.interrupt();
    }

    public void start(){
        this.isOn = true;
        sensorThread = new Thread(this::run, this.sensorName);
        sensorThread.start();
    }
}
