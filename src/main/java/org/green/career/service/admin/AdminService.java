package org.green.career.service.admin;

import java.util.List;
import java.util.Map;

public interface AdminService {

    Map<String, Object> getUserList(int page, String search, String toggle);

    int updateUser(List<String> id, String status);

}
