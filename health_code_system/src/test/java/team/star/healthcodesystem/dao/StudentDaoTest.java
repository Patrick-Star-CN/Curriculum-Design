package team.star.healthcodesystem.dao;

import org.junit.jupiter.api.Test;
import team.star.healthcodesystem.model.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 学生数据访问对象测试类
 *
 * @author Patrick_Star
 * @version 1.3
 */
public class StudentDaoTest {
    private final StudentDao studentDao = new StudentDao();
    @Test
    public void testInputStudent() {
        studentDao.insert(Student.builder()
                .id(202103150101L)
                .name("test")
                .collegeId(1)
                .iid("330181200001010101")
                .majorId(1)
                .classId(1)
                .build());
    }

    @Test
    public void testUpdateStudent() {
        studentDao.update(Student.builder()
                .id(202103150101L)
                .name("test_update")
                .collegeId(1)
                .iid("330181200001010101")
                .majorId(1)
                .classId(1)
                .build());
    }

    @Test
    public void testQueryStudentById() {
        Student student = studentDao.query(202103150101L);
        assertEquals("test_update", student.getName());
    }

    @Test
    public void testDeleteStudent() {
        studentDao.delete(202103150101L);
    }

    @Test
    public void testQueryAllStudent() {
        assertEquals(10, studentDao.query(1, 10).size());
    }

    @Test
    public void testQueryStudentByCollege() {
        assertEquals(8, studentDao.query(2, 1, 10).size());
    }

    @Test
    public void testQueryStudentCount() {
        assertEquals(1, studentDao.queryCount());
    }

    @Test
    public void testQueryStudentCountByCollege() {
        assertEquals(8, studentDao.queryCountByCollege(2));
    }

    @Test
    public void testQueryStudentCountByMajor() {
        assertEquals(6, studentDao.queryCountByMajor(2));
    }

    @Test
    public void testQueryStudentCountByClass() {
        assertEquals(1, studentDao.queryCountByClass(1));
    }

    @Test
    public void testQueryStudentByMajor() {
        assertEquals(6, studentDao.queryByMajor(2, 1, 10).size());
    }

    @Test
    public void testQueryStudentByClass() {
        assertEquals(4, studentDao.queryByClass(2, 1, 10).size());
    }
}
