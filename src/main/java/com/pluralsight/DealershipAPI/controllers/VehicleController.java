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

    //GET all vehicles by min price method
    @RequestMapping(path="{dealership_id}/vehicles/{min_price}", method=RequestMethod.GET)
    public List<Vehicle> vehicles(@PathVariable int dealership_id, @PathVariable double min_price) {
        return vehicleService.filterByMinPrice(dealership_id, min_price);
    }

    //GET all vehicles by max price method
    @RequestMapping(path="{dealership_id}/vehicles/{max_price}", method=RequestMethod.GET)
    public List<Vehicle> vehicles(@PathVariable int dealership_id, @PathVariable double max_price) {
        return vehicleService.filterByMaxPrice(dealership_id, max_price);
    }

    //GET all vehicles by both min and max price method
    @RequestMapping(path="{dealership_id}/vehicles/{min_price}/{max_price}", method=RequestMethod.GET)
    public List<Vehicle> vehicles(@PathVariable int dealership_id, @PathVariable double min_price, @PathVariable double max_price) {
        return vehicleService.filterByPrice(dealership_id, min_price, max_price);
    }

    //GET all vehicles by make method
    @RequestMapping(path="{dealership_id}/vehicles/{make}", method=RequestMethod.GET)
    public List<Vehicle> vehicles(@PathVariable int dealership_id, @PathVariable String make) {
        return vehicleService.filterByMake(dealership_id, make);
    }

    //GET all vehicles by model method
    @RequestMapping(path="{dealership_id}/vehicles/{model}", method=RequestMethod.GET)
    public List<Vehicle> vehicles(@PathVariable int dealership_id, @PathVariable String model) {
        return vehicleService.filterByModel(dealership_id, model);

    }//GET all vehicles by both make and model method
    @RequestMapping(path="{dealership_id}/vehicles/{make}/{model}", method=RequestMethod.GET)
    public List<Vehicle> vehicles(@PathVariable int dealership_id, @PathVariable String make, @PathVariable String model) {
        return vehicleService.filterByMakeModel(dealership_id, make, model);
    }

    //GET all vehicles by min year method
    @RequestMapping(path="{dealership_id}/vehicles/{min_year}", method=RequestMethod.GET)
    public List<Vehicle> vehicles(@PathVariable int dealership_id, @PathVariable int min_year) {
        return vehicleService.filterByMinYear(dealership_id, min_year);
    }

    //GET all vehicles by max year method
    @RequestMapping(path="{dealership_id}/vehicles/{max_year}", method=RequestMethod.GET)
    public List<Vehicle> vehicles(@PathVariable int dealership_id, @PathVariable int max_year) {
        return vehicleService.filterByMaxYear(dealership_id, max_year);
    }

    //GET all vehicles by both min and max year method
    @RequestMapping(path="{dealership_id}/vehicles/{minn_year}/{max_year}", method=RequestMethod.GET)
    public List<Vehicle> vehicles(@PathVariable int dealership_id, @PathVariable int min_year, @PathVariable int max_year) {
        return vehicleService.filterByYear(dealership_id, min_year, max_year);
    }

    //GET all vehicles by color method
    @RequestMapping(path="{dealership_id}/vehicles/{color}", method=RequestMethod.GET)
    public List<Vehicle> vehicles(@PathVariable int dealership_id, @PathVariable String color) {
        return vehicleService.filterByColor(dealership_id, color);
    }

    //GET all vehicles by min miles method
    @RequestMapping(path="{dealership_id}/vehicles/{min_miles}", method=RequestMethod.GET)
    public List<Vehicle> vehicles(@PathVariable int dealership_id, @PathVariable int min_miles) {
        return vehicleService.filterByMinMiles(dealership_id, min_miles);
    }

    //GET all vehicles by max miles method
    @RequestMapping(path="{dealership_id}/vehicles/{max_miles}", method=RequestMethod.GET)
    public List<Vehicle> vehicles(@PathVariable int dealership_id, @PathVariable int max_miles) {
        return vehicleService.filterByMaxMiles(dealership_id, max_miles);
    }

    //GET all vehicles by both min and max miles method
    @RequestMapping(path="{dealership_id}/vehicles/{min_miles}/{max_miles}", method=RequestMethod.GET)
    public List<Vehicle> vehicles(@PathVariable int dealership_id, @PathVariable int min_miles, @PathVariable int max_miles) {
        return vehicleService.filterByMiles(dealership_id, min_miles, max_miles);
    }

    //GET all vehicles by type method
    @RequestMapping(path="{dealership_id}/vehicles/{type}", method=RequestMethod.GET)
    public List<Vehicle> vehicles(@PathVariable int dealership_id, @PathVariable String type) {
        return vehicleService.type(dealership_id, type);
    }

    //POST add vehicle method
    @RequestMapping(path="{dealership_id}/vehicles", method=RequestMethod.POST)
    public int addVehicle(@PathVariable int dealership_id, @RequestBody Vehicle vehicle) {
        return vehicleService.addVehicle(dealership_id, vehicle);
    }

    //PUT update vehicle method
    @RequestMapping(path="{dealership_id}/vehicles", method=RequestMethod.PUT)
    public int updateVehicle(@PathVariable int dealership_id, @RequestBody Vehicle vehicle) {
        return vehicleService.updateVehicle(dealership_id, vehicle);
    }

    //DELETE delete vehicle method
    @RequestMapping(path="{dealership_id}/vehicles/{vin}", method=RequestMethod.DELETE )
    public int deleteVehicle(@PathVariable int dealership_id, @PathVariable String vin) {
        return vehicleService.deleteVehicle(dealership_id, vin);
    }
}
