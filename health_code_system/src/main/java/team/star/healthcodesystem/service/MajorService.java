package team.star.healthcodesystem.service;

import team.star.healthcodesystem.constant.ErrorCode;
import team.star.healthcodesystem.dao.CollegeDao;
import team.star.healthcodesystem.dao.MajorDao;
import team.star.healthcodesystem.dto.MajorDto;
import team.star.healthcodesystem.dto.PaginationDto;
import team.star.healthcodesystem.exception.AppException;
import team.star.healthcodesystem.model.College;
import team.star.healthcodesystem.model.Major;

import java.util.ArrayList;

/**
 * @author Patrick_Star
 * @version 1.1
 */
public class MajorService {
    private final MajorDao majorDao = new MajorDao();
    private final CollegeDao collegeDao = new CollegeDao();

    /**
     * 添加专业
     *
     * @param name        专业名称
     * @param collegeName 学院名称
     */
    public void addMajor(String name, String collegeName) {
        Major major = majorDao.query(collegeName);
        if (major != null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        College college = collegeDao.query(collegeName);
        if (college == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        majorDao.insert(Major.builder()
                .name(name)
                .collegeId(college.getId())
                .build());
    }

    /**
     * 删除专业
     *
     * @param id 专业id
     */
    public void deleteMajor(int id) {
        Major major = majorDao.query(id);
        if (major == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        majorDao.delete(id);
    }

    /**
     * 修改专业
     *
     * @param id          专业id
     * @param name        专业名称
     * @param collegeName 学院名称
     */
    public void updateMajor(int id, String name, String collegeName) {
        Major major = majorDao.query(id);
        Major aMajor = majorDao.query(name);
        College college = collegeName != null ? collegeDao.query(collegeName) : null;
        if (aMajor != null && aMajor.getId() != id
                || major == null
                || collegeName != null && college == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        majorDao.update(Major.builder()
                .id(id)
                .collegeId(collegeName == null ? major.getCollegeId() : college.getId())
                .name(name)
                .build());
    }

    /**
     * 查询专业
     *
     * @param name 专业名称
     * @return 专业
     */
    public Major queryMajor(String name) {
        return majorDao.query(name);
    }

    /**
     * 查询专业
     *
     * @param id 专业id
     * @return 专业
     */
    public Major queryMajor(int id) {
        return majorDao.query(id);
    }

    /**
     * 分页查询所有专业列表
     *
     * @param page 页码
     * @param size 每页大小
     * @return 专业列表
     */
    public PaginationDto<MajorDto> queryAllMajor(int page, int size) {
        ArrayList<Major> majors = majorDao.query(page, size);
        return getDto(majors, page, size, majorDao.queryCount());
    }

    /**
     * 分页查询某学院的专业列表
     *
     * @param collegeName 学院名称
     * @param page        页码
     * @param size        每页大小
     * @return 专业列表
     */
    public PaginationDto<MajorDto> queryMajorListByCollege(String collegeName, int page, int size) {
        College college = collegeDao.query(collegeName);
        if (college == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        ArrayList<Major> majors = majorDao.query(college.getId(), page, size);
        return getDto(majors, page, size, majorDao.queryCount(college.getId()));
    }

    private PaginationDto<MajorDto> getDto(ArrayList<Major> majors, int page, int size, int total) {
        ArrayList<MajorDto> majorDtos = new ArrayList<>();
        for (Major major : majors) {
            majorDtos.add(MajorDto.builder()
                    .id(major.getId())
                    .name(major.getName())
                    .collegeName(collegeDao.query(major.getCollegeId()).getName())
                    .build());
        }
        return PaginationDto.<MajorDto>builder()
                .data(majorDtos)
                .page(page)
                .total(total)
                .size(size)
                .build();
    }
}
