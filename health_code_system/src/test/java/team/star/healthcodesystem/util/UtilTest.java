package team.star.healthcodesystem.util;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 工具类的测试类
 *
 * @author Patrick_Star
 * @version 1.0
 */
public class UtilTest {
    @Test
    void testCreateQRCode() {
        QRCode.createQR("test", "test.png", Color.GREEN);
    }

    @Test
    void testJWT() {
        String jwt = JWT.createJWT("test");
        assertEquals("test", JWT.verifyJWT(jwt));
    }

    @Test
    void testYamlReader() {
        Map<String, Object> config = YamlReader.readConfFromYaml("config.yaml");
        Map<String, String> mysql = (Map<String, String>) config.get("mysql");
        assertEquals("com.mysql.cj.jdbc.Driver", mysql.get("driver"));
    }
}
