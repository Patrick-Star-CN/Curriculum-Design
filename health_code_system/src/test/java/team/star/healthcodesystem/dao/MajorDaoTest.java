package team.star.healthcodesystem.dao;

import org.junit.jupiter.api.Test;
import team.star.healthcodesystem.model.Major;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 专业数据访问对象测试类
 *
 * @author Patrick_Star
 * @version 1.3
 */
public class MajorDaoTest {
    private final MajorDao majorDao = new MajorDao();
    @Test
    public void testInputMajor() {
        majorDao.insert(Major.builder()
                .name("test")
                .collegeId(1).build());
    }

    @Test
    public void testQueryMajor() {
        Major major = majorDao.query(1);
        assertEquals(1, major.getId());
    }

    @Test
    public void testUpdateMajor() {
        majorDao.update(Major.builder()
                .id(1)
                .name("test_update")
                .collegeId(1).build());
    }

    @Test
    public void testDeleteMajor() {
        majorDao.delete(1);
    }

    @Test
    public void testQueryAllMajor() {
        assertEquals(1, majorDao.query(1, 10).size());
    }

    @Test
    public void testQueryMajorByName() {
        assertEquals(1, majorDao.query("test").getId());
    }

    @Test
    public void testQueryCount() {
        assertEquals(1, majorDao.queryCount());
    }

    @Test
    public void testQueryMajorByCollegeId() {
        assertEquals(1, majorDao.query(1, 1, 10).size());
    }

    @Test
    public void testQueryCountByCollegeId() {
        assertEquals(1, majorDao.queryCount(1));
    }
}
