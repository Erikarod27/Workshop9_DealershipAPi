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

    public <T> List<Dealership> getDealership(String query, List<T> arguments) {
        List<Dealership> dealerships = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);

            for (int i = 0; i < arguments.size(); i++) {
                T argument = arguments.get(i);
                if (argument instanceof Integer) {
                    ps.setInt(i + 1, (Integer) argument);
                } else if (argument instanceof Double) {
                    ps.setDouble(i + 1, (Double) argument);
                } else if (argument instanceof String) {
                    ps.setString(i + 1, (String) argument);
                } else {
                    throw new IllegalArgumentException("Unsupported argument type");
                }
            }
            ResultSet dealer = ps.executeQuery();

            while (dealer.next()) {
                Dealership dealership = getDealershipInfo(dealer);
                dealerships.add(dealership);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dealerships;
    }

    @Override
    public List<Dealership> filterByID(int id) {
        String query = "SELECT * FROM dealership WHERE id = ?";
        return getDealership(query, List.of(id));
    }

    @Override
    public List<Dealership> filterByName(String name) {
        String query = "SELECT * FROM dealership WHERE name = ?";
        return getDealership(query, List.of(name));
    }

    @Override
    public List<Dealership> filterByAddress(String address) {
        String query = "SELECT * FROM dealership WHERE address = ?";
        return getDealership(query, List.of(address));
    }

    @Override
    public List<Dealership> filterByPhoneNumber(String phoneNumber) {
        String query = "SELECT * FROM dealership WHERE PhoneNumber = ?";
        return getDealership(query, List.of(phoneNumber));
    }

    private Dealership getDealershipInfo(ResultSet dealer) {
        try {
            int id = dealer.getInt("ID");
            String name = dealer.getString("name");
            String address = dealer.getString("address");
            String phoneNumber = dealer.getString("phoneNUmber");

            return new Dealership(id, name, address, phoneNumber);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
