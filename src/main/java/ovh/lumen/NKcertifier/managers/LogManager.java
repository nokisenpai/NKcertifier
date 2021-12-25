package ovh.lumen.NKcertifier.managers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import ovh.lumen.NKcertifier.data.NKData;
import ovh.lumen.NKcertifier.utils.NKLogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class LogManager
{
	private LogManager() {}

	public static void log(int level, Player player)
	{
		File logFile = new File(((Plugin) NKData.PLUGIN).getDataFolder() + "/logs.txt");

		try
		{
			BufferedWriter output;
			output = new BufferedWriter(new FileWriter(logFile, true));

			LocalDateTime myDateObj = LocalDateTime.now();
			DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			String formattedDate = myDateObj.format(myFormatObj);

			NKLogger.warn(ChatColor.DARK_RED + "[LEVEL " + level + "] Staff member '" + player.getName() + "' was suspected hacked.");
			output.append("[" + formattedDate + "][LEVEL " + level + "] Staff member '" + player.getName() + "' was suspected hacked.");
			output.newLine();

			output.append("Ip address : " + player.getAddress().getAddress().toString());
			output.newLine();

			List<String> list = CertificateManager.getOtherClientsByIp(player);
			String log = "Players on same Ip : ";
			if(list.isEmpty())
			{
				log += "-";
			}
			else
			{
				log += String.join(", ", list);
			}
			output.append(log);
			output.newLine();

			output.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
