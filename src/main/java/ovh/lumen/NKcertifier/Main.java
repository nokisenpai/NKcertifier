package ovh.lumen.NKcertifier;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import ovh.lumen.NKcertifier.data.LogFilter;
import ovh.lumen.NKcertifier.data.NKData;
import ovh.lumen.NKcertifier.enums.InternalMessages;
import ovh.lumen.NKcertifier.interfaces.NKplugin;
import ovh.lumen.NKcertifier.managers.CertificateManager;
import ovh.lumen.NKcertifier.managers.ConfigManager;
import ovh.lumen.NKcertifier.registers.CommandRegister;
import ovh.lumen.NKcertifier.registers.CompleterRegister;
import ovh.lumen.NKcertifier.registers.ListenerRegister;
import ovh.lumen.NKcertifier.utils.NKLogger;

import java.io.File;

public final class Main extends JavaPlugin implements NKplugin
{
	@Override
	public void onEnable()
	{
		setup();
	}

	@Override
	public void onDisable()
	{
		clean();
	}

	@Override
	public void setup()
	{
		NKData.PLUGIN = this;
		NKData.PLUGIN_NAME = this.getName();
		NKData.PLUGIN_VERSION = this.getDescription().getVersion();
		NKData.PLUGIN_AUTHOR = this.getDescription().getAuthors().get(0);

		LogFilter.registerFilter();

		this.saveDefaultConfig();

		// Init
		NKLogger.init(Bukkit.getConsoleSender());
		ConfigManager.init(this.getConfig());

		// Load
		ConfigManager.load();

		if(!new File(getDataFolder() + "/logs.txt").isFile())
		{
			this.saveResource("logs.txt", false);
		}

		//Register
		ListenerRegister.registerAllListeners(this);
		CommandRegister.registerAllCommands(this);
		CompleterRegister.registerAllCompleters(this);

		displayNKSuccess();
	}

	@Override
	public void clean()
	{
		CertificateManager.unload();
	}

	@Override
	public void reload()
	{
		NKLogger.send(InternalMessages.RELOAD_ANNOUNCE.toString());
		clean();
		setup();
	}

	public void disablePlugin()
	{
		getServer().getPluginManager().disablePlugin(this);
	}

	private void displayNKSuccess()
	{
		NKLogger.show("\n"
				+ ChatColor.WHITE + "\u00A0 \u00A0 \u00A0.--.\n"
				+ "\u00A0 \u00A0 \u00A0| \u00A0 '. \u00A0" + ChatColor.GREEN + NKData.PLUGIN_NAME + " v" + NKData.PLUGIN_VERSION + " by " + NKData.PLUGIN_AUTHOR
				+ " - successfully enabled !\n"
				+ ChatColor.WHITE + "'-..___.-'");
	}
}
