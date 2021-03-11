package com.company;

import java.time.*;
import java.time.format.*;

public class Date {

    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d'.'M'.'y");

    public static String getFormat(LocalDateTime date) {
        return date.format(dateFormat);
    }

    public static LocalDate parsing(String dateText) {
        return LocalDate.parse(dateText, dateFormat);
    }

}
