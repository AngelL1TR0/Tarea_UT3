package app;

import service.ExcellService;
import service.FileService;
import settings.Configuration;

public class Actividad2 {

    public static void main(String[] args) {
        ExcellService excellService = new ExcellService();
        excellService.createExcell(Configuration.PATH_XML);
    }
}
