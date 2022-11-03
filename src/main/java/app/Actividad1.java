package app;

import service.FileService;

public class Actividad1 {

    public static void main(String[] args) {
        FileService fileservice = new FileService();
        fileservice.createXml();
    }
}
