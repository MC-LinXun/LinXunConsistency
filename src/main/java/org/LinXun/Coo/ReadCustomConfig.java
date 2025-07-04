package org.LinXun.Coo;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.LinXun.Coo.OutputLog.errorPrint;


public class ReadCustomConfig {
    private InputStream inFile;
    private InputStreamReader reader;
    private Path endUrl;

    /**
 * @param filename 源文件
 * @param targetpath 目标路径
 * @param plugin 插件对象
 * @return FileConfiguration 如资源文件不存在返回空的FileConfiguration
 **/
    public FileConfiguration readConfig(String filename, File targetpath, JavaPlugin plugin){
        File fileUrl = new File(targetpath.toString(), filename);
        try {
            if (!fileUrl.exists() && targetpath.mkdirs()){
                inFile = plugin.getResource(filename);
                if (inFile == null) {
                    errorPrint("配置文件不存在，检查文件名是否正确");
                    return new YamlConfiguration();
                }
                Files.copy(inFile, fileUrl.toPath());
                return YamlConfiguration.loadConfiguration(fileUrl);
            }else {
                YamlConfiguration yamlConfig = YamlConfiguration.loadConfiguration(fileUrl);
                if (yamlConfig.getKeys(false).isEmpty()) {
                    inFile = plugin.getResource(filename);
                    if (inFile == null) {
                        errorPrint("配置文件不存在，检查文件名是否正确");
                        return new YamlConfiguration();
                    }
                    reader = new InputStreamReader(inFile);
                    return YamlConfiguration.loadConfiguration(reader);
                }

            }
        }catch (IOException e) {
            errorPrint("配置文件复制异常,已使用内存配置");
            Bukkit.getConsoleSender().sendMessage(e.toString());
        }catch (SecurityException e){
            errorPrint("安全异常，可能是权限不足");
            Bukkit.getConsoleSender().sendMessage(e.toString());
        }finally {
            if (inFile != null) {
                try {
                    inFile.close();
                } catch (IOException e) {
                    errorPrint("无法释放资源,文件可能已损坏");
                    Bukkit.getConsoleSender().sendMessage(e.toString());
                }
            }
            if (reader!= null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    errorPrint("无法释放资源,文件可能已损坏");
                    Bukkit.getConsoleSender().sendMessage(e.toString());
                }
            }
        }
        return new YamlConfiguration();
    }
}
