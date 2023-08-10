package team.star.healthcodesystem.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 学生业务逻辑测试类
 *
 * @author Patrick_Star
 * @version 1.0
 */
public class StudentServiceTest {
    private final StudentService studentService = new StudentService();

    @Test
    public void testAddStudent() {
        studentService.addStudent(1L, "test", "330181200001010101", "test", "test", "test");
    }

    @Test
    public void testLogin() {
        assertEquals("test", studentService.login(1L, "0001010101").getName());
    }

    @Test
    public void testUpdateStudent() {
        studentService.updateStudent(1L, "test_update", null, null, "test", "test_new");
    }

    @Test
    public void testDeleteStudent() {
        studentService.deleteStudent(1L);
    }

    @Test
    public void testGetStudent() {
        assertEquals("test_new", studentService.queryStudent(1L).getName());
    }

    @Test
    public void testGetStudentList() {
        assertEquals(2, studentService.queryAllStudentList(1, 10).getTotal());
    }

    @Test
    public void testGetStudentListByCollege() {
        assertEquals(2, studentService.queryStudentListByCollege("test", 1, 10).getTotal());
    }

    @Test
    public void testGetStudentListByMajor() {
        assertEquals(2, studentService.queryStudentListByMajor("test", 1, 10).getTotal());
    }

    @Test
    public void testGetStudentListByClass() {
        assertEquals(0, studentService.queryStudentListByClass("test_new", 1, 10).getTotal());
    }

    @Test
    public void testGetStudentCount() {
        assertEquals(2, studentService.queryStudentAllCount());
    }

    @Test
    public void testGetStudentCountByCollege() {
        assertEquals(2, studentService.queryStudentCountByCollege("test"));
    }

    @Test
    public void testGetStudentCountByMajor() {
        assertEquals(6, studentService.queryStudentCountByMajor("软件工程"));
    }

    @Test
    public void testGetStudentCountByClass() {
        assertEquals(4, studentService.queryStudentCountByClass("软件工程2103"));
    }
}
