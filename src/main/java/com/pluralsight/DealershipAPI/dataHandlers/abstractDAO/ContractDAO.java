package com.pluralsight.DealershipAPI.dataHandlers.abstractDAO;

import com.pluralsight.DealershipAPI.models.abstractModels.Contract;

public interface ContractDAO {
    void saveContract(Contract contract);
}
