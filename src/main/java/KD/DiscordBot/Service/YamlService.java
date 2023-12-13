package KD.DiscordBot.Service;

import KD.DiscordBot.Model.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
//TODO: For future me, figure out how to make yaml reader take in any class to reduce redundant code
public class YamlService {
    File File;
    public Config loadConfig(String fileName){
        try{
            File = new File(fileName);
            if(File.createNewFile()){
                System.out.println(fileName + " not present, Creating new one");
                Config config = new Config();
                SaveConfig(fileName, config);
                return config;
            }
            else{
                ObjectMapper om = new ObjectMapper(new YAMLFactory());
                return om.readValue(File, Config.class);
            }
        }
        catch (IOException e){
            FileReadExceptionError(e,"Something bad happened with the " + fileName);
        }
        return null;
    }
    public int SaveConfig(String fileName, Object obj){
        try{
            File = new File(fileName);
            ObjectMapper om = new ObjectMapper(new YAMLFactory());
            om.writeValue(File, obj);
            System.out.println(fileName + " updated!");
            return 0;
        }
        catch (IOException e){
            FileReadExceptionError(e,"Something bad happened with the " + fileName);
            return -1;
        }
    }
    public void FileReadExceptionError(IOException e, String m){
        System.out.println("FILE MACHINE BROKE");
        System.out.println(m);
        System.out.println(e.getMessage());
    }
}
