package team.star.healthcodesystem.dao;

import team.star.healthcodesystem.config.DatabaseConnector;
import team.star.healthcodesystem.model.College;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 学院数据访问对象类
 *
 * @author Patrick_Star
 * @version 1.3
 */
public class CollegeDao {
    private static final String INSERT_SQL = "INSERT INTO college (name) VALUES (?)";
    private static final String QUERY_SQL = "SELECT * FROM college WHERE id = ?";
    private static final String QUERY_BY_NAME_SQL = "SELECT * FROM college WHERE name = ?";
    private static final String PAGINATION_QUERY_ALL_SQL = "SELECT * FROM college LIMIT ?, ?";
    private static final String QUERY_COUNT_SQL = "SELECT COUNT(*) FROM college";
    private static final String UPDATE_SQL = "UPDATE college SET name = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM college WHERE id = ?";

    /**
     * 从数据库中删除学院信息
     *
     * @param id 学院 id
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
     * 更新学院信息
     *
     * @param college 学院对象
     */
    public void update(College college) {
         Connection conn = null;
        PreparedStatement stmt = null;
        try {
             conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(UPDATE_SQL);
            stmt.setString(1, college.getName());
            stmt.setLong(2, college.getId());
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
     * 从数据库中查询学院信息
     *
     * @param id 学院 id
     * @return 学院名称
     */
    public College query(long id) {
         Connection conn = null;
        PreparedStatement stmt = null;
        try {
             conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(QUERY_SQL);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return College.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
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
     * 从数据库中查询所有学院信息
     *
     * @param page 页码
     * @param size 每页大小
     * @return 学院对象列表
     */
    public ArrayList<College> query(int page, int size) {
         Connection conn = null;
        PreparedStatement stmt = null;
        try {
             conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(PAGINATION_QUERY_ALL_SQL);
            stmt.setInt(1, (page - 1) * size);
            stmt.setInt(2, size);
            ResultSet rs = stmt.executeQuery();
            ArrayList<College> colleges = new ArrayList<>();
            while (rs.next()) {
                colleges.add(College.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build());
            }
            return colleges;
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
     * 从数据库中查询学院信息
     *
     * @param name 学院名称
     * @return 学院对象
     */
    public College query(String name) {
         Connection conn = null;
        PreparedStatement stmt = null;
        try {
             conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(QUERY_BY_NAME_SQL);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return College.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
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
     * 向数据库中插入学院信息
     *
     * @param college 学院对象
     */
    public void insert(College college) {
         Connection conn = null;
        PreparedStatement stmt = null;
        try {
             conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(INSERT_SQL);
            stmt.setString(1, college.getName());
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
     * 从数据库中查询学院数量
     *
     * @return 学院数量
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
}
