package org.green.career.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 작성자: 한우성
 * 작성일: 2024-12-01
 * 디비에서 Char 타입으로 되어 있는 값들을 받아서 텍스트 or 코드로 바꿔주는 유틸
 */
public class CodeMapper {

    private static final Map<String, Map<String, String>> CATEGORY_MAP = new HashMap<>();

    static {
        Map<String, String> jobStatusMap = Map.of(
                "C", "마감",
                "O", "모집중",
                "S", "모집 일시중지"
        );
        CATEGORY_MAP.put("jobStatus", jobStatusMap);

        Map<String, String> workTypeMap = Map.of(
                "C", "계약직",
                "F", "정규직",
                "A", "계약/정규직"
        );
        CATEGORY_MAP.put("workType", workTypeMap);

        Map<String, String> upCdMap = Map.of(
                "cloud_cd", "클라우드",
                "mobile_cd", "모바일",
                "devops_cd", "데브옵스",
                "back_cd", "백엔드",
                "front_cd", "프론트엔드",
                "stack_cd", "기술 스택",
                "data_cd", "데이터 분석",
                "design_cd", "디자인",
                "db_cd", "데이터베이스"
        );
        CATEGORY_MAP.put("upCd", upCdMap);
    }

    /**
     * 코드 → 설명 변환
     *
     * @param category 카테고리 (예: "jobStatus", "workType")
     */
    public static String getDescription(String category, String code) {
        return CATEGORY_MAP.getOrDefault(category, Map.of()).getOrDefault(code, code);
    }

    /**
     * 설명 → 코드 변환
     *
     * @param category 카테고리 (예: "jobStatus", "workType")
     */
    public static String getCode(String category, String description) {
        return CATEGORY_MAP.getOrDefault(category, Map.of()).entrySet().stream()
                .filter(entry -> entry.getValue().equals(description))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(description);
    }
}
