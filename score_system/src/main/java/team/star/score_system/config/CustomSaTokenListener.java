package team.star.score_system.config;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * SaToken 自定义侦听器
 *
 * @author Patrick_Star
 * @version 1.0
 */
@Component
@Slf4j
public class CustomSaTokenListener implements SaTokenListener {

    /**
     * 每次登录时触发
     */
    @Override
    public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginModel loginModel) {
        Date date = new Date();
        log.info(date + "  用户" + loginId + "   登录  " + loginModel.toString());
    }

    @Override
    public void doLogout(String s, Object o, String s1) {
    }

    @Override
    public void doKickout(String s, Object o, String s1) {
    }

    @Override
    public void doReplaced(String s, Object o, String s1) {
    }

    @Override
    public void doDisable(String s, Object o, String s1, int i, long l) {
    }

    @Override
    public void doUntieDisable(String s, Object o, String s1) {
    }

    @Override
    public void doOpenSafe(String s, String s1, String s2, long l) {
    }

    @Override
    public void doCloseSafe(String s, String s1, String s2) {
    }

    @Override
    public void doCreateSession(String s) {
    }

    @Override
    public void doLogoutSession(String s) {
    }

    @Override
    public void doRenewTimeout(String s, Object o, long l) {
    }
}
