package mash.masharium.client;

import mash.masharium.api.auth.common.Jwt;
import mash.masharium.api.auth.common.UserDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "auth-client", url = "#{clientProperties.authServiceUrl}")
public interface AuthServiceClient {

    @PostMapping("auth/validation")
    ResponseEntity<UserDetailsDto> validateUsersToken(@RequestBody Jwt jwt);

}
