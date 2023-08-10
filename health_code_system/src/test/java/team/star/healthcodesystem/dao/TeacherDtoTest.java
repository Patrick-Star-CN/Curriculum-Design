package team.star.healthcodesystem.dao;

import org.junit.jupiter.api.Test;
import team.star.healthcodesystem.model.Teacher;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 教师数据访问对象测试类
 *
 * @author Patrick_Star
 * @version 1.1
 */
public class TeacherDtoTest {
    private final TeacherDao teacherDao = new TeacherDao();
    @Test
    public void testInputTeacher() {
        teacherDao.insert(Teacher.builder()
                .id(202103150102L)
                .name("test")
                .collegeId(1)
                .iid("330181200001010101")
                .type(Teacher.Type.TEACHER)
                .password("123456")
                .build());
    }

    @Test
    public void testUpdateTeacher() {
        teacherDao.update(Teacher.builder()
                .id(202103150102L)
                .name("test_update")
                .collegeId(1)
                .iid("330181200001010101")
                .type(Teacher.Type.SUPER_ADMIN)
                .password("123456")
                .build());
    }

    @Test
    public void testQueryTeacherById() {
        Teacher teacher = teacherDao.query(202103150102L);
        assertEquals("test_update", teacher.getName());
    }

    @Test
    public void testDeleteTeacher() {
        teacherDao.delete(202103150102L);
    }


    @Test
    public void testQueryAllTeacher() {
        assertEquals(2, teacherDao.query(1, 10).size());
    }

    @Test
    public void testQueryTeacherByCollege() {
        assertEquals(1, teacherDao.query(1, 1, 10).size());
    }

    @Test
    public void testQueryTeacherCount() {
        assertEquals(2, teacherDao.queryCount());
    }

    @Test
    public void testQueryTeacherCountByCollege() {
        assertEquals(1, teacherDao.queryCount(1));
    }
}
