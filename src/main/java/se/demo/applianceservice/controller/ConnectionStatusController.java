package se.demo.applianceservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.demo.applianceservice.controller.model.ApplianceStatusResponse;
import se.demo.applianceservice.controller.model.RestResponse;
import se.demo.applianceservice.service.ConnectionStatusService;

@Slf4j
@RestController
@RequestMapping("/api/appliance")
public class ConnectionStatusController {

    @Autowired
    private ConnectionStatusService connectionService;

    @PostMapping("{applianceId}/acknowledge-ping")
    public ResponseEntity<RestResponse> acknowledgeAppliancePing(@PathVariable("applianceId") String applianceId) {
        log.info("acknowledgeAppliancePing(), applianceId: {}", applianceId);
        connectionService.updateApplianceStatus(applianceId);
        log.info("acknowledgeAppliancePing(), done.");

        return ResponseEntity.ok(RestResponse.builder()
                .success(true)
                .build());
    }

    @GetMapping("{applianceId}/status")
    public ResponseEntity<ApplianceStatusResponse> getApplianceStatus(@PathVariable("applianceId") String applianceId) {
        log.info("getApplianceStatus(), applianceId: {}", applianceId);
        ApplianceStatusResponse response = connectionService.getApplianceConnectionStatus(applianceId);
        log.info("getApplianceStatus(), done. response: {}", response);

        return ResponseEntity.ok(response);
    }

}
