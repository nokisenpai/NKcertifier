package be.noki_senpai.NKcertifier.managers;

import be.noki_senpai.NKcertifier.data.Certificate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CertificateManager
{
	private final Map<String, Certificate> certificate = new HashMap<>();

	private ConsoleCommandSender console = null;

	public CertificateManager()
	{
		console = Bukkit.getConsoleSender();
	}

	public void unloadCertificate()
	{
		certificate.clear();
	}

	// ######################################
	// Getters (only)
	// ######################################

	public Map<String, Certificate> getSecurityData()
	{
		return certificate;
	}

	public void addCertificate(String name, Certificate certificate)
	{
		this.certificate.put(name, certificate);
	}

	public Certificate getCertificate(String name)
	{
		return certificate.get(name);
	}

	public void warnStaff(String name)
	{
		for(Certificate certificate_ : certificate.values())
		{
			if(certificate_.getName().equals(name))
			{
				continue;
			}

			Player staff = Bukkit.getPlayer(certificate_.getName());
			if(staff != null)
			{
				staff.sendTitle(ChatColor.DARK_RED + "Suspected staff hack", "For Staff member " + ChatColor.BLUE + name, 0, 100, 30);
				staff.playSound(staff.getLocation(), Sound.ENTITY_PANDA_AGGRESSIVE_AMBIENT, 5F, 1F);
				staff.sendMessage(ChatColor.DARK_RED + "Suspected staff hack for Staff member " + ChatColor.BLUE + name);
			}
		}
	}

	public void mailStaff(Player player)
	{
		String mail = ChatColor.DARK_RED + "Staff member " + ChatColor.BLUE + player.getName() + ChatColor.DARK_RED + " was suspected hacked.";
		mail += "\nIp address : " + ChatColor.BLUE + player.getAddress().getAddress().toString();
		List<String> list = getOtherClientsByIp(player);
		mail += ChatColor.DARK_RED + "\nPlayers on same Ip : " + ChatColor.BLUE;
		if(list.isEmpty())
		{
			mail += "-";
		}
		else
		{
			mail += String.join(", ", list);
		}

		for(Certificate certificate_ : certificate.values())
		{
			Player staff = Bukkit.getPlayer(certificate_.getName());
			if(staff != null)
			{
				staff.playSound(staff.getLocation(), Sound.ENTITY_PANDA_AGGRESSIVE_AMBIENT, 5F, 1F);
				staff.sendMessage(mail);
			}
			else
			{
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "mail " + mail.replaceAll("\n", ChatColor.GOLD + " # "));
			}
		}
	}

	public static List<String> getOtherClientsByIp(Player player)
	{
		List<String> playersByIp = new ArrayList<>();
		for(Player player_ : Bukkit.getOnlinePlayers())
		{
			if(player_.getAddress().getAddress().equals(player.getAddress().getAddress()) && !player_.getName().equals(player.getName()))
			{
				playersByIp.add(player_.getName());
			}
		}
		return playersByIp;
	}
}
