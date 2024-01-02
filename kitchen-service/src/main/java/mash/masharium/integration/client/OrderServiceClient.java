package mash.masharium.integration.client;

import mash.masharium.api.order.request.ChangeOrderStatusDto;
import mash.masharium.api.order.request.OrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "order-client", url = "#{clientProperties.orderServiceUrl}")
public interface OrderServiceClient {

    @PostMapping("orders")
    void changeStatus(@RequestBody ChangeOrderStatusDto changeOrderStatusDto);
}
