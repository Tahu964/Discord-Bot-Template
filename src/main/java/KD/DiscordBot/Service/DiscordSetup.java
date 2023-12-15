package KD.DiscordBot.Service;

import KD.DiscordBot.Model.Config;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import javax.security.auth.login.LoginException;

public class DiscordSetup {
    static JDA jda;
    static Config config;
    public void TryDiscordCalls(){
        YamlService yamlService = new YamlService();
        config = yamlService.loadConfig("Config.yaml");
        if(config.getBotToken() == null){
            System.out.println("There is no Bot Token Provided!");
            return;
        }
        try {
            DiscordCalls();
        } catch (LoginException e) {
            System.out.println("LOGIN MACHINE BROKE");
        }
        catch (InterruptedException e) {
            System.out.println("BOT WAIT MACHINE BROKE");
        }
    }
    public void DiscordCalls() throws LoginException, InterruptedException {
        jda = JDABuilder.createDefault(config.getBotToken())
                .addEventListeners(new DiscordListener())
                .setActivity(Activity.playing("Bot is now active"))
                .build();
        jda.awaitReady();
        jda.updateCommands().addCommands(
                Commands.slash("play","A music bot experiment that really doesn't belong here, remove later plz")
                        .addOption(OptionType.STRING,"link","The URL or File to play", true),
                Commands.slash("shutdown","Shuts down the bot.")
        ).queue();
    }
}
