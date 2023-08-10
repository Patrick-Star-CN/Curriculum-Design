package team.star.healthcodesystem.service;

import team.star.healthcodesystem.constant.ErrorCode;
import team.star.healthcodesystem.dao.ClassDao;
import team.star.healthcodesystem.dao.CollegeDao;
import team.star.healthcodesystem.dao.MajorDao;
import team.star.healthcodesystem.dao.StudentDao;
import team.star.healthcodesystem.dto.PaginationDto;
import team.star.healthcodesystem.dto.StudentDto;
import team.star.healthcodesystem.exception.AppException;
import team.star.healthcodesystem.model.Class;
import team.star.healthcodesystem.model.College;
import team.star.healthcodesystem.model.Major;
import team.star.healthcodesystem.model.Student;

import java.util.ArrayList;

/**
 * 学生服务
 *
 * @author Patrick_Star
 * @version 1.1
 */
public class StudentService {
    private final StudentDao studentDao = new StudentDao();
    private final CollegeDao collegeDao = new CollegeDao();
    private final MajorDao majorDao = new MajorDao();
    private final ClassDao classDao = new ClassDao();

    /**
     * 学生注册
     *
     * @param id          学号
     * @param name        姓名
     * @param iid         身份证号
     * @param collegeName 学院名称
     * @param majorName   专业名称
     * @param className   班级名称
     */
    public void addStudent(long id, String name, String iid, String collegeName, String majorName, String className) {
        if (studentDao.query(id) != null) {
            return;
        }
        College college = collegeDao.query(collegeName);
        Major major = majorDao.query(majorName);
        Class clazz = classDao.query(className);
        if (college == null || major == null || clazz == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        studentDao.insert(Student.builder()
                .id(id)
                .name(name)
                .iid(iid)
                .collegeId(college.getId())
                .majorId(major.getId())
                .classId(clazz.getId())
                .build());
    }

    /**
     * 学生登录
     *
     * @param id  学号
     * @param iid 身份证后八位
     * @return 学生信息
     */
    public Student login(long id, String iid) {
        Student student = studentDao.query(id);
        if (student == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        if (iid.length() != 8) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        if (!student.getIid().endsWith(iid)) {
            throw new AppException(ErrorCode.PASSWORD_ERROR);
        }
        return student;
    }

    /**
     * 学生修改信息
     *
     * @param id          学号
     * @param name        姓名
     * @param iid         身份证号
     * @param collegeName 学院名称
     * @param majorName   专业名称
     * @param className   班级名称
     */
    public void updateStudent(long id, String name, String iid, String collegeName, String majorName, String className) {
        Student student = studentDao.query(id);
        if (student == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        College college = collegeName != null ? collegeDao.query(collegeName) : null;
        Major major = majorName != null ? majorDao.query(majorName) : null;
        Class clazz = className != null ? classDao.query(className) : null;
        if ((collegeName != null && college == null)
                || (majorName != null && major == null)
                || (className != null && clazz == null)) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        studentDao.update(Student.builder()
                .id(id)
                .name(name != null ? name : student.getName())
                .iid(iid != null ? iid : student.getIid())
                .collegeId(collegeName != null ? college.getId() : student.getCollegeId())
                .majorId(majorName != null ? major.getId() : student.getMajorId())
                .classId(className != null ? clazz.getId() : student.getClassId())
                .build());
    }

    /**
     * 学生注销
     *
     * @param id 学号
     */
    public void deleteStudent(long id) {
        Student student = studentDao.query(id);
        if (student == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        studentDao.delete(id);
    }

    /**
     * 学生查询
     *
     * @param id 学号
     * @return 学生信息
     */
    public Student queryStudent(long id) {
        Student student = studentDao.query(id);
        if (student == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        return student;
    }

    /**
     * 分页查询学生列表
     *
     * @param page 页码
     * @param size 每页大小
     * @return 所有学生信息
     */
    public PaginationDto<StudentDto> queryAllStudentList(int page, int size) {
        ArrayList<Student> students = studentDao.query(page, size);
        return getDto(students, page, size, studentDao.queryCount());
    }

    /**
     * 查询所有学生数量
     *
     * @return 学生数量
     */
    public int queryStudentAllCount() {
        return studentDao.queryCount();
    }

    /**
     * 分页查询某学院的学生列表
     *
     * @param collegeName 学院名称
     * @param page        页码
     * @param size        每页大小
     * @return 学院所有学生信息
     */
    public PaginationDto<StudentDto> queryStudentListByCollege(String collegeName, int page, int size) {
        College college = collegeDao.query(collegeName);
        if (college == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        ArrayList<Student> students = studentDao.query(college.getId(), page, size);
        return getDto(students, page, size, studentDao.queryCountByCollege(college.getId()));
    }

    /**
     * 分页查询某学院的学生数量
     *
     * @param collegeName 学院名称
     * @return 学生数量
     */
    public int queryStudentCountByCollege(String collegeName) {
        College college = collegeDao.query(collegeName);
        if (college == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        return studentDao.queryCountByCollege(college.getId());
    }

    /**
     * 分页查询某专业的学生列表
     *
     * @param majorName 专业名称
     * @param page      页码
     * @param size      每页大小
     * @return 专业所有学生信息
     */
    public PaginationDto<StudentDto> queryStudentListByMajor(String majorName, int page, int size) {
        Major major = majorDao.query(majorName);
        if (major == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        ArrayList<Student> students = studentDao.queryByMajor(major.getId(), page, size);
        return getDto(students, page, size, studentDao.queryCountByMajor(major.getId()));
    }

    /**
     * 分页查询某专业的学生数量
     *
     * @param majorName 专业名称
     * @return 学生数量
     */
    public int queryStudentCountByMajor(String majorName) {
        Major major = majorDao.query(majorName);
        if (major == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        return studentDao.queryCountByMajor(major.getId());
    }

    /**
     * 分页查询某班级的学生列表
     *
     * @param className 班级名称
     * @param page      页码
     * @param size      每页大小
     * @return 班级所有学生信息
     */
    public PaginationDto<StudentDto> queryStudentListByClass(String className, int page, int size) {
        Class clazz = classDao.query(className);
        if (clazz == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        ArrayList<Student> students = studentDao.queryByClass(clazz.getId(), page, size);
        return getDto(students, page, size, studentDao.queryCountByClass(clazz.getId()));
    }

    /**
     * 分页查询某班级的学生数量
     *
     * @param className 班级名称
     * @return 学生数量
     */
    public int queryStudentCountByClass(String className) {
        Class clazz = classDao.query(className);
        if (clazz == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        return studentDao.queryCountByClass(clazz.getId());
    }

    private PaginationDto<StudentDto> getDto(ArrayList<Student> students, int page, int size, int total) {
        ArrayList<StudentDto> studentDtos = new ArrayList<>();
        for (Student student : students) {
            studentDtos.add(StudentDto.builder()
                    .id(student.getId())
                    .name(student.getName())
                    .iid(student.getIid())
                    .collegeName(collegeDao.query(student.getCollegeId()).getName())
                    .majorName(majorDao.query(student.getMajorId()).getName())
                    .className(classDao.query(student.getClassId()).getName())
                    .build());
        }
        return PaginationDto.<StudentDto>builder()
                .data(studentDtos)
                .page(page)
                .total(total)
                .size(size)
                .build();
    }
}
