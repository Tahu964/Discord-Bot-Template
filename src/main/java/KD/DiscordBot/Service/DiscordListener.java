package KD.DiscordBot.Service;

import KD.DiscordBot.Commands.*;
import KD.DiscordBot.Exceptions.ChannelNotFoundException;
import KD.DiscordBot.LavaPlayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.net.URI;
import java.net.URISyntaxException;

public class DiscordListener extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event){
        System.out.println("Bot ready for action!");
    }
    @Override
    public void onShutdown(ShutdownEvent event){
        System.out.println("Bot Shutting down...");
    }
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        switch(event.getName()){
            case "play":
                Play play = new Play();
                play.PlayCommand(event);
                break;
            case "nowplaying":
                NowPlaying nowPlaying = new NowPlaying();
                nowPlaying.NowPlayingCommand(event);
                break;
            case "queue":
                Queue queue = new Queue();
                queue.QueueCommand(event);
                break;
            case "repeat":
                Repeat repeat = new Repeat();
                repeat.RepeatCommand(event);
                break;
            case "skip":
                Skip skip = new Skip();
                skip.SkipCommand(event);
                break;
            case "stop":
                Stop stop = new Stop();
                stop.StopCommand(event);
                break;
            case "disconnect":
                Disconnect disconnect = new Disconnect();
                disconnect.DisconnectCommand(event);
                break;
            case "shutdown":
                if(event.getUser().getId().equals("441682190321844224")){
                    event.reply("Shutdown command called").queue();
                    DiscordSetup.jda.shutdown();
                }
                else{
                    event.reply("Um excuse me, what do you think you're doing?").queue();
                }
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
}
