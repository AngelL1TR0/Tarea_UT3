package dao;

import entity.Article;

import java.io.File;
import java.util.List;

public interface FileDAO {
    List<String> readLine(File file);

    Article convertArticleInfo(String[] content);

    String showArticleContent(List<Article> articles);

    void insertInfo(List<Article> articles, File fileXml);

    List<String> readLineXML(File file);
}
