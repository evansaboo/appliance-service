package se.demo.applianceservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.demo.applianceservice.controller.model.ApplianceStatusResponse;
import se.demo.applianceservice.controller.model.RestResponse;
import se.demo.applianceservice.service.ConnectionStatusService;

@RestController
@RequestMapping("/api")
public class ConnectionStatusController {

    @Autowired
    private ConnectionStatusService connectionService;

    @PutMapping("generate-customers-and-appliances")
    public ResponseEntity<RestResponse> generateCustomersAndAppliances(String applianceId) {
        connectionService.generateCustomersAndAppliances();
        return ResponseEntity.ok(RestResponse.builder()
                .success(true)
                .build());
    }

    @PostMapping("acknowledge/{applianceId}")
    public ResponseEntity<RestResponse> acknowledgeAppliancePing(@PathVariable("applianceId") String applianceId) {
        connectionService.updateApplianceStatus(applianceId);
        return ResponseEntity.ok(RestResponse.builder()
                .success(true)
                .build());
    }

    @GetMapping("appliance-connection-status/{applianceId}")
    public ResponseEntity<ApplianceStatusResponse> getApplianceStatus(@PathVariable("applianceId") String applianceId) {
        ApplianceStatusResponse response = connectionService.getApplianceConnectionStatus(applianceId);
        return ResponseEntity.ok(response);

    }

}
