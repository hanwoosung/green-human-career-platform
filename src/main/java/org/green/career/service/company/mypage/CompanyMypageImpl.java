package org.green.career.service.company.mypage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dao.company.mypage.CompanyModiDao;
import org.green.career.dto.common.file.request.TblFileRequestDto;
import org.green.career.dto.company.mypage.CompanyModiDto;
import org.green.career.dto.company.mypage.CompanyUserDto;
import org.green.career.exception.BaseException;
import org.green.career.service.AbstractService;
import org.green.career.service.login.LoginService;
import org.green.career.type.ResultType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyMypageImpl extends AbstractService implements CompanyMypageService {

    private final PasswordEncoder passwordEncoder;
    private final CompanyModiDao companyModiDao;

    @Override
    public CompanyModiDto getCompanyModi(CompanyUserDto dto) {

        System.out.println("DTO " + dto);
        CompanyModiDto user = companyModiDao.getCompanyModiById(dto.getId());
        System.out.println("user " + user);
        if(user == null) {
            CompanyModiDto user1 = companyModiDao.getCompanyModiId(dto.getId());
            System.out.println("user1 " + user1);
            if (!passwordEncoder.matches(dto.getPw(), user1.getPw())) {
                log.warn("비밀번호가 일치하지 않음. 입력 비밀번호: {}, 데이터베이스 비밀번호: {}", dto.getPw(), user1.getPw());
                return null;
            } else {
                System.out.println("true Service" + user1);
                return user1;
            }
        } else {
            if (!passwordEncoder.matches(dto.getPw(), user.getPw())) {
                log.warn("비밀번호가 일치하지 않음. 입력 비밀번호: {}, 데이터베이스 비밀번호: {}", dto.getPw(), user.getPw());
                return null;
            } else {
                System.out.println("true Service" + user);
                return user;
            }
        }
    }

    @Override
    public int insertMypageProfile(TblFileRequestDto dto) {
        companyModiDao.deleteMypageProfile(dto.getFileRefId());
        companyModiDao.insertMypageProfile(dto);
//        companyModiDao.updateMypageProfile(dto);
        return 0;
    }

    @Override
    public int updateMypageInfo(CompanyModiDto dto) {
        companyModiDao.updateMyInfo(dto);
        return 0;
    }
}
