package team.star.healthcodesystem.dao;

import org.jetbrains.annotations.NotNull;
import team.star.healthcodesystem.config.DatabaseConnector;
import team.star.healthcodesystem.model.Class;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 班级数据访问对象类
 *
 * @author Patrick_Star
 * @version 1.3
 */
public class ClassDao {
    private static final String INSERT_SQL = "INSERT INTO class (name, major_id) VALUES (?, ?)";
    private static final String QUERY_SQL = "SELECT * FROM class WHERE id = ?";
    private static final String QUERY_BY_NAME_SQL = "SELECT * FROM class WHERE name = ?";
    private static final String PAGINATION_QUERY_ALL_SQL = "SELECT * FROM class LIMIT ?, ?";
    private static final String PAGINATION_QUERY_BY_MAJOR_SQL = "SELECT * FROM class WHERE major_id = ? LIMIT ?, ?";
    private static final String QUERY_COUNT_SQL = "SELECT COUNT(*) FROM class";
    private static final String QUERY_COUNT_BY_MAJOR_SQL = "SELECT COUNT(*) FROM class WHERE major_id = ?";
    private static final String UPDATE_SQL = "UPDATE class SET name = ?, major_id = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM class WHERE id = ?";

    /**
     * 从数据库中删除班级信息
     *
     * @param id 班级 id
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
     * 更新班级信息
     *
     * @param clazz 班级对象
     */
    public void update(Class clazz) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(UPDATE_SQL);
            stmt.setString(1, clazz.getName());
            stmt.setInt(2, clazz.getMajorId());
            stmt.setInt(3, clazz.getId());
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
     * 从数据库中查询班级信息
     *
     * @param id 班级 id
     * @return 班级名称
     */
    public Class query(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(QUERY_SQL);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Class.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .majorId(rs.getInt("major_id"))
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
     * 从数据库中分页查询所有班级信息
     *
     * @param page 页码
     * @param size 每页大小
     * @return 班级对象
     */
    public ArrayList<Class> query(int page, int size) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(PAGINATION_QUERY_ALL_SQL);
            stmt.setInt(1, (page - 1) * size);
            stmt.setInt(2, size);
            return getClasses(stmt);
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
     * 从数据库中分页查询某专业的班级信息
     *
     * @param majorId 专业 id
     * @param page    页码
     * @param size    每页大小
     * @return 班级对象
     */
    public ArrayList<Class> query(int majorId, int page, int size) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(PAGINATION_QUERY_BY_MAJOR_SQL);
            stmt.setInt(1, majorId);
            stmt.setInt(2, (page - 1) * size);
            stmt.setInt(3, size);
            return getClasses(stmt);
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
     * 从数据库中查询班级信息
     *
     * @param name 班级名称
     * @return 班级对象
     */
    public Class query(String name) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(QUERY_BY_NAME_SQL);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Class.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .majorId(rs.getInt("major_id"))
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
     * 向数据库中插入班级信息
     *
     * @param clazz 班级对象
     */
    public void insert(Class clazz) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(INSERT_SQL);
            stmt.setString(1, clazz.getName());
            stmt.setInt(2, clazz.getMajorId());
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
     * 从数据库中查询班级总数
     *
     * @return 班级总数
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
     * 从数据库中查询某专业的班级总数
     *
     * @param majorId 专业 id
     * @return 班级总数
     */
    public int queryCount(int majorId) {
        return getCount(majorId, QUERY_COUNT_BY_MAJOR_SQL);
    }

    static int getCount(int id, String queryCountSql) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement(queryCountSql);
            stmt.setInt(1, id);
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

    @NotNull
    private ArrayList<Class> getClasses(PreparedStatement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery();
        ArrayList<Class> classes = new ArrayList<>();
        while (rs.next()) {
            classes.add(Class.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .majorId(rs.getInt("major_id"))
                    .build());
        }
        return classes;
    }
}
