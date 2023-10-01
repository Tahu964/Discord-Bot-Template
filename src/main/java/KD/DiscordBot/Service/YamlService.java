package KD.DiscordBot.Service;

import KD.DiscordBot.Model.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class YamlService {
    File ConfigFile;
    public Config loadConfig(){
        try{
            ConfigFile = new File("Config.yaml");
            if(ConfigFile.createNewFile()){
                System.out.println("Config.yaml not present, Creating new one");
                Config config = new Config();
                SaveConfig(config);
                return config;
            }
            else{
                ObjectMapper om = new ObjectMapper(new YAMLFactory());
                return om.readValue(ConfigFile, Config.class);
            }
        }
        catch (IOException e){
            FileReadExceptionError(e,"Something bad happened with the Config.yaml");
        }
        return null;
    }
    public int SaveConfig(Config config){
        try{
            ConfigFile = new File("Config.yaml");
            ObjectMapper om = new ObjectMapper(new YAMLFactory());
            om.writeValue(ConfigFile, config);
            System.out.println("Config.yaml updated!");
            return 0;
        }
        catch (IOException e){
            FileReadExceptionError(e,"Something bad happened with the Config.yaml");
            return -1;
        }
    }
    public void FileReadExceptionError(IOException e, String m){
        System.out.println("FILE MACHINE BROKE");
        System.out.println(m);
        System.out.println(e.getMessage());
    }
}
