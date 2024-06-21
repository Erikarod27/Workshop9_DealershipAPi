package com.pluralsight.DealershipAPI.config;

import com.pluralsight.DealershipAPI.dataHandlers.ContractDAOImpl;
import com.pluralsight.DealershipAPI.dataHandlers.DealershipDAOImpl;
import com.pluralsight.DealershipAPI.dataHandlers.VehicleDAOImpl;
import com.pluralsight.DealershipAPI.dataHandlers.abstractDAO.ContractDAO;
import com.pluralsight.DealershipAPI.dataHandlers.abstractDAO.DealershipDAO;
import com.pluralsight.DealershipAPI.dataHandlers.abstractDAO.VehicleDAO;
import com.pluralsight.DealershipAPI.services.DealershipService;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Bean
    public ContractDAO contractDAO(DataSource dataSource) {
        return new ContractDAOImpl(dataSource);
    }

    @Bean
    public DealershipDAO dealershipDAO(DataSource dataSource) {
        return new DealershipDAOImpl(dataSource);
    }

    @Bean
    public VehicleDAO vehicleDAO(DataSource dataSource) {
        return new VehicleDAOImpl(dataSource);
    }

    @Bean
    public DataSource dataSource (@Value("${datasource.username}") String username, @Value("${datasource.password}") String password, @Value("${datasource.url}") String url) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
