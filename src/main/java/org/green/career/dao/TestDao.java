package org.green.career.dao;


import org.apache.ibatis.annotations.Mapper;
import org.green.career.dto.UserDto;

import java.util.List;

@Mapper
public interface TestDao {
    List<UserDto> getUser();
}
