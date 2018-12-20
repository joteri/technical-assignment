/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

import java.util.ArrayList;

/**
 *
 * @author jota
 */
public class City {
    
    private static ArrayList<City> cities = new ArrayList();
    
    private String name;
    private float temp;
    private int humidity;
    private int pressure;
    private float minTemp;
    private float maxTemp;

    public City(String name, float temp, int humidity, int pressure, float minTemp, float maxTemp) {
        this.name = name;
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }

    public String getName() {
        return name;
    }

    public float getTemp() {
        return temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    
    
    public static boolean addCity(City c){
        return City.cities.add(c);
    }
    
    public static ArrayList<City> getCities(){
        return City.cities;
    }

    @Override
    public String toString() {
        return "City{" + "NAME=" + name + ", TEMP=" + temp + ", HUMIDITY=" + humidity + ", PRESSURE=" + pressure + ", MIN_TEMP=" + minTemp + ", MAX_TEMP=" + maxTemp + '}';
    }
    
    
    
    
}
