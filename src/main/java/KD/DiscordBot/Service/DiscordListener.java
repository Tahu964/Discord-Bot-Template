package KD.DiscordBot.Service;

import KD.DiscordBot.Exceptions.ChannelNotFoundException;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

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
            case "test":
                event.reply("Test command called!").queue();
                SendDiscordServerMessage("Here is an example message");
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
