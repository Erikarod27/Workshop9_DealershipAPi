package com.pluralsight.DealershipAPI.dataHandlers.abstractDAO;

import com.pluralsight.DealershipAPI.models.Dealership;

import java.util.List;

public interface DealershipDAO {
    List<Dealership> getAllDealerships();
}
