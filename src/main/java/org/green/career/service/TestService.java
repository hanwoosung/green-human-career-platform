package org.green.career.service;


import lombok.RequiredArgsConstructor;
import org.green.career.dao.TestDao;
import org.green.career.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TestService {

    private  final TestDao testDao;

    public void test(){
        List<UserDto> testList = testDao.getUser();
        testList.forEach(user -> System.out.println(user.getName()));
    }


}
