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
}
