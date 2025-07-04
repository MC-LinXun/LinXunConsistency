package org.LinXun.Coo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import java.util.logging.Logger;

import static org.LinXun.Coo.GeFileDetect.SYSTEM_MESSAGE;


public class OutputLog {
    private Logger logger;
    boolean isLogInit = false;
    public static void errorPrint(String message) {
        // 去除颜色代码
        String strippedMessage = ChatColor.stripColor(message);

        // 计算有效显示长度（中文算2个字符，英文算1个）
        int displayWidth = calculateDisplayWidth(strippedMessage);

        // 生成边框（两侧各留3个字符空间）
        String border = ChatColor.YELLOW + repeatString(displayWidth + 10);
        // 发送到控制台
        Bukkit.getConsoleSender().sendMessage(border);
        Bukkit.getConsoleSender().sendMessage(SYSTEM_MESSAGE + ChatColor.GREEN + message);
        Bukkit.getConsoleSender().sendMessage(border);
    }

    private static int calculateDisplayWidth(String str) {
        int width = 0;
        for (char c : str.toCharArray()) {
            width += (isChinese(c) ? 2 : 1);
        }
        return width;
    }

    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    private static String repeatString(int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append("=");
        }
        return sb.toString();
    }
}
