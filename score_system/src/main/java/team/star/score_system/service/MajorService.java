package team.star.score_system.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import team.star.score_system.constant.ErrorCode;
import team.star.score_system.dto.PaginationDto;
import team.star.score_system.entity.Major;
import team.star.score_system.exception.AppException;
import team.star.score_system.mapper.CollegeMapper;
import team.star.score_system.mapper.MajorMapper;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class MajorService {
    final MajorMapper majorMapper;
    final CollegeMapper collegeMapper;

    public void addMajor(int id, String name, String collegeName) {
        if (id == 0 || name == null || collegeName == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        if (majorMapper.checkId(id) != 0
                || majorMapper.checkName(name) != 0
                || collegeMapper.checkName(collegeName) != 1) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        majorMapper.insert(id, name, collegeName);
    }

    public void deleteMajor(int id) {
        if (id == 0) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        if (majorMapper.checkId(id) != 1) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        majorMapper.delete(id);
    }

    public void updateMajor(int id, String name, String collegeName) {
        if (id == 0 || name == null || collegeName == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        if (majorMapper.checkId(id) != 1
                || collegeMapper.checkName(collegeName) != 1) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        majorMapper.update(id, name, collegeName);
    }

    public PaginationDto<Major> getMajors(int page, int size, String type, String condition) {
        int offset = (page - 1) * size;
        if ("all".equals(type)) {
            return PaginationDto.<Major>builder()
                    .data(majorMapper.selectAll(offset, size))
                    .total(majorMapper.selectCount())
                    .page(page)
                    .size(size)
                    .build();
        } else {
            if (collegeMapper.checkName(condition) != 1) {
                throw new AppException(ErrorCode.PARAM_ERROR);
            }
            return PaginationDto.<Major>builder()
                    .data(majorMapper.selectAllByCollegeName(offset, size, condition))
                    .total(majorMapper.selectCount())
                    .page(page)
                    .size(size)
                    .build();
        }
    }
}
