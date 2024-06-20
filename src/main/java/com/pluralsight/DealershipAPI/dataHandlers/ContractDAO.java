package com.pluralsight.DealershipAPI.dataHandlers;

import com.pluralsight.dataHandlers.abstractDAO.DataManager;
import com.pluralsight.models.LeaseContract;
import com.pluralsight.models.SalesContract;
import com.pluralsight.models.Vehicle;
import com.pluralsight.models.abstractModels.Contract;

import java.util.List;

public class ContractDAO extends DataManager {

//TODO Separate the save contracts into saveLeaseContract and saveSalesContract
    public void saveContract(Contract contract) {
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
}

