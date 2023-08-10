package team.star.healthcodesystem.dao;

import org.junit.jupiter.api.Test;
import team.star.healthcodesystem.model.Class;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 班级数据访问对象测试类
 *
 * @author Patrick_Star
 * @version 1.1
 */
public class ClassDaoTest {
    private final ClassDao classDao = new ClassDao();

    @Test
    public void testInputClass() {
        classDao.insert(Class.builder()
                .name(("test"))
                .majorId(1).build());
    }

    @Test
    public void testQueryClass() {
        Class clazz = classDao.query(1);
        assertEquals(1, clazz.getId());
    }

    @Test
    public void testUpdateClass() {
        classDao.update(Class.builder()
                .id(1)
                .name("test_update")
                .majorId(1).build());
    }

    @Test
    public void testDeleteClass() {
        classDao.delete(1);
    }

    @Test
    public void testQueryAllClass() {
        assertEquals(1, classDao.query(1, 10).size());
    }

    @Test
    public void testQueryClassByName() {
        assertEquals(2, classDao.query("test_new").getId());
    }

    @Test
    public void testQueryClassByMajorId() {
        assertEquals(2, classDao.query(2, 1, 10).size());
    }

    @Test
    public void testQueryClassCount() {
        assertEquals(1, classDao.queryCount());
    }

    @Test
    public void testQueryClassCountByMajorId() {
        assertEquals(1, classDao.queryCount(1));
    }
}
