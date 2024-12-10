package org.green.career.service.company.jbskMngm.bookmarkSeeker;

import java.util.List;
import java.util.Map;

public interface BookmarkSeekerService {

    Map<String, Object> getBookmarkSeekerList(int page, String search, String id);
    List<String> selectBookmarkSeekerIds(String id);

}
