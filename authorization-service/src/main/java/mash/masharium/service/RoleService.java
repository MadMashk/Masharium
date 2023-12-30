package mash.masharium.service;

import mash.masharium.api.auth.constant.UserRoleType;
import mash.masharium.entity.UserLoginData;
import mash.masharium.entity.UserRole;

import java.util.List;
import java.util.Set;

public interface RoleService {

    List<UserRole> createUserRoles(Set<UserRoleType> roles, UserLoginData userLoginData );
}
