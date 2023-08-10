package team.star.healthcodesystem.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Patrick_Star
 * @version 1.0
 */
public class RecordServiceTest {
    private final RecordService recordService = new RecordService();

    @Test
    public void testQueryRecord() {
        assertEquals(9, recordService.queryRecord(1L, null).getId());
    }

    @Test
    public void testAddRecord() {
        recordService.addRecord(1L, "test", true, true, true, true, 1);
        assertEquals(9, recordService.queryRecord(1L, null).getId());
    }

    @Test
    public void testQueryStudentCount() {
        assertEquals(1, recordService.queryStudentCount(null));
    }

    @Test
    public void testQueryTeacherCount() {
        assertEquals(0, recordService.queryTeacherCount(null));
    }

    @Test
    public void testQueryStudentCountByCollege() {
        assertEquals(1, recordService.queryStudentCountByCollege("test", null));
    }

    @Test
    public void testQueryTeacherCountByCollege() {
        assertEquals(0, recordService.queryTeacherCountByCollege("test", null));
    }

    @Test
    public void testQueryStudentCountByClass() {
        assertEquals(1, recordService.queryStudentCountByClass("test", null));
    }
}
