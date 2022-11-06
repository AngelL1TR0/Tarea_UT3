package service;

import dao.FileDAO;
import dao.FileDAOImpl;
import entity.Article;
import settings.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    FileDAO fileDAO = new FileDAOImpl();
    public void createXml(String pathCsv, String path) {
        try {
            File file = new File(pathCsv);
            File fileXml = new File(path + file.getName().replace(".csv", ".xml"));
            List<Article> articles = new ArrayList<>();


            fileXml.createNewFile();

            List<String> line = fileDAO.readLine(file);
            String[] content;
            for (String fileContent : line) {
                content = fileContent.replace(",",".").split(Configuration.DELIMETER);
                articles.add(fileDAO.convertArticleInfo(content));
                String text = fileDAO.showArticleContent(articles);
                System.out.println(text);
                fileDAO.insertInfo(articles,fileXml);

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
