package KD.DiscordBot.Commands;

import KD.DiscordBot.LavaPlayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.net.URI;
import java.net.URISyntaxException;

public class Play {
    public void PlayCommand(SlashCommandInteractionEvent event){
        Member member = event.getMember();
        GuildVoiceState voiceState = member.getVoiceState();
        if(!voiceState.inAudioChannel()){
            event.reply("You're not in a voice channel").queue();
            return;
        }
        Member self = event.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();
        if(!selfVoiceState.inAudioChannel()){
            event.getGuild().getAudioManager().openAudioConnection(voiceState.getChannel());
        } else {
            if(selfVoiceState.getChannel() != voiceState.getChannel()){
                event.reply("You need to be in a valid voice channel").queue();
                return;
            }
        }
        String name = event.getOption("link").getAsString();
        try {
            new URI(name);
        } catch (URISyntaxException e) {
            name = "ytsearch:" + name;
        }
        PlayerManager pm = PlayerManager.get();
        pm.play(event.getGuild(), name);
        event.reply("Playing").queue();
    }
}
