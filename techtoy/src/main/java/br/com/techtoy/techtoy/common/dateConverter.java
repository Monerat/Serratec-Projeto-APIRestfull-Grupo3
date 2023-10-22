package br.com.techtoy.techtoy.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class dateConverter {

    public static String converter(Date date) {
        return new SimpleDateFormat("dd/MM/YYYY HH:mm:ss").format(date);
    }
}