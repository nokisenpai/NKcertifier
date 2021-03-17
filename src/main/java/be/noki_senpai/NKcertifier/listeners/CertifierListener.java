package be.noki_senpai.NKcertifier.listeners;

import be.noki_senpai.NKcertifier.managers.CertificateManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class CertifierListener implements Listener
{
	private CertificateManager certificateManager = null;

	public CertifierListener(CertificateManager certificateManager)
	{
		this.certificateManager = certificateManager;
	}

	@EventHandler
	public void PlayerInteractEvent(PlayerInteractEvent event)
	{
		if(!isCertified(event.getPlayer().getName()))
		{
			event.setCancelled(true);
		}

	}

	@EventHandler
	public void PlayerDropItemEvent(PlayerDropItemEvent event)
	{
		if(!isCertified(event.getPlayer().getName()))
		{
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void EntityDamageEvent(EntityDamageEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			if(!isCertified(((Player) event.getEntity()).getName()))
			{
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void EntityDamageByEntityEvent(EntityDamageByEntityEvent event)
	{
		if(event.getDamager() instanceof Player)
		{
			if(!isCertified(((Player) event.getDamager()).getName()))
			{
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void AsyncPlayerChatEvent(AsyncPlayerChatEvent event)
	{
		if(event.isAsynchronous())
		{
			if(!isCertified(event.getPlayer().getName()))
			{
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void PlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event)
	{
		if(!isCertified(event.getPlayer().getName()))
		{
			if(!event.getMessage().split(" ")[0].equals("/certify"))
			{
				event.setCancelled(true);
			}
		}
	}

	public boolean isCertified(String name)
	{
		if(certificateManager.getCertificate(name) != null)
		{
			return certificateManager.getCertificate(name).isCertified();
		}
		return true;
	}
}