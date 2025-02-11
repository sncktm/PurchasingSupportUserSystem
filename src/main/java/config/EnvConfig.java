package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;

public class EnvConfig {
    private static Properties properties = new Properties();

    public static void load(ServletContext context) {
        String path = context.getRealPath("/WEB-INF/.env"); // WEB-INF の絶対パスを取得
        try (FileInputStream fis = new FileInputStream(path)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
