package be.noki_senpai.NKcertifier.managers;

import be.noki_senpai.NKcertifier.NKCertifier;
import be.noki_senpai.NKcertifier.data.Certificate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class ConfigManager
{
	private ConsoleCommandSender console = null;

	private String defaultTitle = "Staff certifié";
	private String defaultSubTitle = "Bon jeu à vous !";
	private CertificateManager certificateManager = null;

	// Constructor
	public ConfigManager(CertificateManager certificateManager)
	{
		this.certificateManager = certificateManager;
		this.console = Bukkit.getConsoleSender();
	}

	public boolean loadConfig()
	{
		Yaml yaml = new Yaml();
		InputStream inputStream = null;

		try
		{
			inputStream = new FileInputStream(NKCertifier.getPlugin().getDataFolder() + "/config.yml");
		}
		catch(FileNotFoundException e)
		{
			console.sendMessage(ChatColor.DARK_RED + NKCertifier.PNAME + " Error while loading config.yml.");
			return false;
		}

		if(inputStream != null)
		{
			// Store yaml file as map objects
			Map<Object, Object> obj = yaml.load(inputStream);

			String error = "";

			// ######################################
			// Get default title
			// ######################################

			// Check if "default title" key is on obj
			if(obj.containsKey("default-title"))
			{
				defaultTitle = ((String) obj.get("default-title")).replaceAll("&", "§");
			}

			// ######################################
			// Get default subtitle
			// ######################################

			// Check if "default subtitle" key is on obj
			if(obj.containsKey("default-subtitle"))
			{
				defaultSubTitle = ((String) obj.get("default-subtitle")).replaceAll("&", "§");
			}

			// ######################################
			// Get data list
			// ######################################

			// Check if "data" key is on obj
			if(!(obj.containsKey("data")))
			{
				error += "\n> Key 'data' not found. Verify syntax of keys.";
			}

			// ######################################
			// Check config errors
			// ######################################

			// Check if there are errors
			if(!error.equals(""))
			{
				console.sendMessage(ChatColor.DARK_RED + NKCertifier.PNAME + " Error while parsing config.yaml." + error);
				return false;
			}

			// Get blocks timer list
			Map<String, Object> data = (Map<String, Object>) obj.get("data");

			// ######################################
			// Browse data list
			// ######################################

			error = "";

			for(Map.Entry<String, Object> data_ : data.entrySet())
			{
				String name = data_.getKey();
				String password = null;
				String title = null;
				String subTitle = null;

				Map<String, Object> info = (Map<String, Object>) data_.getValue();

				// ######################################
				// Get password
				// ######################################

				// Check if "password" key is on info
				if(!info.containsKey("password"))
				{
					error += "\n"+ name + " > Key 'password' not found. Verify syntax of keys.";
					continue;
				}

				password = (String) info.get("password");

				// ######################################
				// Get title
				// ######################################

				// Check if "title" key is on info
				if(info.containsKey("title"))
				{
					title = ((String) info.get("title")).replaceAll("&", "§");
					if(title.equals(""))
					{
						title = defaultTitle;
					}
				}
				else
				{
					title = defaultTitle;
				}

				// ######################################
				// Get subtitle
				// ######################################

				// Check if "subtitle" key is on info
				if(info.containsKey("subtitle"))
				{
					subTitle = ((String) info.get("subtitle")).replaceAll("&", "§");
					if(subTitle.equals(""))
					{
						subTitle = defaultSubTitle;
					}
				}
				else
				{
					subTitle = defaultSubTitle;
				}
				certificateManager.addCertificate(name, new Certificate(name, password, title, subTitle));
			}

			// ######################################
			// Check config errors
			// ######################################

			// Check if there are errors
			if(!error.equals(""))
			{
				console.sendMessage(ChatColor.DARK_RED + NKCertifier.PNAME + " Error while parsing config.yml." + error);
				return false;
			}

			return true;
		}
		else
		{
			console.sendMessage(ChatColor.DARK_RED + NKCertifier.PNAME + " Error : inputStream is null");
			return false;
		}
	}
}
