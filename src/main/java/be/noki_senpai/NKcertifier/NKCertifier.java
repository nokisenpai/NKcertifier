package be.noki_senpai.NKcertifier;

import be.noki_senpai.NKcertifier.cmd.CertifyCmd;
import be.noki_senpai.NKcertifier.listeners.CertifierListener;
import be.noki_senpai.NKcertifier.listeners.PlayerConnectionListener;
import be.noki_senpai.NKcertifier.managers.Manager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class NKCertifier extends JavaPlugin
{
	public final static String PNAME = "[NKcertifier]";
	private Manager manager = null;
	private ConsoleCommandSender console = null;
	private static NKCertifier plugin = null;

	// Fired when plugin is first enabled
	@Override
	public void onEnable()
	{
		plugin = this;
		this.saveDefaultConfig();

		console = Bukkit.getConsoleSender();
		manager = new Manager(this);

		// Load configuration
		if(!manager.getConfigManager().loadConfig())
		{
			disablePlugin();
			return;
		}

		if(!new File(getDataFolder() + "/logs.txt").isFile())
		{
			this.saveResource("logs.txt", false);
		}

		// Register commands
		getCommand("certify").setExecutor(new CertifyCmd(manager.getCertificateManager()));

		// Event
		getServer().getPluginManager().registerEvents(new CertifierListener(manager.getCertificateManager()), this);
		getServer().getPluginManager().registerEvents(new PlayerConnectionListener(manager.getCertificateManager(), manager.getLogManager()), this);

		console.sendMessage(ChatColor.WHITE + "     .--. ");
		console.sendMessage(ChatColor.WHITE + "     |   '.   " + ChatColor.GREEN + PNAME + " by NoKi_senpai - successfully enabled !");
		console.sendMessage(ChatColor.WHITE + "'-..____.-'");
	}

	// Fired when plugin is disabled
	@Override
	public void onDisable()
	{
		manager.getCertificateManager().unloadCertificate();
		console.sendMessage(ChatColor.GREEN + PNAME + " has been disable.");
	}

	// ######################################
	// Getters & Setters
	// ######################################

	// Getter 'plugin'
	public static NKCertifier getPlugin()
	{
		return plugin;
	}

	// ######################################
	// Disable this plugin
	// ######################################

	public void disablePlugin()
	{
		getServer().getPluginManager().disablePlugin(this);
	}
}
