package se.demo.applianceservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.demo.applianceservice.controller.model.ApplianceStatusResponse;
import se.demo.applianceservice.service.ConnectionStatusService;

@RestController
public class ConnectionStatusController {

    @Autowired
    private ConnectionStatusService connectionService;

    @PutMapping("generate-customers-and-appliances")
    public ResponseEntity<Boolean> generateCustomersAndAppliances(String applianceId) {
        connectionService.generateCustomersAndAppliances();
        return ResponseEntity.ok(true);
    }

    @PostMapping("acknowledge/{applianceId}")
    public ResponseEntity<Boolean> acknowledgeAppliancePing(@PathVariable("applianceId") String applianceId) {
        connectionService.updateApplianceStatus(applianceId);
        return ResponseEntity.ok(true);
    }

    @GetMapping("appliance-connection-status/{applianceId}")
    public ResponseEntity<ApplianceStatusResponse> getApplianceStatus(@PathVariable("applianceId") String applianceId) {
        ApplianceStatusResponse response = connectionService.getApplianceConnectionStatus(applianceId);
        return ResponseEntity.ok(response);

    }

}
