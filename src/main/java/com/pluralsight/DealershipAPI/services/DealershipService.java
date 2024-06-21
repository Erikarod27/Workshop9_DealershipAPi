package com.pluralsight.DealershipAPI.services;

import com.pluralsight.DealershipAPI.dataHandlers.abstractDAO.DealershipDAO;
import com.pluralsight.DealershipAPI.models.Dealership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DealershipService {
    private final DealershipDAO dealershipDAO;

    @Autowired
    public DealershipService(DealershipDAO dealershipDAO) {
        this.dealershipDAO = dealershipDAO;
    }

    public List<Dealership> getAllDealerships() {
        return dealershipDAO.getAllDealerships();
    }

    public List<Dealership> getByID(int id) {
        return dealershipDAO.filterByID(id);
    }

    public List<Dealership> getByName(String name) {
        return dealershipDAO.filterByName(name);
    }

    public List<Dealership> getByAddress(String address) {
        return dealershipDAO.filterByAddress(address);
    }

    public List<Dealership> getByPhoneNumber(String phoneNumber) {
        return dealershipDAO.filterByPhoneNumber(phoneNumber);
    }

}
