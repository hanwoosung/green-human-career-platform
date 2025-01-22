package org.green.career.service.admin;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.green.career.dao.admin.AdminDao;
import org.green.career.dao.company.jbskMngm.JobOfferDao;
import org.green.career.dto.admin.AdminUserDto;
import org.green.career.dto.company.jbskMngm.JobOfferDto;
import org.green.career.exception.BaseException;
import org.green.career.service.AbstractService;
import org.green.career.service.company.jbskMngm.joboffer.JobOfferService;
import org.green.career.type.ResultType;
import org.green.career.utils.PagingBtn;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 작성자: 김상준
 * 작성일: 2024-12-03
 * 기업 입사제안현황
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl extends AbstractService implements AdminService {

    private final AdminDao adminDao;
    private final PasswordEncoder passwordEncoder;
    /*
     * 페이징을 추가한 list 뿌려주기
     * @param page = 현재 페이지
     * */

    @Override
    public Map<String, Object> getUserList(int page, String search, String toggle) {

        Map<String, Object> result = new HashMap<>();

        int pageSize = 10;
        int offset = (page - 1) * pageSize;

        int totalCount = totalCount(search, toggle);

        List<AdminUserDto> userList = userListPaging(offset, pageSize, search, toggle);

        PagingBtn paging = totalCount > 0
                ? new PagingBtn(totalCount, page, pageSize, 10)
                : new PagingBtn(0, 1, pageSize, 10);

        result.put("list", userList);
        result.put("paging", paging);

        return result;
    }

    /*
     * 페이징을 추가한 list 뿌려주기
     * @param offset = 현재 페이지
     * @param limit = 최대 개수+
     * @param offset = ID
     * */

    private List<AdminUserDto> userListPaging(int offset, int limit, String search, String toggle) {
        return adminDao.getUserList(offset, limit, search, toggle);
    }

    /*
     * 페이징을 위한 총 개수 가져오기
     * @param offset = 현재 페이지
     * @param limit = 최대 개수+
     * @param id = 로그인 ID
     * */

    private int totalCount(String search, String toggle) {
        return adminDao.getTotalCnt(search, toggle);
    }

    @Override
    public int updateUser(List<String> ids, String status) {

        String pw = passwordEncoder.encode("113333444444");

        return returnData(() -> {
            int result = 0;
            try {
                adminDao.updateStatus(ids, status, pw);
                System.out.println(ids);
                System.out.println(status);
                System.out.println(pw);
            } catch (BaseException e) {
                throw new BaseException(ResultType.ERROR, "저장 실패!", e);
            }

            return result;
        });
    }

}
