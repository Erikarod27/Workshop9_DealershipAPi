package com.pluralsight.DealershipAPI.dataHandlers.abstractDAO;

import com.pluralsight.DealershipAPI.models.Vehicle;

import java.sql.ResultSet;
import java.util.List;

public interface VehicleDAO {

    List<Vehicle> getAllVehicles(int dealershipId);

    <T> List<Vehicle> getVehicles(String query, List<T> arguments);

    Vehicle filterByVin(int vin);

    List<Vehicle> filterByPrice(double minimumPrice, double maximumPrice);

    List<Vehicle> filterByMakeModel(String make, String model);

    List<Vehicle> filterByYear(int oldestYear, int newestYear);

    List<Vehicle> filterByColor(String color);

    List<Vehicle> filterByMileage(int highestMileage);

    List<Vehicle> filterByType(String type);

    void addVehicle(Vehicle vehicle);

    void updateVehicleAsSold(Vehicle vehicle);

    void removeVehicle(Vehicle vehicle);

}
