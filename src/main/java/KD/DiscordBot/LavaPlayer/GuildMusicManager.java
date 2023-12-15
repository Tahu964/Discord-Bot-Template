package KD.DiscordBot.LavaPlayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.entities.Guild;

public class GuildMusicManager {
    private TrackSchedular trackSchedular;
    private AudioFowarder audioFowarder;

    public GuildMusicManager(AudioPlayerManager manager, Guild guild){
        AudioPlayer player = manager.createPlayer();
        trackSchedular = new TrackSchedular(player);
        player.addListener(trackSchedular);
        audioFowarder = new AudioFowarder(player, guild);
    }

    public TrackSchedular getTrackSchedular() {
        return trackSchedular;
    }

    public AudioFowarder getAudioFowarder() {
        return audioFowarder;
    }
}
