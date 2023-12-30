package mash.masharium.service;

import mash.masharium.api.auth.request.SignInRequest;
import mash.masharium.api.auth.request.SingUpRequest;
import mash.masharium.api.auth.common.Jwt;
import mash.masharium.api.auth.response.TokenValidationResponse;

public interface AuthService {
    Jwt singupUser(SingUpRequest singUpRequest);

    Jwt signInUser(SignInRequest signInRequest);

    TokenValidationResponse validateToken(Jwt token);

    void logoutUser(Jwt token);
}
