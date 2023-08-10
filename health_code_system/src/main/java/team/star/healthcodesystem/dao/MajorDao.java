package team.star.healthcodesystem.dao;

import team.star.healthcodesystem.config.DatabaseConnector;
import team.star.healthcodesystem.model.Major;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 专业数据访问对象类
 *
 * @author Patrick_Star
 * @version 1.5
 */
public class MajorDao {
    private static final String INSERT_SQL = "INSERT INTO major (name, college_id) VALUES (?, ?)";
    private static final String QUERY_SQL = "SELECT * FROM major WHERE id = ?";
    private static final String QUERY_BY_NAME_SQL = "SELECT * FROM major WHERE name = ?";
    private static final String PAGINATION_QUERY_ALL_SQL = "SELECT * FROM major LIMIT ?, ?";
    private static final String QUERY_COUNT_SQL = "SELECT COUNT(*) FROM major";
    private static final String PAGINATION_QUERY_BY_COLLEGE_SQL = "SELECT * FROM major WHERE college_id = ? LIMIT ?, ?";
    private static final String QUERY_COUNT_BY_COLLEGE_SQL = "SELECT COUNT(*) FROM major WHERE college_id = ?";
    private static final String UPDATE_SQL = "UPDATE major SET name = ?, college_id = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM major WHERE id = ?";

    /**
     * 从数据库中删除专业信息
     *
     * @param id 专业 id
     */
    public void delete(int id) {
         Connection conn = null;
        PreparedStatement stmt = null;
        try {
             conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(DELETE_SQL);
            stmt.setInt(1, id);
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
     * 更新专业信息
     *
     * @param major 专业对象
     */
    public void update(Major major) {
         Connection conn = null;
        PreparedStatement stmt = null;
        try {
             conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(UPDATE_SQL);
            stmt.setString(1, major.getName());
            stmt.setInt(2, major.getCollegeId());
            stmt.setInt(3, major.getId());
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
     * 向数据库中插入专业信息
     *
     * @param major 专业对象
     */
    public void insert(Major major) {
         Connection conn = null;
        PreparedStatement stmt = null;
        try {
             conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(INSERT_SQL);
            stmt.setString(1, major.getName());
            stmt.setInt(2, major.getCollegeId());
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
     * 从数据库中查询专业信息
     *
     * @param id 专业 id
     * @return 专业名称
     */
    public Major query(int id) {
         Connection conn = null;
        PreparedStatement stmt = null;
        try {
             conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(QUERY_SQL);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Major.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .collegeId(rs.getInt("college_id")).build();
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
     * 从数据库中查询所有专业信息
     *
     * @param page     页码
     * @param pageSize 页大小
     * @return 专业对象列表
     */
    public ArrayList<Major> query(int page, int pageSize) {
         Connection conn = null;
        PreparedStatement stmt = null;
        try {
             conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(PAGINATION_QUERY_ALL_SQL);
            stmt.setInt(1, (page - 1) * pageSize);
            stmt.setInt(2, pageSize);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Major> majors = new ArrayList<>();
            while (rs.next()) {
                majors.add(Major.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .collegeId(rs.getInt("college_id"))
                        .build());
            }
            return majors;
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
     * 从数据库中查询专业信息
     *
     * @param name 专业名称
     * @return 专业对象
     */
    public Major query(String name) {
         Connection conn = null;
        PreparedStatement stmt = null;
        try {
             conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(QUERY_BY_NAME_SQL);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Major.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .collegeId(rs.getInt("college_id")).build();
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
     * 从数据库中查询专业数量
     *
     * @return 专业数量
     */
    public int queryCount() {
         Connection conn = null;
        PreparedStatement stmt = null;
        try {
             conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(QUERY_COUNT_SQL);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
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
     * 从数据库中查询某学院的专业数量
     *
     * @param collegeId 学院 id
     * @return 专业数量
     */
    public int queryCount(int collegeId) {
        return ClassDao.getCount(collegeId, QUERY_COUNT_BY_COLLEGE_SQL);
    }

    /**
     * 从数据库中查询某学院的专业信息
     *
     * @param collegeId 学院 id
     * @param page      页码
     * @param pageSize  页大小
     * @return 专业对象列表
     */
    public ArrayList<Major> query(int collegeId, int page, int pageSize) {
         Connection conn = null;
        PreparedStatement stmt = null;
        try {
             conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(PAGINATION_QUERY_BY_COLLEGE_SQL);
            stmt.setInt(1, collegeId);
            stmt.setInt(2, (page - 1) * pageSize);
            stmt.setInt(3, pageSize);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Major> majors = new ArrayList<>();
            while (rs.next()) {
                majors.add(Major.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .collegeId(rs.getInt("college_id"))
                        .build());
            }
            return majors;
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
}
