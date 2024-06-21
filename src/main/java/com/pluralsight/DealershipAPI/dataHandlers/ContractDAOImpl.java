package com.pluralsight.DealershipAPI.dataHandlers;

import com.pluralsight.DealershipAPI.dataHandlers.abstractDAO.ContractDAO;
import com.pluralsight.DealershipAPI.dataHandlers.abstractDAO.DataManager;
import com.pluralsight.DealershipAPI.models.LeaseContract;
import com.pluralsight.DealershipAPI.models.SalesContract;
import com.pluralsight.DealershipAPI.models.Vehicle;
import com.pluralsight.DealershipAPI.models.abstractModels.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class ContractDAOImpl extends DataManager implements ContractDAO {

    @Autowired
    public ContractDAOImpl(DataSource dataSource) {
        super(dataSource);
    }
//TODO Separate the save contracts into saveLeaseContract and saveSalesContract
    public void addContract(Contract contract) {
        String insertSql = "";
        Vehicle soldVehicle = contract.getVehicleSold();
        List<?> parameters = List.of();

        if (contract instanceof LeaseContract) {
            insertSql = "INSERT INTO lease_contracts (`vin`,`contract_date`,`customer_name`,`customer_email`,`expectedEndingValue`,`leaseFee`,`totalPrice`,`monthlyPayment`) VALUES (?,?,?,?,?,?,?,?)";

            parameters = List.of(
                    soldVehicle.vin(),
                    contract.getDateOfContract(),
                    contract.getCustomerName(),
                    contract.getCustomerEmail(),
                    ((LeaseContract) contract).getExpectedEndingValue(),
                    ((LeaseContract) contract).getLeaseFee(),
                    contract.getTotalPrice(),
                    contract.getMonthlyPayment()
            );
        }
        if (contract instanceof SalesContract) {

            insertSql = "INSERT INTO sales_contracts (`vin`,`contract_date`,`customer_name`,`customer_email`,`salesTaxPercentage`,`recordingFee`,`processingFee`,`totalPrice`,`financing`,`monthlyPayment`) VALUES (?,?,?,?,?,?,?,?,?,?)";
            parameters = List.of(
                    soldVehicle.vin(),
                    contract.getDateOfContract(),
                    contract.getCustomerName(),
                    contract.getCustomerEmail(),
                    ((SalesContract) contract).getSalesTaxPercentage(),
                    ((SalesContract) contract).getRecordingFee(),
                    ((SalesContract) contract).getProcessingFee(),
                    contract.getTotalPrice(),
                    ((SalesContract) contract).isWouldLikeToFinance() ? "YES" : "NO",
                    contract.getMonthlyPayment()
            );
        }

        executeUpdate(insertSql, parameters);
    }

    @Override
    public LeaseContract getLeaseContractById(int contractId) {
        String query = "SELECT * FROM leaseContracts WHERE contract_id = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setInt(1, contractId);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                System.out.println("hello");
//                return getContractInfo(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public SalesContract getSalesContractById(int contractId) {
        String query = "SELECT * FROM salesContracts WHERE contract_id = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setInt(1, contractId);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                System.out.println("hello");
//                return getContractInfo(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

//    private SalesContract getContractInfo(ResultSet resultSet) {
//
//    }
}

