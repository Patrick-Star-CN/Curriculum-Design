package team.star.healthcodesystem.service;

import team.star.healthcodesystem.constant.ErrorCode;
import team.star.healthcodesystem.dao.CollegeDao;
import team.star.healthcodesystem.dao.TeacherDao;
import team.star.healthcodesystem.dto.PaginationDto;
import team.star.healthcodesystem.dto.TeacherDto;
import team.star.healthcodesystem.exception.AppException;
import team.star.healthcodesystem.model.College;
import team.star.healthcodesystem.model.Teacher;

import java.util.ArrayList;

/**
 * 教师服务
 *
 * @author Patrick_Star
 * @version 1.1
 */
public class TeacherService {
    private final TeacherDao teacherDao = new TeacherDao();
    private final CollegeDao collegeDao = new CollegeDao();

    /**
     * 教师注册
     *
     * @param id   教师工号
     * @param name 姓名
     * @param iid  身份证号
     * @param type 教师类型
     */
    public void addTeacher(long id, String name, String iid, Teacher.Type type, String collegeName) {
        if (teacherDao.query(id) != null) {
            return;
        }
        College college = collegeDao.query(collegeName);
        if (college == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        teacherDao.insert(Teacher.builder()
                .id(id)
                .name(name)
                .iid(iid)
                .type(type)
                .collegeId(college.getId())
                .password(null)
                .build());
    }

    /**
     * 教师登录
     *
     * @param id       教师工号
     * @param password 密码
     * @return 教师信息
     */
    public Teacher login(long id, String password) {
        Teacher teacher = teacherDao.query(id);
        if (teacher == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        if (!teacher.getPassword().equals(password)) {
            throw new AppException(ErrorCode.PASSWORD_ERROR);
        }
        return teacher;
    }

    /**
     * 教师通过身份证号登录
     *
     * @param id  教师工号
     * @param iid 教师身份证后八位
     * @return 教师信息
     */
    public Teacher loginByIdentity(long id, String iid) {
        Teacher teacher = teacherDao.query(id);
        if (teacher == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        if (iid.length() != 8) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        if (!teacher.getIid().endsWith(iid)) {
            throw new AppException(ErrorCode.PASSWORD_ERROR);
        }
        return teacher;
    }

    /**
     * 教师修改密码
     *
     * @param id              教师工号
     * @param oldPassword     旧密码
     * @param newPassword     新密码
     * @param isAdministrator 是否为管理员
     */
    public void changePassword(long id, String oldPassword, String newPassword, boolean isAdministrator) {
        Teacher teacher = teacherDao.query(id);
        if (teacher == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        if (!isAdministrator && !teacher.getPassword().equals(oldPassword)) {
            throw new AppException(ErrorCode.PASSWORD_ERROR);
        }
        teacherDao.update(Teacher.builder()
                .id(teacher.getId())
                .name(teacher.getName())
                .password(newPassword)
                .collegeId(teacher.getCollegeId())
                .iid(teacher.getIid())
                .type(teacher.getType()).build());
    }

    /**
     * 教师修改信息
     *
     * @param id          教师工号
     * @param name        姓名
     * @param collegeName 学院名称
     * @param iid         身份证号
     * @param type        教师类型
     */
    public void changeInfo(long id, String name, String collegeName, String iid, Teacher.Type type) {
        Teacher teacher = teacherDao.query(id);
        if (teacher == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        College college = collegeDao.query(collegeName);
        if (college == null) {
            System.out.println(collegeName);
            System.out.println(college);
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        teacherDao.update(Teacher.builder()
                .id(teacher.getId())
                .name(name != null ? name : teacher.getName())
                .password(teacher.getPassword())
                .collegeId(collegeName != null ? college.getId() : teacher.getCollegeId())
                .iid(iid != null ? iid : teacher.getIid())
                .type(type != null ? type : teacher.getType()).build());
    }

    /**
     * 教师注销
     *
     * @param id 教师工号
     */
    public void deleteTeacher(long id) {
        Teacher teacher = teacherDao.query(id);
        if (teacher == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        teacherDao.delete(id);
    }

    /**
     * 教师查询
     *
     * @param id 教师工号
     * @return 教师信息
     */
    public Teacher queryTeacher(long id) {
        Teacher teacher = teacherDao.query(id);
        if (teacher == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        return teacher;
    }

    /**
     * 分页查询教师列表
     *
     * @return 教师信息
     */
    public PaginationDto<TeacherDto> queryAllTeachers(int page, int size) {
        ArrayList<Teacher> teachers = teacherDao.query(page, size);
        return getDto(teachers, page, size, teacherDao.queryCount());
    }

    /**
     * 分页查询所有教师数量
     *
     * @return 教师数量
     */
    public int queryAllTeachersCount() {
        return teacherDao.queryCount();
    }

    /**
     * 分页查询某学院的教师列表
     *
     * @param collegeName 学院名称
     * @return 教师信息
     */
    public PaginationDto<TeacherDto> queryTeachersByCollege(String collegeName, int page, int size) {
        College college = collegeDao.query(collegeName);
        if (college == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        ArrayList<Teacher> teachers = teacherDao.query(college.getId(), page, size);
        return getDto(teachers, page, size, teacherDao.queryCount(college.getId()));
    }

    /**
     * 分页查询某学院的所有教师数量
     *
     * @param collegeName 学院名称
     * @return 教师数量
     */
    public int queryTeachersByCollegeCount(String collegeName) {
        College college = collegeDao.query(collegeName);
        if (college == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        return teacherDao.queryCount(college.getId());
    }

    private PaginationDto<TeacherDto> getDto(ArrayList<Teacher> teachers, int page, int size, int total) {
        ArrayList<TeacherDto> teacherDtos = new ArrayList<>();
        for (Teacher teacher : teachers) {
            teacherDtos.add(TeacherDto.builder()
                    .id(teacher.getId())
                    .name(teacher.getName())
                    .iid(teacher.getIid())
                    .collegeName(collegeDao.query(teacher.getCollegeId()).getName())
                    .password(teacher.getPassword())
                    .type(teacher.getType().name())
                    .build());
        }
        return PaginationDto.<TeacherDto>builder()
                .data(teacherDtos)
                .page(page)
                .total(total)
                .size(size)
                .build();
    }
}
