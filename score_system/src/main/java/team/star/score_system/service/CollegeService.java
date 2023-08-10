package team.star.score_system.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import team.star.score_system.constant.ErrorCode;
import team.star.score_system.dto.PaginationDto;
import team.star.score_system.entity.College;
import team.star.score_system.exception.AppException;
import team.star.score_system.mapper.CollegeMapper;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class CollegeService {
    final CollegeMapper collegeMapper;
    public void addCollege(int id, String name) {
        if (id == 0 || name == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        if (collegeMapper.checkId(id) != 0 || collegeMapper.checkName(name) != 0) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        collegeMapper.insert(id, name);
    }

    public void deleteCollege(int id) {
        if (id == 0) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        if (collegeMapper.checkId(id) != 1) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        collegeMapper.delete(id);
    }

    public void updateCollege(int id, String name) {
        if (id == 0 || name == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        if (collegeMapper.checkId(id) != 1) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        collegeMapper.update(id, name);
    }

    public void updateCollegeName(int id, String name) {
        if (id == 0 || name == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        if (collegeMapper.checkId(id) != 1 || collegeMapper.checkName(name) != 0) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        collegeMapper.update(id, name);
    }

    public PaginationDto<College> getColleges(int page, int size) {
        if (page <= 0 || size <= 0) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        int total = collegeMapper.selectCount();
        int offset = (page - 1) * size;
        return PaginationDto.<College>builder()
                .total(total)
                .data(collegeMapper.selectAll(offset, size))
                .page(page)
                .size(size)
                .build();
    }
}
