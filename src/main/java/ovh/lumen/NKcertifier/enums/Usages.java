package ovh.lumen.NKcertifier.enums;

import ovh.lumen.NKcertifier.data.NKData;

public enum Usages
{
	ROOT_CMD("/" + NKData.PLUGIN_NAME.toLowerCase() + " [reload]");

	private final String value;

	Usages(String value)
	{
		this.value = value;
	}

	public String toString()
	{
		return InternalMessages.PREFIX_USAGE + this.value;
	}
}
