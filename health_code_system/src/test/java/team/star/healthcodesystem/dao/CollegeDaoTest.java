package team.star.healthcodesystem.dao;

import org.junit.jupiter.api.Test;
import team.star.healthcodesystem.model.College;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 学院数据访问对象测试类
 *
 * @author Patrick_Star
 * @version 1.2
 */
public class CollegeDaoTest {
    private final CollegeDao collegeDao = new CollegeDao();
    @Test
    public void testInputCollege() {
        collegeDao.insert(College.builder()
                .name("test")
                .build());
    }

    @Test
    public void testQueryCollege() {
        College college = collegeDao.query(1L);
        assertEquals(1, college.getId());
    }

    @Test
    public void testUpdateCollege() {
        collegeDao.update(College.builder()
                .id(1)
                .name("test_update")
                .build());
    }

    @Test
    public void testDeleteCollege() {
        collegeDao.delete(1L);
    }

    @Test
    public void testQueryCollegeByName() {
        assertEquals(1, collegeDao.query(1, 10).size());
    }

    @Test
    public void testQueryAllCollege() {
        assertEquals(1, collegeDao.query("test").getId());
    }

    @Test
    public void testQueryCollegeCount() {
        assertEquals(1, collegeDao.queryCount());
    }
}
