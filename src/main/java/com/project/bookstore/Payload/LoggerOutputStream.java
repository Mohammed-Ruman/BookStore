package com.project.bookstore.Payload;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.*;

public class LoggerOutputStream extends OutputStream {

	private final Logger logger;
	private final Level level;
	private final StringBuilder buffer = new StringBuilder();

	public LoggerOutputStream(Logger logger, Level level) {
		this.logger = logger;
		this.level = level;
	}

	@Override
	public void write(int b) throws IOException {
		char c = (char) b;
		if (c == '\n') {
			logger.log(level, buffer.toString());
			buffer.setLength(0);
		} else {
			buffer.append(c);
		}
	}

}
