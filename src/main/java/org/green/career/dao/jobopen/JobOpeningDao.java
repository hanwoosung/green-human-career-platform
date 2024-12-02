package org.green.career.dao.jobopen;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.green.career.dto.common.file.request.TblFileRequestDto;
import org.green.career.dto.jobopen.requset.JobOpeningRequestDto;

import java.util.List;

@Mapper
public interface JobOpeningDao {

    int insertJobOpening(@Param("job") JobOpeningRequestDto jobOpeningRequestDto);

    int selectMax();

    int insertSkills(@Param("jNo") int jNo, @Param("skills") List<String> skillList);

    int insertFiles(@Param("files") List<TblFileRequestDto> fileList);

}
