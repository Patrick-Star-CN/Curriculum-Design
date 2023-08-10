package team.star.healthcodesystem.util;

import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Slf4j
public class YamlReader {
    /**
     * 直接读取 yaml 配置文件
     * @param confName 配置文件名称
     * @return yaml 的 InputStream
     * */
    public static Map<String,Object> readConfFromYaml(String confName){
        try {
            return new Yaml().load(YamlReader.class.getClassLoader().getResourceAsStream(confName));
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
