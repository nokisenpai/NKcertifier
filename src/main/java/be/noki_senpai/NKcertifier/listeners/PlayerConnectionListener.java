package be.noki_senpai.NKcertifier.listeners;

import be.noki_senpai.NKcertifier.NKCertifier;
import be.noki_senpai.NKcertifier.data.Certificate;
import be.noki_senpai.NKcertifier.managers.CertificateManager;
import be.noki_senpai.NKcertifier.managers.LogManager;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerConnectionListener implements Listener
{
	private CertificateManager certificateManager = null;
	private LogManager logManager = null;

	public PlayerConnectionListener(CertificateManager certificateManager, LogManager logManager)
	{
		this.certificateManager = certificateManager;
		this.logManager = logManager;
	}

	@EventHandler
	public void PlayerJoinEvent(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		Certificate certificate = certificateManager.getCertificate(player.getName());

		if(certificate != null)
		{
			certificate.setSkip(false);
			player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 1, false, false, false));
			new BukkitRunnable()
			{
				@Override
				public void run()
				{
					if(!certificate.isCertified())
					{
						player.sendTitle(ChatColor.DARK_RED + "Suspected staff hack", ChatColor.DARK_RED + "Please certify yourself", 0, 100, 30);
						player.playSound(player.getLocation(), Sound.ENTITY_PANDA_AGGRESSIVE_AMBIENT, 5F, 1F);
						player.sendMessage(ChatColor.DARK_RED + "Suspected staff hack. Please certify yourself.");
						logManager.log(0, player);

						new BukkitRunnable()
						{
							@Override
							public void run()
							{
								if(!certificate.isCertified())
								{
									certificateManager.warnStaff(player.getName());
									logManager.log(1, player);
									new BukkitRunnable()
									{
										@Override
										public void run()
										{
											if(!certificate.isCertified())
											{
												certificateManager.mailStaff(player);
												logManager.log(2, player);
												player.kickPlayer("Suspected staff hack. Please leave us alone.");
											}
										}
									}.runTaskLater(NKCertifier.getPlugin(), 20 * 60 * 3);
								}
							}
						}.runTaskLaterAsynchronously(NKCertifier.getPlugin(), 20 * 60);
					}
				}
			}.runTaskLaterAsynchronously(NKCertifier.getPlugin(), 20 * 60);
		}
	}

	@EventHandler
	public void PlayerQuitEvent(PlayerQuitEvent event)
	{
		Certificate certificate = certificateManager.getCertificate(event.getPlayer().getName());

		if(certificate != null)
		{
			certificate.setCertified(false);
			certificate.setSkip(true);
		}
	}
}
