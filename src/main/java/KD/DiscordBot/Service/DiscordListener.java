package KD.DiscordBot.Service;

import KD.DiscordBot.Exceptions.ChannelNotFoundException;
import KD.DiscordBot.LavaPlayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
//https://github.com/relaxingleg/Tutorial-Bot/blob/master/src/main/java/com/relaxingleg/commands/music/Play.java
public class DiscordListener extends ListenerAdapter {
    DeathTimer Timer;
    @Override
    public void onReady(ReadyEvent event){
        System.out.println("Bot ready for action!");
        Timer = new DeathTimer();
    }
    @Override
    public void onShutdown(ShutdownEvent event){
        System.out.println("Bot Shutting down...");
    }
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        switch(event.getName()){
            case "why":
                event.reply("¯\\_(ツ)_/¯").queue();
                break;
            case "shutdown":
                if(event.getUser().getId().equals("441682190321844224")){
                    event.reply("Shutdown command called").queue();
                    DiscordSetup.jda.shutdown();
                }
                else{
                    event.reply("OI YOU CAN'T STOP WHAT IS ALREADY IN MOTION").queue();
                }
                break;
            case "countdown":
                event.reply(GetTimeDiff()).queue();
                break;
            case "play":
                Member member = event.getMember();
                GuildVoiceState voiceState = member.getVoiceState();
                if(!voiceState.inAudioChannel()){
                    event.reply("You're not in a voice channel").queue();
                    break;
                }
                Member self = event.getGuild().getSelfMember();
                GuildVoiceState selfVoiceState = self.getVoiceState();
                if(!selfVoiceState.inAudioChannel()){
                    event.getGuild().getAudioManager().openAudioConnection(voiceState.getChannel());
                } else {
                    if(selfVoiceState.getChannel() != voiceState.getChannel()){
                        event.reply("You need to be in a valid voice channel").queue();
                        break;
                    }
                }
                String name = event.getOption("link").getAsString();
                try {
                    new URI(name);
                } catch (URISyntaxException e) {
                    name = "ytsearch:" + name;
                }
                PlayerManager pm = PlayerManager.get();
                event.reply("Playing ").queue();
                pm.play(event.getGuild(), name);
                break;
            default:
                event.reply("That is not a command. Try again.").queue();
        }
    }
    public void SendDiscordServerMessage(String message) {
        TextChannel channel;
        for (String s : DiscordSetup.config.getChannelList()) {
            try{
                channel = DiscordSetup.jda.getTextChannelById(s);
                if(channel != null) {
                    channel.sendMessage(message).queue();
                }
                else{ throw new ChannelNotFoundException(); }
            }
            catch(ChannelNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public void SendDiscordServerPhoto(File F) {
        TextChannel channel;
        for (String s : DiscordSetup.config.getChannelList()) {
            try{
                channel = DiscordSetup.jda.getTextChannelById(s);
                if(channel != null) {
                    channel.sendFiles(FileUpload.fromData(F)).queue();
                }
                else{ throw new ChannelNotFoundException(); }
            }
            catch(ChannelNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public String GetTimeDiff(){
        Duration duration = Duration.between(LocalDateTime.now(), Timer.ldt);
        long DD = duration.toDays();
        long HH = duration.toHoursPart();
        long MM = duration.toMinutesPart();
        long SS = duration.toSecondsPart();
        return String.format("%2d days, %2d hours, %2d minutes, %2d seconds left", DD, HH, MM, SS);
    }
    public class DeathTimer {
        java.util.Timer deathClock;
        LocalDateTime ldt;
        public DeathTimer(){
            deathClock = new Timer(true);
            TimerTask end = new theEnd();
            TimerTask Three = new ThreeDays();
            TimerTask Two = new TwoDays();
            TimerTask One = new OneDay();
            ldt = LocalDateTime.of(2024,3,21,0,0,0);
            LocalDateTime hour72 = ldt.minusHours(72);
            LocalDateTime hour48 = ldt.minusHours(48);
            LocalDateTime hour24 = ldt.minusHours(24);

            ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
            ZonedDateTime zhour72 = hour72.atZone(ZoneId.systemDefault());
            ZonedDateTime zhour48 = hour48.atZone(ZoneId.systemDefault());
            ZonedDateTime zhour24 = hour24.atZone(ZoneId.systemDefault());
            Date date = Date.from(zdt.toInstant());
            Date dhour72 = Date.from(zhour72.toInstant());
            Date dhour48 = Date.from(zhour48.toInstant());
            Date dhour24 = Date.from(zhour24.toInstant());

            deathClock.schedule(end, date);
            deathClock.schedule(Three, dhour72);
            deathClock.schedule(Two, dhour48);
            deathClock.schedule(One, dhour24);
        }

    }
    class theEnd extends TimerTask{
        public void run(){
            SendDiscordServerMessage("The end has arrived, you all should be dead if you aren't already");
        }
    }
    class ThreeDays extends TimerTask{
        public void run(){
            File f = new File("src/main/resources/72hours.jpg");
            SendDiscordServerPhoto(f);
        }
    }
    class TwoDays extends TimerTask{
        public void run(){
            File f = new File("src/main/resources/48hours.jpg");
            SendDiscordServerPhoto(f);
        }
    }
    class OneDay extends TimerTask{
        public void run(){
            File f = new File("src/main/resources/24hours.jpg");
            SendDiscordServerPhoto(f);
        }
    }
}
