package ovh.lumen.NKcertifier.data;

import ovh.lumen.NKcertifier.enums.LogLevel;
import ovh.lumen.NKcertifier.interfaces.NKplugin;
import ovh.lumen.NKcore.api.data.DBAccess;
import ovh.lumen.NKcore.api.data.NKServer;

import java.util.HashMap;
import java.util.Map;

public class NKData
{
	public static DBAccess DBACCESS = new DBAccess();
	public static NKServer SERVER_INFO = null;
	public static String PREFIX = null;
	public static LogLevel LOGLEVEL = null;
	public static NKplugin PLUGIN = null;
	public static String PLUGIN_NAME = null;
	public static String PLUGIN_VERSION = null;
	public static String PLUGIN_AUTHOR = null;
	public static String DEFAULT_TITLE = null;
	public static String DEFAULT_SUBTITLE = null;
	public static Map<String, Certificate> CERTIFICATES = new HashMap<>();
}
