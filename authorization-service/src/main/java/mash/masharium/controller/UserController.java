package mash.masharium.controller;


import lombok.RequiredArgsConstructor;
import mash.masharium.api.auth.common.Jwt;
import mash.masharium.api.auth.response.UserInfoResponse;
import mash.masharium.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    public UserInfoResponse getUserInfo(@RequestParam("jwt") Jwt jwt) {
        return userService.prepareUserInfos(jwt);
    }
}
