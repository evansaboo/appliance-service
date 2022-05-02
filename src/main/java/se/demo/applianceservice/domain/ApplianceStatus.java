package se.demo.applianceservice.domain;

import lombok.Builder;

import java.util.Date;

@Builder
public record ApplianceStatus(String applianceId,
                              Boolean isConnected,
                              Date lastConnectionPing) {

}
