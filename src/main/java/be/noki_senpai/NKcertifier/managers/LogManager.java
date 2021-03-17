package be.noki_senpai.NKcertifier.managers;

import be.noki_senpai.NKcertifier.NKCertifier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LogManager
{
	private ConsoleCommandSender console = null;

	public LogManager()
	{
		this.console = Bukkit.getConsoleSender();
	}

	public void log(int level, Player player)
	{
		File logFile = new File(NKCertifier.getPlugin().getDataFolder() + "/logs.txt");

		try
		{
			BufferedWriter output;
			output = new BufferedWriter(new FileWriter(logFile, true));

			LocalDateTime myDateObj = LocalDateTime.now();
			DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			String formattedDate = myDateObj.format(myFormatObj);

			console.sendMessage(ChatColor.DARK_RED + "[WARN][LEVEL " + level + "] Staff member '" + player.getName() + "' was suspected hacked.");
			output.append("[" + formattedDate + "][WARN][LEVEL " + level + "] Staff member '" + player.getName() + "' was suspected hacked.");
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
