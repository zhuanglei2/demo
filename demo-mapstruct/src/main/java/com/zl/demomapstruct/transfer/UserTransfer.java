package com.zl.demomapstruct.transfer;

import com.zl.demomapstruct.dto.User;
import com.zl.demomapstruct.enums.UserTypeEnum;
import com.zl.demomapstruct.vo.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/9 10:47
 */
@Mapper(componentModel = "spring")
public interface UserTransfer {
    UserTransfer INSTANCE = Mappers.getMapper(UserTransfer.class);

    /**
     * 字段数量类型数量相同，利用工具BeanUtils也可以实现类似效果
     * @param source
     * @return
     */
    UserVO1 toConvertVO1(User source);
    User fromConvertEntity1(UserVO1 userVO1);

    /**
     * 字段数量类型相同,数量少：仅能让多的转换成少的，故没有fromConvertEntity2
     * @param source
     * @return
     */
    UserVO2 toConvertVO2(User source);


    @Mappings({@Mapping(target = "createTime",expression = "java(com.zl.demomapstruct.utils.DateTransform.strToDate(source.getCreateTime()))"),})
    UserVO3 toConvertVO3(User source);

    User fromConvertEntity3(UserVO3 userVO3);

    @Mappings({
            @Mapping(source = "id", target = "userId"),
            @Mapping(source = "name", target = "userName")
    })
    UserVO4 toConvertVO4(User source);

    User fromConvertEntity4(UserVO4 userVO4);

    @Mapping(source = "userTypeEnum", target = "type")
    UserVO5 toConvertVO5(UserEnum source);

    UserEnum fromConvertEntity5(UserVO5 userVO5);
}
