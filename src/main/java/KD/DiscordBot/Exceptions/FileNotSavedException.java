package KD.DiscordBot.Exceptions;

public class FileNotSavedException extends Exception{
    public FileNotSavedException(){
        super("File was not saved. Something is interfering with saving");
    }
}
