package team.star.healthcodesystem.dao;

import org.jetbrains.annotations.NotNull;
import team.star.healthcodesystem.config.DatabaseConnector;
import team.star.healthcodesystem.model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 教师数据访问对象
 *
 * @author Patrick_Star
 * @version 1.3
 */
public class TeacherDao {
    private static final String INSERT_SQL = "INSERT INTO teacher(id, name, iid, password, type, college_id) VALUES (?,?,?,?,?,?)";
    private static final String QUERY_SQL = "SELECT * FROM teacher WHERE id = ?";
    private static final String PAGINATION_QUERY_BY_COLLEGE_SQL = "SELECT * FROM teacher WHERE college_id = ? LIMIT ?, ?";
    private static final String PAGINATION_QUERY_ALL_SQL = "SELECT * FROM teacher LIMIT ?, ?";
    private static final String QUERY_ALL_COUNT_SQL = "SELECT COUNT(*) FROM teacher";
    private static final String QUERY_COUNT_BY_COLLEGE_SQL = "SELECT COUNT(*) FROM teacher WHERE college_id = ?";
    private static final String UPDATE_SQL = "UPDATE teacher SET name = ?, iid = ?, password = ?, type = ?, college_id = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM teacher WHERE id = ?";

    /**
     * 从数据库中删除教师信息
     *
     * @param id 教师工号
     */
    public void delete(long id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(DELETE_SQL);
            stmt.setLong(1, id);
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
     * 更新教师信息
     *
     * @param teacher 教师对象
     */
    public void update(Teacher teacher) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(UPDATE_SQL);
            stmt.setString(1, teacher.getName());
            stmt.setString(2, teacher.getIid());
            stmt.setString(3, teacher.getPassword());
            stmt.setString(4, teacher.getType().toString());
            stmt.setInt(5, teacher.getCollegeId());
            stmt.setLong(6, teacher.getId());
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
     * 从数据库中查询教师信息
     *
     * @param id 教师工号
     * @return 教师对象
     */
    public Teacher query(long id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(QUERY_SQL);
            stmt.setLong(1, id);
            return getTeacher(stmt);
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
     * 从数据库中查询所有教师信息
     *
     * @param page     页码
     * @param pageSize 每页大小
     * @return 教师对象
     */
    public ArrayList<Teacher> query(int page, int pageSize) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(PAGINATION_QUERY_ALL_SQL);
            stmt.setInt(1, (page - 1) * pageSize);
            stmt.setInt(2, pageSize);
            return getTeachers(stmt);
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
     * 从数据库中查询某学院所有教师信息
     *
     * @param collegeId 学院编号
     * @param page      页码
     * @param pageSize  每页大小
     * @return 教师对象
     */
    public ArrayList<Teacher> query(int collegeId, int page, int pageSize) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(PAGINATION_QUERY_BY_COLLEGE_SQL);
            stmt.setInt(1, collegeId);
            stmt.setInt(2, (page - 1) * pageSize);
            stmt.setInt(3, pageSize);
            return getTeachers(stmt);
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
     * 从数据库中查询教师总数
     *
     * @return 教师总数
     */
    public int queryCount() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(QUERY_ALL_COUNT_SQL);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
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
        return 0;
    }

    /**
     * 从数据库中查询某学院教师总数
     *
     * @param collegeId 学院编号
     * @return 教师总数
     */
    public int queryCount(int collegeId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(QUERY_COUNT_BY_COLLEGE_SQL);
            stmt.setInt(1, collegeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
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
        return 0;
    }

    @NotNull
    private ArrayList<Teacher> getTeachers(PreparedStatement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery();
        ArrayList<Teacher> teachers = new ArrayList<>();
        while (rs.next()) {
            teachers.add(Teacher.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .iid(rs.getString("iid"))
                    .password(rs.getString("password"))
                    .type(Teacher.Type.valueOf(rs.getString("type")))
                    .collegeId(rs.getInt("college_id"))
                    .build());
        }
        return teachers;
    }

    /**
     * 向数据库中插入教师信息
     *
     * @param teacher 教师对象
     */
    public void insert(Teacher teacher) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(INSERT_SQL);
            stmt.setLong(1, teacher.getId());
            stmt.setString(2, teacher.getName());
            stmt.setString(3, teacher.getIid());
            stmt.setString(4, teacher.getPassword());
            stmt.setString(5, teacher.getType().toString());
            stmt.setInt(6, teacher.getCollegeId());
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

    private Teacher getTeacher(PreparedStatement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return Teacher.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .iid(rs.getString("iid"))
                    .password(rs.getString("password"))
                    .type(Teacher.Type.valueOf(rs.getString("type")))
                    .collegeId(rs.getInt("college_id"))
                    .build();
        }
        return null;
    }
}
