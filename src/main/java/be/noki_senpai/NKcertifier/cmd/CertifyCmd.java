package be.noki_senpai.NKcertifier.cmd;

import be.noki_senpai.NKcertifier.data.Certificate;
import be.noki_senpai.NKcertifier.managers.CertificateManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class CertifyCmd implements CommandExecutor
{
	CertificateManager certificateManager = null;

	public CertifyCmd(CertificateManager certificateManager)
	{
		this.certificateManager = certificateManager;
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args)
	{
		String password = "";

		if(sender instanceof ConsoleCommandSender)
		{
			sender.sendMessage(ChatColor.RED + "Cette commande ne peut pas être effectuée en console.");
		}

		// if no argument
		if (args.length == 0)
		{
			sender.sendMessage(ChatColor.RED + "Vous n'avez spécifié aucun argument.");
			return true;
		}
		else
		{
			Certificate certificate = certificateManager.getCertificate(sender.getName());

			if(certificate == null)
			{
				sender.sendMessage(ChatColor.RED + "Vous n'avez pas accès à cette commande.");
				return true;
			}

			for(int i = 0; i < args.length; i++)
			{
				password += args[i] + " ";
			}
			password = password.substring(0, password.length() - 1);

			if(!certificate.getPassword().equals(password))
			{
				sender.sendMessage(ChatColor.RED + "Argument(s) invalide.");
				return true;
			}

			certificate.setCertified(true);
			Player player = (Player)sender;
			player.removePotionEffect(PotionEffectType.BLINDNESS);
			player.sendMessage(certificate.getTitle() +" " +certificate.getSubTitle());
			player.sendTitle(certificate.getTitle(), certificate.getSubTitle(), 10 , 70, 20);
		}
		return true;
	}
}
