package team.star.healthcodesystem.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 班级业务逻辑测试类
 *
 * @author Patrick_Star
 * @version 1.0
 */
public class ClassServiceTest {
    private final ClassService classService = new ClassService();

    @Test
    public void testGetAllClassesList() {
        assertEquals(1, classService.queryClassList(1, 10).getTotal());
    }

    @Test
    public void testGetClassesListByCollegeId() {
        assertEquals(1, classService.queryClassListByMajor("test", 1, 10).getTotal());
    }

    @Test
    public void testGetClassesCount() {
        assertEquals(1, classService.queryCount());
    }

    @Test
    public void testGetClassesCountByCollegeId() {
        assertEquals(1, classService.queryCountByMajor("test"));
    }

    @Test
    public void testAddClass() {
        classService.addClass("test_tmp", "test");
    }

    @Test
    public void testDeleteClass() {
        classService.deleteClass(3);
    }

    @Test
    public void testUpdateClass() {
        classService.updateClass(3, "test_ak", "test");
    }

    @Test
    public void testGetClassById() {
        assertEquals("test_ak", classService.queryClass(3).getName());
    }

    @Test
    public void testGetClassByName() {
        assertEquals(3, classService.queryClass("test_ak").getId());
    }
}
