package team.star.healthcodesystem.dao;

import org.junit.jupiter.api.Test;
import team.star.healthcodesystem.model.Record;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 记录数据访问对象测试类
 *
 * @author Patrick_Star
 * @version 1.1
 */
public class RecordDaoTest {
    private final RecordDao recordDao = new RecordDao();

    @Test
    public void testInputRecord() {
        recordDao.insert(Record.builder()
                .userId(1L)
                .phoneNum("12345678901")
                .color(Record.Color.GREEN)
                .build());
    }

    @Test
    public void testQueryRecord() {
        Record record = recordDao.query(1L, new Date(System.currentTimeMillis()));
        assertEquals(1, record.getId());
    }

    @Test
    public void testQueryRecordByUserId() {
        assertEquals(1, recordDao.query(1L, 1, 10).size());
    }

    @Test
    public void testUpdateRecord() {
        recordDao.update(Record.builder()
                .userId(1L)
                .color(Record.Color.RED)
                .createTime(new Date(System.currentTimeMillis()))
                .build());
    }

    @Test
    public void testDeleteRecord() {
        recordDao.delete(1L, new Date(System.currentTimeMillis()));
    }

    @Test
    public void testQueryCount() {
        assertEquals(1, recordDao.queryCount(1L));
    }

    @Test
    public void testQueryStudentCount() {
        assertEquals(1, recordDao.queryStudentCount(new Date(System.currentTimeMillis())));
    }

    @Test
    public void testQueryStudentCountByCollege() {
        assertEquals(1, recordDao.queryStudentCountByCollege(new Date(System.currentTimeMillis()), 1));
    }

    @Test
    public void testQueryStudentCountByClass() {
        assertEquals(1, recordDao.queryStudentCountByClass(new Date(System.currentTimeMillis()), 1));
    }

    @Test
    public void testQueryTeacherCount() {
        assertEquals(1, recordDao.queryTeacherCount(new Date(System.currentTimeMillis())));
    }

    @Test
    public void testQueryTeacherCountByCollege() {
        assertEquals(1, recordDao.queryTeacherCountByCollege(new Date(System.currentTimeMillis()), 1));
    }
}
