package org.green.career.service.resume;

import org.green.career.dto.resume.ResumeDto;

/**
 * 작성자: 구경림
 * 작성일: 2024-12-03
 * 이력서 서비스 인터페이스
 * TODO:
 */
public interface ResumeService {
    ResumeDto getUserInfo(String id);
}
