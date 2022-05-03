package se.demo.applianceservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.demo.applianceservice.controller.model.RestResponse;
import se.demo.applianceservice.service.DatabaseService;

@Slf4j
@RestController
@RequestMapping("/api/database")
public class DatabaseController {

    @Autowired
    DatabaseService databaseService;

    @PutMapping("table/create/customer-and-appliance")
    public ResponseEntity<RestResponse> createCustomerAndApplianceTable() {
        log.info("createCustomerAndApplianceTable()");
        databaseService.generateCustomersAndAppliances();
        log.info("createCustomerAndApplianceTable(), done.");

        return ResponseEntity.ok(RestResponse.builder()
                .success(true)
                .build());
    }
}
