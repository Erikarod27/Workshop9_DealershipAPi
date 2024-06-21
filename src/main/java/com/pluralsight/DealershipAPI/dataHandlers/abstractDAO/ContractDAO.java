package com.pluralsight.DealershipAPI.dataHandlers.abstractDAO;

import com.pluralsight.DealershipAPI.models.LeaseContract;
import com.pluralsight.DealershipAPI.models.SalesContract;
import com.pluralsight.DealershipAPI.models.abstractModels.Contract;

public interface ContractDAO {
    SalesContract getSalesContractById(int contractId);

    void addContract(Contract contract);

    LeaseContract getLeaseContractById(int contractId);
}
