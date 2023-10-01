package KD.DiscordBot.Model;

import java.util.ArrayList;
import java.util.List;

public class Config {
    protected String BotToken;
    protected List<String> ChannelList;

    public Config(){
        BotToken = null;
        ChannelList = new ArrayList<>();
    }
    public Config(String BotToken, List<String> ChannelList){
        this.BotToken = BotToken;
        this.ChannelList = ChannelList;
    }
    public String getBotToken() {
        return BotToken;
    }

    public void setBotToken(String botToken) {
        BotToken = botToken;
    }

    public List<String> getChannelList() {
        return ChannelList;
    }

    public void setChannelList(List<String> channelList) {
        ChannelList = channelList;
    }

    public void addChannel(String channel){
        ChannelList.add(channel);
    }
}
