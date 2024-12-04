package org.green.career.service.resume;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dao.resume.ResumeDao;
import org.green.career.dto.resume.ResumeDto;
import org.springframework.stereotype.Service;
/**
 * 작성자: 구경림
 * 작성일: 2024-12-03
 * 이력서 서비스 인터페이스 구현체
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;

    @Override
    public ResumeDto getUserInfo(String id) {
        return resumeDao.getUserInfo(id);
    }
}
