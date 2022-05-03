package se.demo.applianceservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.demo.applianceservice.controller.model.ApplianceStatusResponse;
import se.demo.applianceservice.domain.Appliance;
import se.demo.applianceservice.exception.EntityNotFoundException;
import se.demo.applianceservice.repository.ApplianceFacade;
import se.demo.applianceservice.repository.CustomerFacade;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class DatabaseService {
    @Autowired
    private CustomerFacade customerFacade;
    @Autowired
    private ApplianceFacade applianceFacade;

    public void generateCustomersAndAppliances() {
        log.debug("generateCustomersAndAppliances().");

        customerFacade.createCustomerTable();
        applianceFacade.createApplianceTable();

        log.debug("generateCustomersAndAppliances(), done.");
    }
}
