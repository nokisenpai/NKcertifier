package be.noki_senpai.NKcertifier.managers;

import be.noki_senpai.NKcertifier.NKCertifier;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class Manager
{
	private ConsoleCommandSender console = null;
	private ConfigManager configManager = null;
	private CertificateManager certificateManager = null;
	private LogManager logManager = null;

	public Manager(NKCertifier instance)
	{
		console = Bukkit.getConsoleSender();
		certificateManager = new CertificateManager();
		configManager = new ConfigManager(certificateManager);
		logManager = new LogManager();
	}

	// ######################################
	// Getters & Setters
	// ######################################

	// Console
	public ConsoleCommandSender getConsole()
	{
		return console;
	}

	// ConfigManager
	public ConfigManager getConfigManager()
	{
		return configManager;
	}

	// CertificateManager
	public CertificateManager getCertificateManager()
	{
		return certificateManager;
	}
	// LogManager
	public LogManager getLogManager()
	{
		return logManager;
	}
}
