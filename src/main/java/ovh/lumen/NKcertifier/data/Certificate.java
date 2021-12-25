package ovh.lumen.NKcertifier.data;

import java.util.Objects;

public class Certificate
{
	private final String name;
	private final String password;
	private final String title;
	private final String subTitle;
	private boolean certified = false;
	private boolean skip = false;
	private Long ts = null;

	public Certificate(String name, String password, String title, String subTitle)
	{
		this.name = name;
		this.password = password;
		this.title = title;
		this.subTitle = subTitle;
	}

	public String getName()
	{
		return name;
	}

	public String getPassword()
	{
		return password;
	}

	public String getTitle()
	{
		return title;
	}

	public String getSubTitle()
	{
		return subTitle;
	}

	public boolean isUncertified()
	{
		return !certified && !skip;
	}

	public void setCertified(boolean bool)
	{
		this.certified = bool;
	}

	public void setSkip(boolean skip)
	{
		this.skip = skip;
	}

	public boolean sameTs(Long ts)
	{
		return Objects.equals(this.ts, ts);
	}

	public void setTs(Long ts)
	{
		this.ts = ts;
	}
}
