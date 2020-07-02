package net.fbvictorhugo.j.barreirasanitaria.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static net.fbvictorhugo.j.barreirasanitaria.utils.Constantes.FORMATO_DATA_NASCIMENTO;

public abstract class DateUtil {

    public static String formataData(Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DATA_NASCIMENTO, Locale.getDefault());
        try {
            return sdf.format(data);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public static Date formataDataNascimento(String texto) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DATA_NASCIMENTO, Locale.getDefault());
        try {
            return sdf.parse(texto);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
