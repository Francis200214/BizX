package com.biz.core.banner;

import com.biz.core.spring.BizXSpringBeanDefinitionRegistrar;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

/**
 * BizBanner 打印
 *
 * @author francis
 * @create 2024-06-23 17:33
 **/
@Slf4j
public final class BizBanner {

    public static void printBanner() {
        String[] colors = {
                "\033[0;31m", // RED
                "\033[0;32m", // GREEN
                "\033[0;33m", // YELLOW
                "\033[0;34m", // BLUE
                "\033[0;35m", // PURPLE
                "\033[0;36m", // CYAN
                "\033[0;37m"  // WHITE
        };

        Random random = new Random();
        String version = getVersion();

        String banner = "\n" +
                "  ____  _    __  __\n" +
                " | __ )(_)___\\ \\/ /\n" +
                " |  _ \\| |/ _ \\\\  / \n" +
                " | |_) | |  __//  \\ \n" +
                " |____/|_|\\___/_/\\_\\\n" +
                "                   \n" +
                "   Framework: BizX\n" +
                "   Version: " + version + "\n";

        StringBuilder coloredBanner = new StringBuilder();
        for (char c : banner.toCharArray()) {
            if (c != ' ' && c != '\n') {
                String color = colors[random.nextInt(colors.length)];
                coloredBanner.append(color).append(c).append("\033[0m");
            } else {
                coloredBanner.append(c);
            }
        }

        System.out.println(coloredBanner);
    }

    /**
     * 获取框架版本号
     */
    private static String getVersion() {
        Properties properties = new Properties();
        try (InputStream input = BizXSpringBeanDefinitionRegistrar.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                log.warn("Sorry, unable to find application.properties");
                return "Unknown";
            }
            properties.load(input);
            return properties.getProperty("framework.version");
        } catch (IOException ex) {
            log.error("Error reading application.properties", ex);
            return "Unknown";
        }
    }

}
