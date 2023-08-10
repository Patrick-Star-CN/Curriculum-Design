package team.star.healthcodesystem.dao;

import team.star.healthcodesystem.config.DatabaseConnector;
import team.star.healthcodesystem.model.Record;

import java.sql.*;
import java.util.ArrayList;

/**
 * 记录数据访问对象类
 *
 * @author Patrick_Star
 * @version 1.0
 */
public class RecordDao {
    private static final String INSERT_SQL = "INSERT INTO record (user_id, phone_num, color, create_time) VALUES (?, ?, ?, ?)";
    private static final String SELECT_SQL = "SELECT * FROM record WHERE user_id = ? AND create_time = ?";
    private static final String PAGINATION_QUERY_ALL_SQL = "SELECT * FROM record WHERE user_id = ? LIMIT ?, ?";
    private static final String QUERY_COUNT_SQL = "SELECT COUNT(*) FROM record WHERE user_id = ?";
    private static final String QUERY_ALL_STUDENT_COUNT_SQL = "SELECT COUNT(*) FROM record WHERE create_time = ? AND user_id IN (SELECT id FROM student)";
    private static final String QUERY_STUDENT_COUNT_BY_COLLEGE_SQL = "SELECT COUNT(*) FROM record WHERE create_time = ? AND user_id IN (SELECT id FROM student WHERE college_id = ?)";
    private static final String QUERY_STUDENT_COUNT_BY_MAJOR_SQL = "SELECT COUNT(*) FROM record WHERE create_time = ? AND user_id IN (SELECT id FROM student WHERE major_id = ?)";
    private static final String QUERY_STUDENT_COUNT_BY_CLASS_SQL = "SELECT COUNT(*) FROM record WHERE create_time = ? AND user_id IN (SELECT id FROM student WHERE class_id = ?)";
    private static final String QUERY_ALL_TEACHER_COUNT_SQL = "SELECT COUNT(*) FROM record WHERE create_time = ? AND user_id IN (SELECT id FROM teacher)";
    private static final String QUERY_TEACHER_COUNT_BY_COLLEGE_SQL = "SELECT COUNT(*) FROM record WHERE create_time = ? AND user_id IN (SELECT id FROM teacher WHERE college_id = ?)";
    private static final String UPDATE_SQL = "UPDATE record SET color = ? WHERE user_id = ? AND create_time = ?";
    private static final String DELETE_SQL = "DELETE FROM record WHERE user_id = ? AND create_time = ?";

    /**
     * 从数据库中删除记录信息
     *
     * @param userId     用户 id
     * @param createTime 创建时间
     */
    public void delete(long userId, Date createTime) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(DELETE_SQL);
            stmt.setLong(1, userId);
            stmt.setDate(2, createTime);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 向数据库中插入记录信息
     *
     * @param record 记录对象
     */
    public void insert(Record record) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(INSERT_SQL);
            stmt.setLong(1, record.getUserId());
            stmt.setString(2, record.getPhoneNum());
            stmt.setString(3, record.getColor().toString());
            stmt.setDate(4, new Date(System.currentTimeMillis()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 更新记录信息
     *
     * @param record 记录对象
     */
    public void update(Record record) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(UPDATE_SQL);
            stmt.setString(1, record.getColor().toString());
            stmt.setLong(2, record.getUserId());
            stmt.setDate(3, record.getCreateTime());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从数据库中查询记录信息
     *
     * @param userId     用户 id
     * @param createTime 创建时间
     * @return 记录对象
     */
    public Record query(long userId, Date createTime) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(SELECT_SQL);
            stmt.setLong(1, userId);
            stmt.setDate(2, createTime);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Record.builder()
                        .id(rs.getInt("id"))
                        .userId(rs.getLong("user_id"))
                        .phoneNum(rs.getString("phone_num"))
                        .color(Record.Color.valueOf(rs.getString("color")))
                        .createTime(rs.getDate("create_time"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 从数据库中分页查询记录信息
     *
     * @param userId 用户 id
     * @return 记录对象
     */
    public ArrayList<Record> query(long userId, int page, int size) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(PAGINATION_QUERY_ALL_SQL);
            stmt.setLong(1, userId);
            stmt.setInt(2, (page - 1) * size);
            stmt.setInt(3, size);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Record> records = new ArrayList<>();
            while (rs.next()) {
                records.add(Record.builder()
                        .id(rs.getInt("id"))
                        .userId(rs.getLong("user_id"))
                        .color(Record.Color.valueOf(rs.getString("color")))
                        .createTime(rs.getDate("create_time"))
                        .phoneNum(rs.getString("phone_num"))
                        .build());
            }
            return records;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 从数据库中查询某用户打卡记录数量
     *
     * @param userId 用户 id
     * @return 记录对象
     */
    public int queryCount(long userId) {
        return getCount(null, QUERY_COUNT_SQL, userId);
    }

    /**
     * 从数据库中查询所有已打卡的学生数量
     *
     * @param createTime 创建时间
     * @return 记录对象
     */
    public int queryStudentCount(Date createTime) {
        return getCount(createTime, QUERY_ALL_STUDENT_COUNT_SQL, null);
    }

    /**
     * 从数据库中查询某个学院已打卡的学生数量
     *
     * @param createTime 创建时间
     * @param collegeId  学院 id
     * @return 记录对象
     */
    public int queryStudentCountByCollege(Date createTime, long collegeId) {
        return getCount(createTime, QUERY_STUDENT_COUNT_BY_COLLEGE_SQL, collegeId);
    }

    /**
     * 从数据库中查询某个专业已打卡的学生数量
     *
     * @param createTime 创建时间
     * @param majorId    专业 id
     * @return 记录对象
     */
    public int queryStudentCountByMajor(Date createTime, long majorId) {
        return getCount(createTime, QUERY_STUDENT_COUNT_BY_MAJOR_SQL, majorId);
    }

    /**
     * 从数据库中查询所有已打卡的教师数量
     *
     * @param createTime 创建时间
     * @return 记录对象
     */
    public int queryTeacherCount(Date createTime) {
        return getCount(createTime, QUERY_ALL_TEACHER_COUNT_SQL, null);
    }

    /**
     * 从数据库中查询某个学院已打卡的教师数量
     *
     * @param createTime 创建时间
     * @param collegeId  学院 id
     * @return 记录对象
     */
    public int queryTeacherCountByCollege(Date createTime, long collegeId) {
        return getCount(createTime, QUERY_TEACHER_COUNT_BY_COLLEGE_SQL, collegeId);
    }

    /**
     * 从数据库中查询某班级所有已打卡的学生数量
     *
     * @param createTime 创建时间
     * @return 记录对象
     */
    public int queryStudentCountByClass(Date createTime, long classId) {
        return getCount(createTime, QUERY_STUDENT_COUNT_BY_CLASS_SQL, classId);
    }

    private int getCount(Date createTime, String queryAllTeacherCountSql, Long id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(queryAllTeacherCountSql);
            if (createTime != null) {
                stmt.setDate(1, createTime);
            }
            if (id != null) {
                stmt.setLong(createTime == null ? 1 : 2, id);
            }
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
