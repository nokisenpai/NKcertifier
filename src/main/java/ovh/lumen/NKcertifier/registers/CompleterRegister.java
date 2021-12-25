package ovh.lumen.NKcertifier.registers;

import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import ovh.lumen.NKcertifier.completers.RootCompleter;
import ovh.lumen.NKcertifier.data.NKData;
import ovh.lumen.NKcertifier.enums.InternalMessages;
import ovh.lumen.NKcertifier.utils.MessageParser;
import ovh.lumen.NKcertifier.utils.NKLogger;

import java.util.HashMap;
import java.util.Map;

public class CompleterRegister
{
	private static final Map<String, TabCompleter> completers = setCompleters();

	public static void registerAllCompleters(JavaPlugin plugin)
	{
		completers.forEach((commandName, tabCompleter) ->
		{
			PluginCommand command = plugin.getCommand(commandName);
			if(command != null)
			{
				command.setTabCompleter(tabCompleter);
			}
			else
			{
				MessageParser messageParser = new MessageParser(InternalMessages.REGISTER_COMPLETER_FAIL.toString());
				messageParser.addArg(commandName);

				NKLogger.warn(messageParser.parse());
			}
		});
	}

	private static Map<String, TabCompleter> setCompleters()
	{
		Map<String, TabCompleter> completers = new HashMap<>();
		completers.put(NKData.PLUGIN_NAME, new RootCompleter());

		return completers;
	}
}
