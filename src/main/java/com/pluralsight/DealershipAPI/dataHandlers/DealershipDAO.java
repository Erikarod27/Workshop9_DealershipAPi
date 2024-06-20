package com.pluralsight.DealershipAPI.dataHandlers;

import com.pluralsight.DealershipAPI.dataHandlers.abstractDAO.DataManager;
import com.pluralsight.DealershipAPI.models.Dealership;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DealershipDAO extends DataManager {

    public DealershipDAO() {
    }

    public List<Dealership> loadDealershipsFromDatabase() {
        openConnection();

        List<Dealership> dealershipList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet dealerships = statement.executeQuery("SELECT * FROM dealerships");

            while (dealerships.next()) {
                int id = dealerships.getInt("dealershipID");
                String name = dealerships.getString("name");
                String address = dealerships.getString("address");
                String phoneNumber = dealerships.getString("phoneNumber");
                Dealership dealership = new Dealership(id, name, address, phoneNumber);
                dealershipList.add(dealership);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return dealershipList;
    }

}
