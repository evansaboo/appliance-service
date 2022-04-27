package se.demo.applianceservice.domain;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Appliance(String applianceId, LocalDateTime lastConnectionPing) {

}
