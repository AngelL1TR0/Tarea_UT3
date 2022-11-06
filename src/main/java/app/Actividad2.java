package app;

import service.ExcellService;
import service.FileService;
import settings.Configuration;

import java.text.ParseException;

public class Actividad2 {

    public static void main(String[] args) throws ParseException {
        ExcellService excellService = new ExcellService();
        excellService.createExcell(Configuration.PATH_XML);
    }
}
