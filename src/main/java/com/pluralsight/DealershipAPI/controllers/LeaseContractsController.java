package com.pluralsight.DealershipAPI.controllers;

import com.pluralsight.DealershipAPI.models.LeaseContract;
import com.pluralsight.DealershipAPI.models.abstractModels.Contract;
import com.pluralsight.DealershipAPI.services.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LeaseContractsController {
    private final ContractService contractService;

    @Autowired
    public LeaseContractsController(ContractService contractService) {
        this.contractService = contractService;
    }

    @RequestMapping(path="/LeaseContract/{contract_id}", method = RequestMethod.GET)
    public LeaseContract getLeaseContract(@PathVariable int contract_id) {
        return contractService.getLeaseContractById(contract_id);
    }

    @RequestMapping(path="/LeaseContract/add", method = RequestMethod.POST)
    public void addLeaseContract(@RequestBody Contract contract) {
        contractService.addContract(contract);
    }
}
