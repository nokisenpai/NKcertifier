package ovh.lumen.NKcertifier.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import ovh.lumen.NKcertifier.data.Certificate;
import ovh.lumen.NKcertifier.data.NKData;
import ovh.lumen.NKcertifier.managers.CertificateManager;
import ovh.lumen.NKcertifier.managers.LogManager;

public class PlayerConnectionListener implements Listener
{
	public PlayerConnectionListener() {}

	@EventHandler
	public void PlayerJoinEvent(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		Certificate certificate = NKData.CERTIFICATES.get(player.getName());
		Long ts = System.currentTimeMillis();

		if(certificate != null)
		{
			certificate.setTs(ts);
			certificate.setSkip(false);
			player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 1, false, false, false));
			new BukkitRunnable()
			{
				@Override
				public void run()
				{
					if(certificate.isUncertified() && certificate.sameTs(ts))
					{
						player.sendTitle(ChatColor.DARK_RED + "Suspected staff hack", ChatColor.DARK_RED + "Please certify yourself", 0, 100, 30);
						player.playSound(player.getLocation(), Sound.ENTITY_PANDA_AGGRESSIVE_AMBIENT, 5F, 1F);
						player.sendMessage(ChatColor.DARK_RED + "Suspected staff hack. Please certify yourself.");
						LogManager.log(0, player);

						new BukkitRunnable()
						{
							@Override
							public void run()
							{
								if(certificate.isUncertified() && certificate.sameTs(ts))
								{
									CertificateManager.warnStaff(player.getName());
									LogManager.log(1, player);
									new BukkitRunnable()
									{
										@Override
										public void run()
										{
											if(certificate.isUncertified() && certificate.sameTs(ts))
											{
												CertificateManager.mailStaff(player);
												LogManager.log(2, player);
												player.kickPlayer("Suspected staff hack. Please leave us alone.");
											}
										}
									}.runTaskLater((Plugin) NKData.PLUGIN, 20 * 60 * 3);
								}
							}
						}.runTaskLaterAsynchronously((Plugin) NKData.PLUGIN, 20 * 60);
					}
				}
			}.runTaskLaterAsynchronously((Plugin) NKData.PLUGIN, 20 * 60);
		}
	}

	@EventHandler
	public void PlayerQuitEvent(PlayerQuitEvent event)
	{
		Certificate certificate = NKData.CERTIFICATES.get(event.getPlayer().getName());

		if(certificate != null)
		{
			certificate.setCertified(false);
			certificate.setSkip(true);
		}
	}
}
