package org.green.career.service.main;

import org.green.career.dao.main.MainDao;
import org.green.career.dto.common.CodeInfoDto;
import org.green.career.exception.BaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * 작성자: 한우성
 * 작성일: 2024-11-30
 */
class MainServiceImplTest {

    @Mock
    private MainDao mainDao;

    @InjectMocks
    private MainServiceImpl mainService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findSkillList_Success() {
        // Given
        List<CodeInfoDto> mockData = Arrays.asList(
                new CodeInfoDto("1", "CSS", "front_cd"),
                new CodeInfoDto("2", "Spring Boot", "back_cd")
        );
        when(mainDao.findSkillList()).thenReturn(mockData);

        // When
        Map<String, List<CodeInfoDto>> result = mainService.findSkillList();

        // Then
        assertNotNull(result);
        System.out.println("Grouped Data: " + result);

        assertTrue(result.containsKey("front"));
        assertTrue(result.containsKey("back"));
    }

    @Test
    void findSkillList_NoData() {
        // Given
        when(mainDao.findSkillList()).thenReturn(null);

        // When
        Exception exception = assertThrows(BaseException.class, () -> mainService.findSkillList());

        // Then
        assertNotNull(exception);
        System.out.println("에러: " + exception.getMessage());
    }
}
