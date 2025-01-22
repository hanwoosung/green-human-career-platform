package org.green.career.dao.admin;

import org.apache.ibatis.annotations.Mapper;
import org.green.career.dto.admin.AdminUserDto;

import java.util.List;

@Mapper
public interface AdminDao {

    List<AdminUserDto> getUserList(int offset, int limit, String search, String toggle);

    int getTotalCnt(String search, String toggle);

    int updateStatus(List<String> ids, String status, String pw);

}
