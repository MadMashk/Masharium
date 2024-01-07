package mash.masharium.service;

import mash.masharium.api.auth.common.Jwt;
import mash.masharium.api.auth.common.UserDetailsDto;
import mash.masharium.api.auth.request.SignInRequest;
import mash.masharium.api.auth.request.SingUpRequest;

public interface AuthService {
    Jwt singupUser(SingUpRequest singUpRequest);

    Jwt signInUser(SignInRequest signInRequest);

    UserDetailsDto validateToken(Jwt token);

    void logoutUser(Jwt token);
}
