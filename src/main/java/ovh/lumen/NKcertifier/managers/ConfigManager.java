package ovh.lumen.NKcertifier.managers;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import ovh.lumen.NKcertifier.data.Certificate;
import ovh.lumen.NKcertifier.data.NKData;
import ovh.lumen.NKcertifier.enums.InternalMessages;
import ovh.lumen.NKcertifier.enums.LogLevel;
import ovh.lumen.NKcertifier.utils.NKLogger;

public final class ConfigManager
{
	private ConfigManager() {}

	private static FileConfiguration config = null;

	public static void init(FileConfiguration config)
	{
		ConfigManager.config = config;
	}

	public static void load()
	{
		NKData.DEFAULT_TITLE = config.getString("default-title", "Staff certifié");
		NKData.DEFAULT_SUBTITLE = config.getString("default-subtitle", "Bon jeu à vous !");

		ConfigurationSection data = config.getConfigurationSection("data");

		if(data != null)
		{
			for(String playerName : data.getKeys(false))
			{
				ConfigurationSection playerData = data.getConfigurationSection(playerName);

				if(playerData == null)
				{
					continue;
				}

				Certificate certificate = new Certificate(playerName, playerData.getString("password"), playerData.getString("title"), playerData.getString("subtitle"));

				NKData.CERTIFICATES.put(playerName, certificate);
			}
		}

		try
		{
			NKData.LOGLEVEL = LogLevel.valueOf(config.getString("log-level", "LOG").toUpperCase());
		}
		catch(IllegalArgumentException e)
		{
			NKLogger.error(InternalMessages.CONFIG_KEY_LOGLEVEL_BAD_VALUE.toString());
			NKData.LOGLEVEL = LogLevel.LOG;
		}

		NKLogger.setLogLevel(NKData.LOGLEVEL);

		NKData.CERTIFICATES.forEach((s, certificate) ->
				NKLogger.debug(s + "|" + certificate.getPassword() + "|" + certificate.getTitle() + "|" + certificate.getSubTitle()));//TODO (remove)
	}
}
