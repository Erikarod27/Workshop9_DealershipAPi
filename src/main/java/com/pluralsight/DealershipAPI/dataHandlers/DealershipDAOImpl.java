package com.pluralsight.DealershipAPI.dataHandlers;

import com.pluralsight.DealershipAPI.dataHandlers.abstractDAO.DataManager;
import com.pluralsight.DealershipAPI.dataHandlers.abstractDAO.DealershipDAO;
import com.pluralsight.DealershipAPI.models.Dealership;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DealershipDAOImpl extends DataManager implements DealershipDAO {

    public DealershipDAOImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Dealership> getAllDealerships() {
        List<Dealership> dealershipList = new ArrayList<>();
        try (Connection connection = getConnection()) {
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
        }

        return dealershipList;
    }
}
