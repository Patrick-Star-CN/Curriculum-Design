package team.star.healthcodesystem.dao;

import org.jetbrains.annotations.NotNull;
import team.star.healthcodesystem.config.DatabaseConnector;
import team.star.healthcodesystem.model.Student;

import java.sql.*;
import java.util.ArrayList;

/**
 * 学生数据访问对象
 *
 * @author Patrick_Star
 * @version 1.4
 */
public class StudentDao {
    private static final String INPUT_SQL = "INSERT INTO student(id, name, college_id, iid, major_id, class_id) VALUES (?,?,?,?,?,?)";
    private static final String QUERY_SQL = "SELECT * FROM student WHERE id = ?";
    private static final String PAGINATION_QUERY_BY_COLLEGE_SQL = "SELECT * FROM student WHERE college_id = ? LIMIT ?, ?";
    private static final String PAGINATION_QUERY_BY_MAJOR_SQL = "SELECT * FROM student WHERE major_id = ? LIMIT ?, ?";
    private static final String PAGINATION_QUERY_BY_CLASS_SQL = "SELECT * FROM student WHERE class_id = ? LIMIT ?, ?";
    private static final String PAGINATION_QUERY_ALL_SQL = "SELECT * FROM student LIMIT ?, ?";
    private static final String QUERY_ALL_COUNT_SQL = "SELECT COUNT(*) FROM student";
    private static final String QUERY_COUNT_BY_COLLEGE_SQL = "SELECT COUNT(*) FROM student WHERE college_id = ?";
    private static final String QUERY_COUNT_BY_MAJOR_SQL = "SELECT COUNT(*) FROM student WHERE major_id = ?";
    private static final String QUERY_COUNT_BY_CLASS_SQL = "SELECT COUNT(*) FROM student WHERE class_id = ?";
    private static final String UPDATE_SQL = "UPDATE student SET name = ?, college_id = ?, iid = ?, major_id = ?, class_id = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM student WHERE id = ?";

    /**
     * 从数据库中删除学生信息
     *
     * @param id 学生学号
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
     * 更新学生信息
     *
     * @param student 学生对象
     */
    public void update(Student student) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(UPDATE_SQL);
            stmt.setString(1, student.getName());
            stmt.setInt(2, student.getCollegeId());
            stmt.setString(3, student.getIid());
            stmt.setInt(4, student.getMajorId());
            stmt.setInt(5, student.getClassId());
            stmt.setLong(6, student.getId());
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
     * 从数据库中查询学生信息
     *
     * @param id 学生学号
     * @return 学生对象
     */
    public Student query(long id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(QUERY_SQL);
            stmt.setLong(1, id);
            return getStudent(stmt);
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
    }

    /**
     * 从数据库中分页查询所有学生信息
     *
     * @param page 页码
     * @param size 每页大小
     * @return 学生对象
     */
    public ArrayList<Student> query(int page, int size) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(PAGINATION_QUERY_ALL_SQL);
            stmt.setInt(1, (page - 1) * size);
            stmt.setInt(2, size);
            return getStudents(stmt);
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
    }

    /**
     * 从数据库中查询学生总数
     *
     * @return 学生总数
     */
    public int queryCount() {
        return getStudentCount(null, QUERY_ALL_COUNT_SQL);
    }

    /**
     * 从数据库中查询同一学院的学生总数
     *
     * @param collegeId 学院id
     * @return 学生总数
     */
    public int queryCountByCollege(int collegeId) {
        return getStudentCount(collegeId, QUERY_COUNT_BY_COLLEGE_SQL);
    }

    /**
     * 从数据库中查询同一专业的学生总数
     *
     * @param majorId 专业id
     * @return 学生总数
     */
    public int queryCountByMajor(int majorId) {
        return getStudentCount(majorId, QUERY_COUNT_BY_MAJOR_SQL);
    }

    /**
     * 从数据库中查询同一班级的学生总数
     *
     * @param classId 班级id
     * @return 学生总数
     */
    public int queryCountByClass(int classId) {
        return getStudentCount(classId, QUERY_COUNT_BY_CLASS_SQL);
    }

    /**
     * 从数据库中分页查询同一学院的所有学生信息
     *
     * @param collegeId 学院id
     * @param page      页码
     * @param size      每页大小
     * @return 学生对象
     */
    public ArrayList<Student> query(int collegeId, int page, int size) {
        return getStudents(collegeId, page, size, PAGINATION_QUERY_BY_COLLEGE_SQL);
    }

    /**
     * 从数据库中分页查询同一专业的所有学生信息
     *
     * @param majorId 专业id
     * @param page    页码
     * @param size    每页大小
     * @return 学生对象
     */
    public ArrayList<Student> queryByMajor(int majorId, int page, int size) {
        return getStudents(majorId, page, size, PAGINATION_QUERY_BY_MAJOR_SQL);
    }

    /**
     * 从数据库中分页查询同一班级的所有学生信息
     *
     * @param classId 班级id
     * @param page    页码
     * @param size    每页大小
     * @return 学生对象
     */
    public ArrayList<Student> queryByClass(int classId, int page, int size) {
        return getStudents(classId, page, size, PAGINATION_QUERY_BY_CLASS_SQL);
    }

    @NotNull
    private ArrayList<Student> getStudents(int id, int page, int size, String sql) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setInt(2, (page - 1) * size);
            stmt.setInt(3, size);
            return getStudents(stmt);
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
    }

    /**
     * 向数据库中插入学生信息
     *
     * @param student 学生对象
     */
    public void insert(Student student) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(INPUT_SQL);
            stmt.setLong(1, student.getId());
            stmt.setString(2, student.getName());
            stmt.setInt(3, student.getCollegeId());
            stmt.setString(4, student.getIid());
            stmt.setInt(5, student.getMajorId());
            stmt.setInt(6, student.getClassId());
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

    private Student getStudent(PreparedStatement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return Student.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .collegeId(rs.getInt("college_id"))
                    .iid(rs.getString("iid"))
                    .majorId(rs.getInt("major_id"))
                    .classId(rs.getInt("class_id"))
                    .build();
        }
        return null;
    }

    private ArrayList<Student> getStudents(PreparedStatement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery();
        ArrayList<Student> students = new ArrayList<>();
        while (rs.next()) {
            students.add(Student.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .collegeId(rs.getInt("college_id"))
                    .iid(rs.getString("iid"))
                    .majorId(rs.getInt("major_id"))
                    .classId(rs.getInt("class_id"))
                    .build());
        }
        return students;
    }

    private int getStudentCount(Integer majorId, String queryCountByMajorSql) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(queryCountByMajorSql);
            if (majorId != null) {
                stmt.setInt(1, majorId);
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
