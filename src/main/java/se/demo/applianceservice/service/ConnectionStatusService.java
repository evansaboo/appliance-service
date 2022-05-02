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
public class ConnectionStatusService {
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

    public void updateApplianceStatus(String applianceId) {
        log.debug("updateApplianceStatus(), applianceId: {}", applianceId);

        applianceFacade.updateApplianceStatus(applianceId);

        log.debug("updateApplianceStatus(), done.");
    }

    public ApplianceStatusResponse getApplianceConnectionStatus(String applianceId) {
        log.debug("getApplianceConnectionStatus(), applianceId: {}", applianceId);

        Optional<Appliance> applianceOptional = applianceFacade.getApplianceStatus(applianceId).stream().findFirst();
        Appliance appliance = getAppliance(applianceOptional, applianceId);

        ApplianceStatusResponse response = ApplianceStatusResponse.builder()
                .applianceId(appliance.applianceId())
                .isConnected(isApplianceConnected(appliance))
                .lastConnection(appliance.lastConnectionPing().toString())
                .build();

        log.debug("getApplianceConnectionStatus(), response: {}", response);
        return response;
    }

    private Appliance getAppliance(Optional<Appliance> appliance, String applianceId) {
        if (appliance.isPresent()) {
            return appliance.get();
        } else {
            throw new EntityNotFoundException("Couldn't find appliance with ID: " + applianceId);
        }
    }

    /**
     * An appliance is classified as disconnected if it has not pinged for longer than 2 minutes.
     */
    private Boolean isApplianceConnected(Appliance appliance) {
        int timeInSeconds = 120;
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now, appliance.lastConnectionPing());

        return Math.abs(duration.toSeconds()) <= timeInSeconds;
    }
}
