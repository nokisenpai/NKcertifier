package be.noki_senpai.NKcertifier.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;

public class NKLogger extends AbstractFilter
{
	public void registerFilter()
	{
		Logger logger = (Logger) LogManager.getRootLogger();
		logger.addFilter(this);
	}

	@Override
	public Result filter(LogEvent event)
	{
		if(event == null)
		{
			return Result.NEUTRAL;
		}

		return validateMessage(event.getMessage().toString());
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t)
	{
		return validateMessage(msg.toString());
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object... params)
	{
		return validateMessage(msg);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t)
	{
		if(msg == null)
		{
			return Result.NEUTRAL;
		}

		return validateMessage(msg.toString());
	}

	private Result validateMessage(String message)
	{
		if(message == null)
		{
			return Result.NEUTRAL;
		}
		return Result.NEUTRAL;
		/*boolean shouldBeOmitted; // Implement

		return shouldBeOmitted ? Result.DENY : Result.NEUTRAL;*/
	}
}
