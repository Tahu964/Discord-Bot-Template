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
            case "shutdown":
                event.reply("Shutdown command called").queue();
                DiscordSetup.jda.shutdown();
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
