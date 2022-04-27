package se.demo.applianceservice.controller.model;

import lombok.Builder;

@Builder
public record AppliancePingResponse(boolean connected){
}
