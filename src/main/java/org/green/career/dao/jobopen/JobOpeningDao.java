package org.green.career.dao.jobopen;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.green.career.dto.common.CodeInfoDto;
import org.green.career.dto.common.file.request.TblFileRequestDto;
import org.green.career.dto.common.file.response.FileResponseDto;
import org.green.career.dto.jobopen.JobOpeningDetailDto;
import org.green.career.dto.jobopen.requset.JobOpeningRequestDto;
import org.green.career.dto.jobopen.response.JobOpenCompanyDto;
import org.green.career.dto.jobopen.response.JobRecordDto;
import org.green.career.dto.jobopen.response.ResponseMyResume;

import java.util.List;

/**
 * 작성자: 한우성
 * 작성일: 2024-11-30
 * 채용공고전체 다오
 */
@Mapper
public interface JobOpeningDao {

    int insertJobOpening(@Param("job") JobOpeningRequestDto jobOpeningRequestDto);

    int selectMax();

    int insertSkills(@Param("jNo") int jNo, @Param("skills") List<String> skillList);

    int deleteSkills(@Param("jNo") int jNo, @Param("skills") List<String> removedSkills);

    int insertFiles(@Param("files") List<TblFileRequestDto> fileList);

    JobOpeningDetailDto getJobOpening(int jNo);

    List<FileResponseDto> getFile(int jNo);

    int deleteFileDB(@Param("fileId") Long fileId, @Param("refId") String refId);

    FileResponseDto getFileById(@Param("fileId") Long fileId);

    int updateJobOpening(@Param("jNo") int jNo, @Param("job") JobOpeningRequestDto jobOpeningRequestDto);

    JobOpenCompanyDto getCompany(String companyId);

    List<JobRecordDto> getResumeList(int jNo);

    int jobOpeningPass(@Param("jrNo") int jrNo, @Param("type") String type);

    List<ResponseMyResume> myResumes(String id);

    int resumeApply(@Param("jNo") int jNo, @Param("rNo") int rNo, @Param("id") String id);

    int existsResume(@Param("jNo") int jNo, @Param("rNo") int rNo, @Param("id") String id);

    int delete(int jNo);
    List<CodeInfoDto> mySkill(int jNo);

}
