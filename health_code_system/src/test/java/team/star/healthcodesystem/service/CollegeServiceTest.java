package team.star.healthcodesystem.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 学院业务逻辑测试类
 *
 * @author Patrick_Star
 * @version 1.0
 */
public class CollegeServiceTest {
    private final CollegeService collegeService = new CollegeService();

    @Test
    public void testQueryCollegeList() {
        assertEquals(2, collegeService.queryCollegeList(1, 10).getTotal());
    }

    @Test
    public void testQueryCount() {
        assertEquals(2, collegeService.queryCount());
    }

    @Test
    public void testQueryCollege() {
        assertEquals(1, collegeService.queryCollege("test").getId());
    }

    @Test
    public void testAddCollege() {
        collegeService.addCollege("test_m4");
    }

    @Test
    public void testUpdateCollege() {
        collegeService.updateCollege(1, "test_ak");
    }

    @Test
    public void testDeleteCollege() {
        collegeService.deleteCollege(1);
    }
}
