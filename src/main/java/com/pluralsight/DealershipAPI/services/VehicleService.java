package com.pluralsight.DealershipAPI.services;

import com.pluralsight.DealershipAPI.dataHandlers.abstractDAO.VehicleDAO;
import com.pluralsight.DealershipAPI.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class VehicleService {
    private final VehicleDAO vehicleDAO;

    @Autowired
    public VehicleService(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public List<Vehicle> getAllVehicles(int dealershipId) {
        return vehicleDAO.getAllVehicles(dealershipId);
    }

    public Vehicle filterByVin(int vin) {
        return vehicleDAO.filterByVin(vin);
    }

    public List<Vehicle> filterByPrice(int id, double min, double max) {
        return  vehicleDAO.filterByPrice(min, max);
    }

    public List<Vehicle> filterByMakeModel(int id, String make, String model) {
        return vehicleDAO.filterByMakeModel(make, model);
    }

    public List<Vehicle> filterByYear (int id, int minYear, int maxYear) {
        return vehicleDAO.filterByYear(minYear, maxYear);
    }

    public List<Vehicle> filterByColor(int id, String color) {
        return vehicleDAO.filterByColor(color);
    }

    public List<Vehicle> filterByMiles(int id, double minMiles, double maxMiles) {
        return vehicleDAO.filterByMileage(minMiles, maxMiles);
    }

    public List<Vehicle> filterByType(int id, String type) {
        return vehicleDAO.filterByType(type);
    }

    public List<Vehicle> getVehiclesByFilter(Map<String, Object> queryParams) {
        return vehicleDAO.filterVehicles(queryParams);
    }

    public void addVehicle(int dealershipId, Vehicle vehicle) {
        vehicleDAO.addVehicle(dealershipId, vehicle);
    }

    public int updateVehicle(Vehicle vehicle) {
        return vehicleDAO.updateVehicle(vehicle);
    }

    public int deleteVehicle(int vin) {
        return vehicleDAO.removeVehicle(vin);
    }

}
