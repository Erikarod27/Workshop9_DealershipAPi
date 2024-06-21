package com.pluralsight.DealershipAPI.dataHandlers.abstractDAO;

import com.pluralsight.DealershipAPI.models.Dealership;

import java.util.List;

public interface DealershipDAO {
    List<Dealership> getAllDealerships();

    List<Dealership> filterByID(int id);

    List<Dealership> filterByName(String name);

    List<Dealership> filterByAddress(String Address);

    List<Dealership> filterByPhoneNumber(String PhoneNumber);
}
