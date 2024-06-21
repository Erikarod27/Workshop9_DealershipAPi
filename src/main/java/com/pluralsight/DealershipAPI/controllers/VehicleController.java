package com.pluralsight.DealershipAPI.controllers;

import com.pluralsight.DealershipAPI.models.Vehicle;
import com.pluralsight.DealershipAPI.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class VehicleController {
    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @RequestMapping(path = "/{dealership_id}/vehicles", method = RequestMethod.GET)
    public List<Vehicle> filterVehicles(@PathVariable int dealership_id,
                                     @RequestParam(required = false) String make,
                                     @RequestParam(required = false) String model,
                                     @RequestParam(required = false) Integer year,
                                     @RequestParam(required = false) Double minOdometer,
                                     @RequestParam(required = false) Double maxOdometer,
                                     @RequestParam(required = false) String color,
                                     @RequestParam(required = false) String vehicleType,
                                     @RequestParam(required = false) Double minPrice,
                                     @RequestParam(required = false) Double maxPrice) {

        Map<String, Object> queryParams = new HashMap<>();

        queryParams.put("dealership_id", dealership_id);
        if (make != null) queryParams.put("make", make);
        if (model != null) queryParams.put("model", model);
        if(year != null) queryParams.put("year", year);
        if (minOdometer != null) queryParams.put("minOdometer", minOdometer);
        if (maxOdometer != null) queryParams.put("maxOdometer", maxOdometer);
        if (color != null) queryParams.put("color", color);
        if (vehicleType != null) queryParams.put("vehicleType", vehicleType);
        if (minPrice != null) queryParams.put("minPrice", minPrice);
        if (maxPrice != null) queryParams.put("maxPrice", maxPrice);

        return vehicleService.getVehiclesByFilter(queryParams);
    }

    //POST add vehicle method
    @RequestMapping(path="{dealership_id}/vehicles/add", method=RequestMethod.POST)
    public void addVehicle(@PathVariable int dealership_id, @RequestBody Vehicle vehicle) {
        vehicleService.addVehicle(dealership_id, vehicle);
    }

    //PUT update vehicle method
    @RequestMapping(path="{dealership_id}/vehicles/update", method=RequestMethod.PUT)
    public int updateVehicle(@PathVariable int dealership_id, @RequestBody Vehicle vehicle) {
        return vehicleService.updateVehicle(vehicle);
    }

    //DELETE delete vehicle method
    @RequestMapping(path="{dealership_id}/vehicles/delete", method=RequestMethod.DELETE )
    public int deleteVehicle(@PathVariable int dealership_id, @RequestParam int vin) {
        return vehicleService.deleteVehicle(vin);
    }
}
