package se.demo.applianceservice.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import se.demo.applianceservice.domain.Appliance;
import se.demo.applianceservice.repository.ApplianceFacade;
import se.demo.applianceservice.repository.CustomerFacade;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConnectionStatusControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    ApplianceFacade applianceFacade;

    @MockBean
    CustomerFacade customerFacade;

    String APPLIANCE_ID = "UXD123";

    @Test
    void generateCustomersAndAppliances() {
        webTestClient.put()
                .uri("/api/generate-customers-and-appliances")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("success").isEqualTo("true");
    }

    @Test
    void acknowledgeAppliancePing() {
        webTestClient.post()
                .uri("/api/acknowledge/" + APPLIANCE_ID)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("success").isEqualTo("true");
    }

    @Test
    void getApplianceStatus() {
        LocalDateTime datetimeNow = LocalDateTime.now();

        Mockito.when(applianceFacade.getApplianceStatus(any())).thenReturn(
                List.of(Appliance.builder()
                        .applianceId(APPLIANCE_ID)
                        .lastConnectionPing(datetimeNow).build()
                ));

        webTestClient.get()
                .uri("/api/appliance-connection-status/" + APPLIANCE_ID)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("applianceId").isEqualTo(APPLIANCE_ID)
                .jsonPath("isConnected").isEqualTo("true")
                .jsonPath("lastConnection").isEqualTo(datetimeNow.toString());
    }
}