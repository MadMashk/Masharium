package mash.masharium.controller;

import lombok.RequiredArgsConstructor;
import mash.masharium.api.auth.common.Jwt;
import mash.masharium.api.auth.common.UserDetailsDto;
import mash.masharium.api.auth.request.SignInRequest;
import mash.masharium.api.auth.request.SingUpRequest;
import mash.masharium.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public Jwt signup(@RequestBody SingUpRequest singUpRequest) {
        return authService.singupUser(singUpRequest);
    }

    @PostMapping("/signin")
    public Jwt signIn(@RequestBody SignInRequest signInRequest) {
        return authService.signInUser(signInRequest);
    }

    @PostMapping("/validation")
    public UserDetailsDto validateUsersToken(@RequestBody Jwt jwt) {
        return authService.validateToken(jwt);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody Jwt jwt) {
        authService.logoutUser(jwt);
    }

}
