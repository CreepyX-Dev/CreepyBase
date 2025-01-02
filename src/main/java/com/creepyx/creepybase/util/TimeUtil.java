package com.creepyx.creepybase.util;

import lombok.Getter;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Getter
public class TimeUtil {

	public Timestamp getTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public Timestamp getTimestamp(long time) {
		return new Timestamp(time);
	}

	public Timestamp getTimestamp(Duration duration) {
		return new Timestamp(System.currentTimeMillis() + duration.toMillis());
	}

	public Timestamp getTimestamp(Duration... durations) {
		List<Duration> list = Arrays.stream(durations).toList();
		long finalDuration = 0;
		for (Duration duration : list) {
			finalDuration += duration.toMillis();
		}
		return new Timestamp(System.currentTimeMillis() + finalDuration);
	}

	public Time getTime() {
		return new Time(System.currentTimeMillis());
	}

	public Time getTime(long time) {
		return new Time(time);
	}

	public Time getTime(Duration duration) {
		return new Time(System.currentTimeMillis() + duration.toMillis());
	}

	public Time getTime(Duration... durations) {
		List<Duration> list = Arrays.stream(durations).toList();
		long finalDuration = 0;
		for (Duration duration : list) {
			finalDuration += duration.toMillis();
		}
		return new Time(System.currentTimeMillis() + finalDuration);
	}

}
