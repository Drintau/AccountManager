package drintau.accountmanager.desktop;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.net.URI;

@Slf4j
public final class DesktopUtil {

    public static void openBrowser(String url) {
        URI uri = URI.create(url);
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(uri);
        } catch (Exception e) {
            log.error("打开系统浏览器失败,请手动复制网址到浏览器访问", e);
        }
    }

}
