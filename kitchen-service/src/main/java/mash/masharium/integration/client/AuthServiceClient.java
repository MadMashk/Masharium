package mash.masharium.integration.client;

import mash.masharium.api.auth.common.Jwt;
import mash.masharium.api.auth.response.TokenValidationResponse;
import mash.masharium.api.auth.response.UserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "auth-client", url = "#{clientProperties.authServiceUrl}")
public interface AuthServiceClient {

    @PostMapping("/auth/validation")
    TokenValidationResponse validateUsersToken(@RequestBody Jwt jwt);

    @GetMapping("/users/info")
    UserInfoResponse getUserInfo(@RequestParam String jwt);
}
