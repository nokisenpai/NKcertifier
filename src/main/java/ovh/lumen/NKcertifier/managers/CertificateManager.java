package ovh.lumen.NKcertifier.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import ovh.lumen.NKcertifier.data.Certificate;
import ovh.lumen.NKcertifier.data.NKData;

import java.util.ArrayList;
import java.util.List;

public final class CertificateManager
{
	private CertificateManager() {}

	public static void unload()
	{
		NKData.CERTIFICATES.clear();
	}

	public static void warnStaff(String name)
	{
		for(Certificate certificate : NKData.CERTIFICATES.values())
		{
			if(certificate.getName().equals(name))
			{
				continue;
			}

			Player staff = Bukkit.getPlayer(certificate.getName());
			if(staff != null)
			{
				staff.sendTitle(ChatColor.DARK_RED + "Suspected staff hack", "For Staff member " + ChatColor.BLUE + name, 0, 100, 30);
				staff.playSound(staff.getLocation(), Sound.ENTITY_PANDA_AGGRESSIVE_AMBIENT, 5F, 1F);
				staff.sendMessage(ChatColor.DARK_RED + "Suspected staff hack for Staff member " + ChatColor.BLUE + name);
			}
		}
	}

	public static void mailStaff(Player player)
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

		for(Certificate certificate : NKData.CERTIFICATES.values())
		{
			Player staff = Bukkit.getPlayer(certificate.getName());
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
