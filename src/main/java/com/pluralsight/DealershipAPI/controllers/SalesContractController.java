package com.pluralsight.DealershipAPI.controllers;

import com.pluralsight.DealershipAPI.models.SalesContract;
import com.pluralsight.DealershipAPI.models.abstractModels.Contract;
import com.pluralsight.DealershipAPI.services.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SalesContractController {
    private final ContractService contractService;

    @Autowired
    public SalesContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @RequestMapping(path="/SalesContract/{contract_id}", method = RequestMethod.GET)
    public SalesContract getSalesContract(@PathVariable int contract_id) {
        return contractService.getSalesContractById(contract_id);
    }

    @RequestMapping(path="SalesContract/add", method=RequestMethod.POST)
    public void addSalesContract(@RequestBody Contract contract) {
        contractService.addContract(contract);
    }
}
