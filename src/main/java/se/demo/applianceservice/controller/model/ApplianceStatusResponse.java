package se.demo.applianceservice.controller.model;

import lombok.Builder;

@Builder
public record ApplianceStatusResponse(String applianceId, boolean isConnected, String lastConnection) {
}
