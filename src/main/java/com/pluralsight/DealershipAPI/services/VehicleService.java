package com.pluralsight.DealershipAPI.services;

import com.pluralsight.DealershipAPI.dataHandlers.abstractDAO.VehicleDAO;
import com.pluralsight.DealershipAPI.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    public List<Vehicle> getVehiclesByFilter(Map<String, Object> queryParams) {
        return vehicleDAO.filterVehicles(queryParams);
    }
}
