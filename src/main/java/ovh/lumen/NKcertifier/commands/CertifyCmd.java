package ovh.lumen.NKcertifier.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import ovh.lumen.NKcertifier.data.Certificate;
import ovh.lumen.NKcertifier.data.NKData;

public class CertifyCmd implements CommandExecutor
{
	public CertifyCmd() {}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, String[] args)
	{
		String password = "";

		if(sender instanceof ConsoleCommandSender)
		{
			sender.sendMessage(ChatColor.RED + "Cette commande ne peut pas être effectuée en console.");

			return true;
		}

		if(args.length == 0)
		{
			sender.sendMessage(ChatColor.RED + "Vous n'avez spécifié aucun argument.");

			return true;
		}

		Certificate certificate = NKData.CERTIFICATES.get(sender.getName());

		if(certificate == null)
		{
			sender.sendMessage(ChatColor.RED + "Vous n'avez pas accès à cette commande.");

			return true;
		}

		for(String arg : args)
		{
			password += arg + " ";
		}
		password = password.substring(0, password.length() - 1);

		if(!certificate.getPassword().equals(password))
		{
			sender.sendMessage(ChatColor.RED + "Argument(s) invalide.");

			return true;
		}

		certificate.setCertified(true);
		Player player = (Player) sender;
		player.removePotionEffect(PotionEffectType.BLINDNESS);
		player.sendMessage(certificate.getTitle() + " " + certificate.getSubTitle());
		player.sendTitle(certificate.getTitle(), certificate.getSubTitle(), 10, 70, 20);

		return true;
	}
}
