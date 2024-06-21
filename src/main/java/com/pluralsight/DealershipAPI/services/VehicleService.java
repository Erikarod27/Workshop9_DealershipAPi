package com.pluralsight.DealershipAPI.services;

import com.pluralsight.DealershipAPI.dataHandlers.abstractDAO.VehicleDAO;
import com.pluralsight.DealershipAPI.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class VehicleService {
    private VehicleDAO vehicleDAO;

    @Autowired
    public VehicleService(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public List<Vehicle> getAllVehicles(int dealershipId) {
        return vehicleDAO.getAllVehicles(dealershipId);
    }

    public List<Vehicle> filterByVin(int vin) {
        return Collections.singletonList(vehicleDAO.filterByVin(vin));
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
}
