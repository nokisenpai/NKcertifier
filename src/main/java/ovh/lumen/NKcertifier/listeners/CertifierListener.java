package ovh.lumen.NKcertifier.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import ovh.lumen.NKcertifier.data.NKData;

public class CertifierListener implements Listener
{
	public CertifierListener() {}

	@EventHandler
	public void PlayerInteractEvent(PlayerInteractEvent event)
	{
		if(isUncertified(event.getPlayer().getName()))
		{
			event.setCancelled(true);
		}

	}

	@EventHandler
	public void PlayerDropItemEvent(PlayerDropItemEvent event)
	{
		if(isUncertified(event.getPlayer().getName()))
		{
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void EntityDamageEvent(EntityDamageEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			if(isUncertified(event.getEntity().getName()))
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
			if(isUncertified(event.getDamager().getName()))
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
			if(isUncertified(event.getPlayer().getName()))
			{
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void PlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event)
	{
		if(isUncertified(event.getPlayer().getName()))
		{
			if(!event.getMessage().split(" ")[0].equals("/certify"))
			{
				event.setCancelled(true);
			}
		}
	}

	public boolean isUncertified(String name)
	{
		if(NKData.CERTIFICATES.get(name) != null)
		{
			return NKData.CERTIFICATES.get(name).isUncertified();
		}

		return false;
	}
}