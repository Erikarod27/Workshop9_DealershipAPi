package com.pluralsight.DealershipAPI.services;

import com.pluralsight.DealershipAPI.dataHandlers.abstractDAO.ContractDAO;
import com.pluralsight.DealershipAPI.models.LeaseContract;
import com.pluralsight.DealershipAPI.models.SalesContract;
import com.pluralsight.DealershipAPI.models.abstractModels.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContractService {
    private final ContractDAO contractDAO;

    @Autowired
    public ContractService(ContractDAO contractDAO) {
        this.contractDAO = contractDAO;
    }

    public SalesContract getSalesContractById(int contract_id) {
        return contractDAO.getSalesContractById(contract_id);
    }

    public LeaseContract getLeaseContractById(int contract_id) {
        return contractDAO.getLeaseContractById(contract_id);
    }

    public void addContract(Contract contract) {
        contractDAO.addContract(contract);
    }
}
