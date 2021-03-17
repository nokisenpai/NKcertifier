package be.noki_senpai.NKcertifier.data;

public class Certificate
{
	private String name = null;
	private String password = null;
	private String title = null;
	private String subTitle = null;
	private boolean certified = false;
	private  boolean skip = false;

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

	public boolean isCertified()
	{
		return certified || skip;
	}

	public void setCertified(boolean bool)
	{
		this.certified = bool;
	}

	public void setSkip(boolean skip)
	{
		this.skip = skip;
	}
}
