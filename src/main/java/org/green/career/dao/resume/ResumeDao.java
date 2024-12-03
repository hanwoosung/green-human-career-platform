package org.green.career.dao.resume;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.green.career.dto.resume.ResumeDto;

@Mapper
public interface ResumeDao {
    ResumeDto getUserInfo(String id);
}
