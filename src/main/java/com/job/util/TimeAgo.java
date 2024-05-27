package com.job.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TimeAgo {
	public static String calculateTimeAgo(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        long years = ChronoUnit.YEARS.between(dateTime, now);
        long months = ChronoUnit.MONTHS.between(dateTime, now);
        long days = ChronoUnit.DAYS.between(dateTime, now);
        long hours = ChronoUnit.HOURS.between(dateTime, now);
        long minutes = ChronoUnit.MINUTES.between(dateTime, now);
        long seconds = ChronoUnit.SECONDS.between(dateTime, now);

        if (years > 0) return years + "년 전";
        else if (months > 0) return months + "달 전";
        else if (days > 0) return days + "일 전";
        else if (hours > 0) return hours + "시간 전";
        else if (minutes > 0) return minutes + "분 전";
        else if (seconds > 0) return seconds + "초 전";
        else return "방금 전";
    }
    
    public static LocalDateTime stringToLocalDateTime(String strDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return LocalDateTime.parse(strDate, formatter);
    }
    
}