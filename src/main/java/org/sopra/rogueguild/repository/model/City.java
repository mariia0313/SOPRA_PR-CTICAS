package org.sopra.rogueguild.repository.model;

import java.util.HashSet;

public class City {
    
    private String name;
    private HashSet <City> connectedCities;

    public City(String name){
        this.name = name;
        this.connectedCities = new HashSet<>();
    }

    public String getName(){
        return this.name;
    }

    public HashSet<City> getConnections(){
        return this.connectedCities;
    }

    public void addCityConnections(City city){
        if(city!=null && city != this){
            this.connectedCities.add(city);
            city.connectedCities.add(this);
        }
    }

    public static City createWorldMap(){
        City oakhaven = new City("Oakhaven");
        City sylvanwood = new City("Sylvanwood");
        City timberwall = new City("Timberwall");
        City ironstone = new City("Ironstone");
        City stormport = new City("Stormport");
        City mossdeep = new City("Mossdeep");
        City ravencrest = new City("Ravencrest");

        oakhaven.addCityConnections(sylvanwood);
        sylvanwood.addCityConnections(timberwall);
        sylvanwood.addCityConnections(mossdeep);
        ironstone.addCityConnections(stormport);
        stormport.addCityConnections(ravencrest);
        
        return sylvanwood;
    }


    @Override
    public String toString() {
        return name;
    }

}
