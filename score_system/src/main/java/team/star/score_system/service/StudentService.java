package team.star.score_system.service;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;
import team.star.score_system.constant.ErrorCode;
import team.star.score_system.dto.HometownDto;
import team.star.score_system.dto.PaginationDto;
import team.star.score_system.entity.Student;
import team.star.score_system.exception.AppException;
import team.star.score_system.mapper.ClassMapper;
import team.star.score_system.mapper.CollegeMapper;
import team.star.score_system.mapper.MajorMapper;
import team.star.score_system.mapper.StudentMapper;

import java.util.ArrayList;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class StudentService {
    final StudentMapper studentMapper;
    final CollegeMapper collegeMapper;
    final MajorMapper majorMapper;
    final ClassMapper classMapper;

    public int countStudentsByHometown(String hometown) {
        return studentMapper.getCountByHometown(hometown);
    }

    public PaginationDto<Student> getStudents(int page, int size, String type, String condition) {
        int offset = (page - 1) * size;
        if ("all".equals(type)) {
            return PaginationDto.<Student>builder()
                    .data(studentMapper.selectAll(offset, size))
                    .total(studentMapper.selectCount())
                    .page(page)
                    .size(size)
                    .build();
        } else if ("college".equals(type)) {
            if (collegeMapper.checkName(condition) != 1) {
                throw new AppException(ErrorCode.PARAM_ERROR);
            }
            return PaginationDto.<Student>builder()
                    .data(studentMapper.selectByCollege(offset, size, condition))
                    .total(studentMapper.selectCountByCollege(condition))
                    .page(page)
                    .size(size)
                    .build();
        } else if ("major".equals(type)) {
            if (majorMapper.checkName(condition) != 1) {
                throw new AppException(ErrorCode.PARAM_ERROR);
            }
            return PaginationDto.<Student>builder()
                    .data(studentMapper.selectByMajor(offset, size, condition))
                    .total(studentMapper.selectCountByMajor(condition))
                    .page(page)
                    .size(size)
                    .build();
        } else {
            if (classMapper.checkName(condition) != 1) {
                throw new AppException(ErrorCode.PARAM_ERROR);
            }
            return PaginationDto.<Student>builder()
                    .data(studentMapper.selectByClass(offset, size, condition))
                    .total(studentMapper.selectCountByClass(condition))
                    .page(page)
                    .size(size)
                    .build();
        }
    }

    public Student login(long id, String password) {
        if (studentMapper.checkId(id) != 1) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        if (studentMapper.login(id, password) != 1) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        return studentMapper.selectOne(id);
    }

    public void updateStudent(long id, String name, String gender, int birthYear,
                              String hometown, String className) {
        if (studentMapper.checkId(id) != 1
                || classMapper.checkName(className) != 1) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        studentMapper.update(id, name, gender, birthYear, className, hometown);
    }

    public void addStudent(long id, String name, String gender, int birthYear,
                           String hometown, String className) {
        if (studentMapper.checkId(id) == 1
                || classMapper.checkName(className) != 1) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        studentMapper.insert(id, name, gender, birthYear, className, hometown);
    }

    public void deleteStudent(long id) {
        if (studentMapper.checkId(id) != 1) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        studentMapper.delete(id);
    }

    public void updatePassword(long id, String oldPassword, String newPassword, boolean isAdmin) {
        if (isAdmin) {
            if (studentMapper.checkId(id) != 1) {
                throw new AppException(ErrorCode.PARAM_ERROR);
            }
            studentMapper.changePassword("zjut" + id, id);
            return;
        }
        if (studentMapper.checkId(id) != 1
                || studentMapper.login(id, oldPassword) != 1) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        studentMapper.changePassword(newPassword, id);
    }

    public ArrayList<HometownDto> getHometownCounts() {
        ArrayList<String> hometownList = studentMapper.selectHometownList();
        ArrayList<HometownDto> hometownDtoList = new ArrayList<>();
        int i = 0;
        for (String hometown : hometownList) {
            hometownDtoList.add(HometownDto.builder()
                    .id(++i)
                    .hometown(hometown)
                    .count(studentMapper.getCountByHometown(hometown))
                    .build());
        }
        return hometownDtoList;
    }

    public PaginationDto<Student> getStudentsByHometown(int page, int size, String hometown) {
        int offset = (page - 1) * size;
        return PaginationDto.<Student>builder()
                .data(studentMapper.selectByHometown(offset, size, hometown))
                .total(studentMapper.getCountByHometown(hometown))
                .page(page)
                .size(size)
                .build();
    }
}

