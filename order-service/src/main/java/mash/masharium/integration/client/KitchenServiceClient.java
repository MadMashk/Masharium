package mash.masharium.integration.client;

import mash.masharium.api.kitchen.TicketRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "tickets", url = "#{clientProperties.kitchenServiceUrl}")
public interface KitchenServiceClient {

    @PostMapping("/tickets")
    void create(@RequestBody TicketRequestDto dto);

}
