package mash.masharium.mapper;

import mash.masharium.api.auth.response.UserInfoResponse;
import mash.masharium.entity.UserAccount;
import mash.masharium.entity.UserDevice;
import mash.masharium.entity.UserLoginData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {

    @Mapping(target = "userId", source = "userLoginData.id")
    @Mapping(target = "userDeviceInfos", source = "userDeviceList")
    UserInfoResponse mapToUserInfoResponse(UserAccount userAccount, UserLoginData userLoginData, List<UserDevice> userDeviceList);


}
