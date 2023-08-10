package team.star.healthcodesystem.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Patrick_Star
 * @version 1.0
 */
public class MajorServiceTest {
    private final MajorService majorService = new MajorService();

    @Test
    public void testQueryMajorList() {
        assertEquals(2, majorService.queryAllMajor(1, 10).getTotal());
    }

    @Test
    public void testAddMajor() {
        majorService.addMajor("test", "test");
        assertEquals(2, majorService.queryAllMajor(1, 10).getTotal());
    }

    @Test
    public void testDeleteMajor() {
        majorService.deleteMajor(1);
        assertEquals(1, majorService.queryAllMajor(1, 10).getTotal());
    }

    @Test
    public void testUpdateMajor() {
        majorService.updateMajor(5, "test", "test");
        assertEquals(2, majorService.queryAllMajor(1, 10).getTotal());
    }

    @Test
    public void testQueryMajor() {
        assertEquals("test", majorService.queryMajor(5).getName());
    }

    @Test
    public void testQueryMajorByName() {
        assertEquals(5, majorService.queryMajor("test").getId());
    }

    @Test
    public void testQueryMajorByCollege() {
        assertEquals(5, majorService.queryMajorListByCollege("test", 1, 1).getData().get(0).getId());
    }
}
