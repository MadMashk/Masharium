package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import mash.masharium.api.auth.constant.UserRoleType;
import mash.masharium.entity.UserLoginData;
import mash.masharium.entity.UserRole;
import mash.masharium.repository.UserRoleRepository;
import mash.masharium.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImp implements RoleService {

    private final UserRoleRepository userRoleRepository;

    @Override
    public List<UserRole> createUserRoles(Set<UserRoleType> roles, UserLoginData userLoginData) {

        return userRoleRepository.saveAll(roles.stream().map(role -> {
            UserRole userRole = new UserRole();
            userRole.setUserRoleType(role);
            userRole.setUserLoginData(userLoginData);
            return userRole;
        }).toList());

    }
}
