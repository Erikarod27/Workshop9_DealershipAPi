package com.pluralsight.DealershipAPI.controllers;

import com.pluralsight.DealershipAPI.models.Dealership;
import com.pluralsight.DealershipAPI.services.DealershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DealershipController {
    private final DealershipService dealershipService;

    @Autowired
    public DealershipController(DealershipService dealershipService) {
        this.dealershipService = dealershipService;
    }

    @RequestMapping(path = "/dealerships", method = RequestMethod.GET)
    public List<Dealership> dealerships() {
        return dealershipService.getAllDealerships();
    }
}
