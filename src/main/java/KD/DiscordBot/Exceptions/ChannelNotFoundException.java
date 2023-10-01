package KD.DiscordBot.Exceptions;

public class ChannelNotFoundException extends Exception{
    public ChannelNotFoundException()
    {
        super("Cannot find channel, returned null. Please check channel ID is correct");
    }
}
