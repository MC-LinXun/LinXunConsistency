package org.LinXun.Coo;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.LinXun.Coo.OutputLog.errorPrint;


public class GeFileDetect {
    public static final String SYSTEM_MESSAGE = ChatColor.RED + "[凌寻] " + ChatColor.YELLOW;
    private FileConfiguration config;
    private final JavaPlugin plugin;
    private File configUrl;

    public GeFileDetect(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public FileConfiguration CreateFileVe () {
        //配置默认路径
        Path filePathUrl = Paths.get(
                plugin.getDataFolder().getParentFile().toString(),
                "LinXun",
                    plugin.getDescription().getName()
        );
        File filesUrl = filePathUrl.toFile();
        // 目标文件路径
        configUrl = new File(filesUrl,"config.yml");

        //检查是否创建成功,无法创建 则读取默认配置
        if (!filesUrl.exists() && !filesUrl.mkdirs()) {
            defaultConfig();
            return config;
        }else if (!configUrl.exists()){
            try {
                InputStream in = plugin.getResource("config.yml");
                Files.copy(in,configUrl.toPath());
                config = YamlConfiguration.loadConfiguration(configUrl);
                return config;
            }catch (IOException e) {
                defaultConfig();
                return config;
            }
        }
        config = YamlConfiguration.loadConfiguration(configUrl);
        return config;
    }
    public void defaultConfig (){
        InputStream defaultIn = plugin.getResource("config.yml");
        config = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultIn, StandardCharsets.UTF_8));
        errorPrint(plugin.getDescription().getName()+"配置文件创建异常,自动读取默认配置(默认配置无法被改写)");
    }

    //磁盘内重载配置
    public FileConfiguration diskSave () {
        config = YamlConfiguration.loadConfiguration(configUrl);
        return config;
    }

    //内存重载配置到磁盘
    public void memorySave  () throws IOException {
        config.save(configUrl);
    }
}
