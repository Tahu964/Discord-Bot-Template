package KD.DiscordBot;

import KD.DiscordBot.Service.DiscordSetup;

public class Main {
    public static void main(String[] args) {
        DiscordSetup setup = new DiscordSetup();
        setup.TryDiscordCalls();
    }
}