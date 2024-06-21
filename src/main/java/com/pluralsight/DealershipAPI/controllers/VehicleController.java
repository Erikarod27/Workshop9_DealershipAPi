package com.pluralsight.DealershipAPI.controllers;

import com.pluralsight.DealershipAPI.models.Vehicle;
import com.pluralsight.DealershipAPI.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VehicleController {
    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    //GET all vehicles method
    @RequestMapping(path = "/{dealership_id}/vehicles", method = RequestMethod.GET)
    public List<Vehicle> vehicles(@PathVariable int dealership_id) {
        return vehicleService.getAllVehicles(dealership_id);
    }

    //GET all vehicles by min and max price method
    @RequestMapping(path="{dealership_id}/vehicles/filter", method=RequestMethod.GET)
    public List<Vehicle> vehicles(@PathVariable int dealership_id, @RequestParam double min_price, @RequestParam double max_price) {
        return vehicleService.filterByPrice(dealership_id, min_price, max_price);
    }

    //GET all vehicles by make and model method
    @RequestMapping(path="{dealership_id}/vehicles/filter", method=RequestMethod.GET)
    public List<Vehicle> vehicles(@PathVariable int dealership_id, @RequestParam String make, @RequestParam String model) {
        return vehicleService.filterByMakeModel(dealership_id, make, model);
    }

    //GET all vehicles by both min and max year method
    @RequestMapping(path="{dealership_id}/vehicles/filter", method=RequestMethod.GET)
    public List<Vehicle> vehicles(@PathVariable int dealership_id, @RequestParam int min_year, @RequestParam int max_year) {
        return vehicleService.filterByYear(dealership_id, min_year, max_year);
    }

    //GET all vehicles by color method
    @RequestMapping(path="{dealership_id}/vehicles/filter", method=RequestMethod.GET)
    public List<Vehicle> vehicles(@PathVariable int dealership_id, @RequestParam String color) {
        return vehicleService.filterByColor(dealership_id, color);
    }

    //GET all vehicles by both min and max miles method
    @RequestMapping(path="{dealership_id}/vehicles/filter", method=RequestMethod.GET)
    public List<Vehicle> vehicles(@PathVariable int dealership_id, @RequestParam int min_miles, @RequestParam int max_miles) {
        return vehicleService.filterByMiles(dealership_id, min_miles, max_miles);
    }

    //GET all vehicles by type method
    @RequestMapping(path="{dealership_id}/vehicles/filter", method=RequestMethod.GET)
    public List<Vehicle> vehicles(@PathVariable int dealership_id, @RequestParam String type) {
        return vehicleService.type(dealership_id, type);
    }

    //POST add vehicle method
    @RequestMapping(path="{dealership_id}/vehicles/add", method=RequestMethod.POST)
    public int addVehicle(@PathVariable int dealership_id, @RequestParam Vehicle vehicle) {
        return vehicleService.addVehicle(dealership_id, vehicle);
    }

    //PUT update vehicle method
    @RequestMapping(path="{dealership_id}/vehicles/update", method=RequestMethod.PUT)
    public int updateVehicle(@PathVariable int dealership_id, @RequestBody Vehicle vehicle) {
        return vehicleService.updateVehicle(dealership_id, vehicle);
    }

    //DELETE delete vehicle method
    @RequestMapping(path="{dealership_id}/vehicles/delete", method=RequestMethod.DELETE )
    public int deleteVehicle(@PathVariable int dealership_id, @RequestParam String vin) {
        return vehicleService.deleteVehicle(dealership_id, vin);
    }
}
