package config; // config パッケージに配置

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener  // web.xml に登録しなくても動作する
public class EnvLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        EnvConfig.load(sce.getServletContext()); // アプリ起動時に.envをロード
        System.out.println("環境変数をロードしました！");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
