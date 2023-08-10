package team.star.healthcodesystem.service;

import team.star.healthcodesystem.constant.ErrorCode;
import team.star.healthcodesystem.dao.*;
import team.star.healthcodesystem.exception.AppException;
import team.star.healthcodesystem.model.Class;
import team.star.healthcodesystem.model.College;
import team.star.healthcodesystem.model.Major;
import team.star.healthcodesystem.model.Record;

import java.sql.Date;

/**
 * 健康码记录服务
 *
 * @author Patrick_Star
 * @version 1.0
 */
public class RecordService {
    private final RecordDao recordDao = new RecordDao();
    private final StudentDao studentDao = new StudentDao();
    private final TeacherDao teacherDao = new TeacherDao();
    private final CollegeDao collegeDao = new CollegeDao();
    private final MajorDao majorDao = new MajorDao();
    private final ClassDao classDao = new ClassDao();

    /**
     * 查询某用户的健康码记录
     *
     * @param userId 用户id
     * @param date   日期
     * @return 健康码记录
     */
    public Record queryRecord(Long userId, String date) {
        if (studentDao.query(userId) == null && teacherDao.query(userId) == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        return recordDao.query(userId, date == null ? new Date(System.currentTimeMillis()) : Date.valueOf(date));
    }


    /**
     * 添加体温记录
     *
     * @param userId               用户id
     * @param phoneNum             电话号码
     * @param haveBeenWuhan        是否去过武汉
     * @param haveBeenAbroad       是否去过国外
     * @param haveBeenTouchPatient 是否接触过疑似患者
     * @param isPatient            是否是疑似患者
     * @param healthStatus         健康状态
     */
    public void addRecord(Long userId, String phoneNum, Boolean haveBeenWuhan, Boolean haveBeenAbroad,
                          Boolean haveBeenTouchPatient, Boolean isPatient, Integer healthStatus) {
        if (studentDao.query(userId) == null && teacherDao.query(userId) == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        if (recordDao.query(userId, new Date(System.currentTimeMillis())) != null) {
            throw new AppException(ErrorCode.RECORD_EXISTED);
        }
        int type = 0;
        if (haveBeenWuhan || haveBeenAbroad || healthStatus == 1) {
            type = 1;
        }
        if (haveBeenTouchPatient || isPatient || healthStatus >= 2) {
            type = 2;
        }
        recordDao.insert(Record.builder()
                .userId(userId)
                .phoneNum(phoneNum)
                .color(type == 0 ? Record.Color.GREEN : type == 1 ? Record.Color.YELLOW : Record.Color.RED)
                .build());
    }

    /**
     * 查询全校学生的健康码提交数量
     *
     * @param date 日期
     * @return 健康码提交数量
     */
    public int queryStudentCount(String date) {
        return recordDao.queryStudentCount(date == null ? new Date(System.currentTimeMillis()) : Date.valueOf(date));
    }

    /**
     * 查询全校教师的健康码提交数量
     *
     * @param date 日期
     * @return 健康码提交数量
     */
    public int queryTeacherCount(String date) {
        return recordDao.queryTeacherCount(date == null ? new Date(System.currentTimeMillis()) : Date.valueOf(date));
    }

    /**
     * 查询某学院学生的健康码提交数量
     *
     * @param date        日期
     * @param collegeName 学院名称
     * @return 健康码提交数量
     */
    public int queryStudentCountByCollege(String collegeName, String date) {
        College college = collegeDao.query(collegeName);
        if (college == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        return recordDao.queryStudentCountByCollege(date == null ? new Date(System.currentTimeMillis()) : Date.valueOf(date), college.getId());
    }

    /**
     * 查询某学院教师的健康码提交数量
     *
     * @param date        日期
     * @param collegeName 学院名称
     * @return 健康码提交数量
     */
    public int queryTeacherCountByCollege(String collegeName, String date) {
        College college = collegeDao.query(collegeName);
        if (college == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        return recordDao.queryTeacherCountByCollege(date == null ? new Date(System.currentTimeMillis()) : Date.valueOf(date), college.getId());
    }

    /**
     * 查询某班级学生的健康码提交数量
     *
     * @param date      日期
     * @param className 班级名称
     * @return 健康码提交数量
     */
    public int queryStudentCountByClass(String className, String date) {
        Class clazz = classDao.query(className);
        if (clazz == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        return recordDao.queryStudentCountByClass(date == null ? new Date(System.currentTimeMillis()) : Date.valueOf(date), clazz.getId());
    }

    /**
     * 查询某专业学生的健康码提交数量
     *
     * @param date      日期
     * @param majorName 专业名称
     * @return 健康码提交数量
     */
    public int queryStudentCountByMajor(String majorName, String date) {
        Major major = majorDao.query(majorName);
        if (major == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        return recordDao.queryStudentCountByMajor(date == null ? new Date(System.currentTimeMillis()) : Date.valueOf(date), major.getId());
    }
}