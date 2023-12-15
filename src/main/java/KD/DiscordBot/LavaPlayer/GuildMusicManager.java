package KD.DiscordBot.LavaPlayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.entities.Guild;

public class GuildMusicManager {
    private TrackScheduler trackScheduler;
    private AudioFowarder audioFowarder;

    public GuildMusicManager(AudioPlayerManager manager, Guild guild){
        AudioPlayer player = manager.createPlayer();
        trackScheduler = new TrackScheduler(player);
        player.addListener(trackScheduler);
        audioFowarder = new AudioFowarder(player, guild);
    }

    public TrackScheduler getTrackScheduler() { return trackScheduler; }

    public AudioFowarder getAudioFowarder() {
        return audioFowarder;
    }
}
