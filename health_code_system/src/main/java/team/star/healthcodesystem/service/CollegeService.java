package team.star.healthcodesystem.service;

import team.star.healthcodesystem.constant.ErrorCode;
import team.star.healthcodesystem.dao.CollegeDao;
import team.star.healthcodesystem.dto.PaginationDto;
import team.star.healthcodesystem.exception.AppException;
import team.star.healthcodesystem.model.College;

/**
 * 学院服务
 *
 * @author Patrick_Star
 * @version 1.1
 */
public class CollegeService {
    private final CollegeDao collegeDao = new CollegeDao();

    /**
     * 查询所有学院列表
     *
     * @param page 页码
     * @param size 每页大小
     * @return 学院列表
     */
    public PaginationDto<College> queryCollegeList(int page, int size) {
        return PaginationDto.<College>builder()
                .data(collegeDao.query(page, size))
                .page(page)
                .size(size)
                .total(collegeDao.queryCount())
                .build();
    }

    /**
     * 查询所有学院数量
     *
     * @return 学院数量
     */
    public int queryCount() {
        return collegeDao.queryCount();
    }

    /**
     * 查询某学院
     *
     * @param collegeName 学院名称
     * @return 学院
     */
    public College queryCollege(String collegeName) {
        return collegeDao.query(collegeName);
    }

    /**
     * 查询某学院
     *
     * @param id 学院 id
     * @return 学院
     */
    public College queryCollege(int id) {
        return collegeDao.query(id);
    }

    /**
     * 添加学院
     *
     * @param name 学院名称
     */
    public void addCollege(String name) {
        College college = collegeDao.query(name);
        if (college != null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        collegeDao.insert(College.builder()
                .name(name)
                .build());
    }

    /**
     * 更新学院名称
     *
     * @param id   学院 id
     * @param name 学院名称
     */
    public void updateCollege(int id, String name) {
        College college = collegeDao.query(name);
        if (collegeDao.query(id) == null
                || college != null && college.getId() != id) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        collegeDao.update(College.builder()
                .id(id)
                .name(name)
                .build());
    }

    /**
     * 删除学院
     *
     * @param id 学院 id
     */
    public void deleteCollege(int id) {
        College college = collegeDao.query(id);
        if (college == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        collegeDao.delete(id);
    }
}
