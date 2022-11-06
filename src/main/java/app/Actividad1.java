package app;

import service.FileService;
import settings.Configuration;

public class Actividad1 {

    public static void main(String[] args) {
        FileService fileservice = new FileService();
        fileservice.createXml(Configuration.PATH_CSV, Configuration.PATH);
    }
}
