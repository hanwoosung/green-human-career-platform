package org.green.career.service.login.regist;

import org.apache.ibatis.annotations.Mapper;
import org.green.career.dto.login.UserLoginDto;

import java.util.List;

public interface RegistCompanyService {
    int registCompany(UserLoginDto user);
    int checkId(String id);
}
