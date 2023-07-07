package plugin.functions;

import com.mongodb.client.model.Filters;
import mindustry.gen.Call;
import mindustry.gen.Player;
import org.bson.Document;
import plugin.utils.MenuHandler;
import useful.Bundle;

import java.util.Date;

import static plugin.ConfigJson.discordurl;
import static plugin.Ploogin.playerCollection;

public class Other {

    public static void welcomeMenu(Player player){
        String title = "\uE86B Welcome!";
        String description = "[orange]Welcome to our server!\n\n" +
                "[red]<[orange]Rules[red]>\n" +
                "[#f]\uE815 [orange]Do not grief or sabotage your team.\n" +
                "[#f]\uE815 [orange]Do not build/write any NSFW or offensive content.\n" +
                "[#f]\uE815 [orange]Do not try lag the server using lag machines or similar stuff.\n" +
                "[green]\uE800 [orange]Use common sense, do not be toxic/mean to others.\n\n" +
                "[orange]Write /help to see all commands that are available on server.\n" +
                "[blue]\uE80D Also make sure to join our discord.";
        String button1 = "Close";
        String button2 = "[blue]\uE80D Join our discord!";
        Call.menu(player.con, MenuHandler.welcomeMenu, title, description, new String[][]{{button1}, {button2}});
    }
    public static void kickIfBanned(Player player){
        Document user = playerCollection.find(Filters.eq("uuid", player.uuid())).first();
        if (user == null){
            return;
        }
        long lastBan = user.getLong("lastBan");
        Date date = new Date();
        if (lastBan > date.getTime()) {
            String timeUntilUnban = Bundle.formatDuration(lastBan - date.getTime());
            player.con.kick("[red]You have been banned!\n\n" +"[white]Duration: " + timeUntilUnban + " until unban\n\nIf you think this is a mistake, make sure to appeal ban in our discord: " + discordurl, 0);
        }
    }

}