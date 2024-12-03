package org.green.career.service.resume;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dao.resume.ResumeDao;
import org.green.career.dto.resume.ResumeDto;
import org.springframework.stereotype.Service;
/**
 * 작성자: 구경림
 * 작성일: 2024-12-03
 * 구직자 이력서 인터페이스 구현체
 * TODO: 기업 회원가입과 통합필요, 서버측 유효성검사
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
