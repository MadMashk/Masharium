package mash.masharium.security.mapper;

import mash.masharium.api.auth.common.UserDetailsDto;
import mash.masharium.security.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDetailsMapper {

    User mapUserDetailsDtoToUser(UserDetailsDto userDetailsDto);
}
