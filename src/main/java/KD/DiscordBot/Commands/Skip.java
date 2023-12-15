package KD.DiscordBot.Commands;

import KD.DiscordBot.LavaPlayer.GuildMusicManager;
import KD.DiscordBot.LavaPlayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Skip {
    public void SkipCommand(SlashCommandInteractionEvent event){
        Member member = event.getMember();
        GuildVoiceState memberVoiceState = member.getVoiceState();

        if(!memberVoiceState.inAudioChannel()) {
            event.reply("You're not in a voice channel").queue();
            return;
        }

        Member self = event.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();

        if(!selfVoiceState.inAudioChannel()) {
            event.reply("I am not being used at the moment").queue();
            return;
        }

        if(selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
            event.reply("You need to be in a valid voice channel").queue();
            return;
        }

        GuildMusicManager guildMusicManager = PlayerManager.get().getGuildMusicManager(event.getGuild());
        guildMusicManager.getTrackScheduler().getPlayer().stopTrack();
        event.reply("Skipped").queue();
    }
}
