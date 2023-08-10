package team.star.healthcodesystem.service;

import org.junit.jupiter.api.Test;
import team.star.healthcodesystem.model.Teacher;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Patrick_Star
 * @version 1.0
 */
public class TeacherServiceTest {
    private final TeacherService teacherService = new TeacherService();

    @Test
    public void testQueryTeacher() {
        assertEquals(202103150102L, teacherService.queryTeacher(202103150102L).getId());
    }

    @Test
    public void testAddByName() {
        teacherService.addTeacher(202103150101L, "张三", "330181200001011010", Teacher.Type.SUPER_ADMIN, "计算机学院");
        assertEquals(202103150101L, teacherService.queryTeacher(202103150101L).getId());
    }

    @Test
    public void testChangePassword() {
        teacherService.changePassword(202103150101L, "test", "test", false);
        assertEquals("test", teacherService.queryTeacher(202103150101L).getPassword());
    }

    @Test
    public void testLogin() {
         assertEquals(202103150101L, teacherService.login(202103150101L, "test").getId());
    }

    @Test
    public void testLoginByIdentity() {
        assertEquals(202103150101L, teacherService.loginByIdentity(202103150101L, "01010101").getId());
    }

    @Test
    public void testChangeTeacherInfo() {
        teacherService.changeInfo(202103150101L, "test", "test", "330181200001011010", Teacher.Type.COLLEGE_ADMIN);
        assertEquals(Teacher.Type.COLLEGE_ADMIN, teacherService.queryTeacher(202103150101L).getType());
    }

    @Test
    public void testDeleteTeacher() {
        teacherService.deleteTeacher(202103150102L);
    }

    @Test
    public void testQueryTeacherList() {
        assertEquals(2, teacherService.queryAllTeachers(1, 10).getTotal());
    }

    @Test
    public void testQueryTeacherListByCollege() {
        assertEquals(1, teacherService.queryTeachersByCollege("test", 1, 10).getTotal());
    }

    @Test
    public void testQueryAllTeachersCount() {
        assertEquals(2, teacherService.queryAllTeachersCount());
    }

    @Test
    public void testQueryTeachersCountByCollege() {
        assertEquals(1, teacherService.queryTeachersByCollegeCount("test"));
    }

}
