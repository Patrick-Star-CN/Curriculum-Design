package team.star.score_system.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;
import team.star.score_system.constant.ErrorCode;
import team.star.score_system.entity.Admin;
import team.star.score_system.exception.AppException;
import team.star.score_system.mapper.AdminMapper;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class AdminService {
    final AdminMapper adminMapper;

    public Admin login(Integer username, String password) {
        if (username == null || password == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        if (adminMapper.checkUsername(username) != 1) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        return adminMapper.login(username, password);
    }

    public Admin.Role getRole(Integer username) {
        if (username == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        if (adminMapper.checkUsername(username) != 1) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        return adminMapper.selectByUsername(username).getRole();
    }

    public void changePassword(Integer username, String oldPassword, String newPassword) {
        if (username == null || oldPassword == null || newPassword == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        if (adminMapper.checkUsername(username) != 1) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        if (adminMapper.login(username, oldPassword) == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        adminMapper.changePassword(username, newPassword);
    }

    public void insert(Integer id, String password, String role) {
        if (id == null || password == null || role == null || !EnumUtils.isValidEnum(Admin.Role.class, role)) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        if (adminMapper.checkUsername(id) != 0) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        adminMapper.insert(id, password, role);
    }

    public void updateRole(Integer username, String role) {
        if (username == null || role == null || !EnumUtils.isValidEnum(Admin.Role.class, role)) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        if (adminMapper.checkUsername(username) != 1) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        adminMapper.updateRole(username, role);
    }

    public void delete(Integer username) {
        if (username == null) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        if (adminMapper.checkUsername(username) != 1) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        adminMapper.delete(username);
    }
}
