package team.star.healthcodesystem.service;

import team.star.healthcodesystem.constant.ErrorCode;
import team.star.healthcodesystem.dao.ClassDao;
import team.star.healthcodesystem.dao.CollegeDao;
import team.star.healthcodesystem.dao.MajorDao;
import team.star.healthcodesystem.dto.ClassDto;
import team.star.healthcodesystem.dto.MajorDto;
import team.star.healthcodesystem.dto.PaginationDto;
import team.star.healthcodesystem.exception.AppException;
import team.star.healthcodesystem.model.Class;
import team.star.healthcodesystem.model.College;
import team.star.healthcodesystem.model.Major;

import java.util.ArrayList;

/**
 * 班级服务
 *
 * @author Patrick_Star
 * @version 1.1
 */
public class ClassService {
    private final ClassDao classDao = new ClassDao();
    private final MajorDao majorDao = new MajorDao();

    /**
     * 分页查询班级列表
     *
     * @param page 页码
     * @param size 每页大小
     * @return 班级列表
     */
    public PaginationDto<ClassDto> queryClassList(int page, int size) {
        ArrayList<Class> clazz = classDao.query(page, size);
        return getDto(clazz, page, size, queryCount());
    }

    /**
     * 查询所有班级数量
     *
     * @return 班级数量
     */
    public int queryCount() {
        return classDao.queryCount();
    }

    /**
     * 分页查询某专业的班级列表
     *
     * @param majorName 专业名称
     * @param page        页码
     * @param size        每页大小
     * @return 班级列表
     */
    public PaginationDto<ClassDto> queryClassListByMajor(String majorName, int page, int size) {
        Major major = majorDao.query(majorName);
        if (major == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        ArrayList<Class> classes = classDao.query(major.getId(), page, size);
        return getDto(classes, page, size, queryCountByMajor(majorName));
    }

    /**
     * 查询某专业的班级数量
     *
     * @param majorName 学院名称
     * @return 班级数量
     */
    public int queryCountByMajor(String majorName) {
        Major major = majorDao.query(majorName);
        if (major == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        return classDao.queryCount(major.getId());
    }

    /**
     * 添加班级
     *
     * @param majorName 专业名称
     * @param name      班级名称
     */
    public void addClass(String name, String majorName) {
        if (classDao.query(name) != null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        Major major = majorDao.query(majorName);
        if (major == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        classDao.insert(Class.builder()
                .name(name)
                .majorId(major.getId())
                .build());
    }

    /**
     * 删除班级
     *
     * @param id 班级名称
     */
    public void deleteClass(int id) {
        if (classDao.query(id) == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        classDao.delete(id);
    }

    /**
     * 修改班级名称
     *
     * @param id        班级名称
     * @param name      班级名称
     * @param majorName 学院名称
     */
    public void updateClass(int id, String name, String majorName) {
        Class clazz = classDao.query(id);
        Class aClass = classDao.query(name);
        Major major = majorName != null ? majorDao.query(majorName) : null;
        if (clazz == null
                || aClass != null && aClass.getId() != id
                || majorName != null && major == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        classDao.update(Class.builder()
                .id(id)
                .name(name)
                .majorId(majorName == null ? clazz.getMajorId() : major.getId())
                .build());
    }

    /**
     * 查询班级
     *
     * @param id 班级名称
     */
    public Class queryClass(int id) {
        return classDao.query(id);
    }

    /**
     * 查询班级
     *
     * @param name 班级名称
     */
    public Class queryClass(String name) {
        return classDao.query(name);
    }

    private PaginationDto<ClassDto> getDto(ArrayList<Class> classes, int page, int size, int total) {
        ArrayList<ClassDto> classDtos = new ArrayList<>();
        for (Class clazz : classes) {
            classDtos.add(ClassDto.builder()
                    .id(clazz.getId())
                    .name(clazz.getName())
                    .majorName(majorDao.query(clazz.getMajorId()).getName())
                    .build());
        }
        return PaginationDto.<ClassDto>builder()
                .data(classDtos)
                .page(page)
                .total(total)
                .size(size)
                .build();
    }
}
