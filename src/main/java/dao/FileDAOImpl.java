package dao;

import entity.Article;
import settings.Configuration;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FileDAOImpl implements FileDAO {
    @Override
    public List<String> readLine(File file) {
        List<String> lines = new ArrayList<>();
        try (Reader reader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            boolean firstLine = true;
            while ((line = bufferedReader.readLine()) != null) {
                if (firstLine){
                    firstLine = false;
                    continue;
            }
                lines.add(line);
        }
    } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }

    @Override
    public Article convertArticleInfo(String[] content) {
        Article article = new Article();
        article.setArticleName(content[0]);
        article.setType(content[1]);
        article.setSaleDate(content[2]);
        article.setSellingPrice(new BigDecimal(content[3]));
        article.setDerivativesCost(new BigDecimal(content[4]));
        article.setProductionCost(new BigDecimal(content[5]));
        article.setTaxes(new BigDecimal(content[6]));

        return article;
    }

    @Override
    public String showArticleContent(List<Article> articles) {
        String infoText = "";
        for (int i = 0; i < articles.size() ; i++) {
                BigDecimal totalCost = articles.get(i).getDerivativesCost().add(articles.get(i).getProductionCost().add(articles.get(i).getTaxes()));
                BigDecimal total = articles.get(i).getSellingPrice().subtract(totalCost);
                infoText = "<articulo>" + "\n" +
                        "<nombre>"+ articles.get(i).getArticleName() +"</nombre>" + "\n" +
                        "<tipo>" + articles.get(i).getType() + "</tipo>" + "\n" +
                        "<fecha>" + articles.get(i).getSaleDate() + "</fecha>" + "\n" +
                        "<precio>" + articles.get(i).getSellingPrice() + "</precio>" + "\n" +
                        "<coste>" + totalCost + "</coste>" + "\n" +
                        "<beneficio>" + total + "</beneficio>" + "\n" +
                        "</articulo>" + "\n";
            }
            return infoText;
        }

    @Override
    public void insertInfo(List<Article> articles, File fileXml) {
        try {
            FileWriter fileWriter = new FileWriter(fileXml);
            fileWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n");
            fileWriter.write("<ventas>" + "\n");
            for (int i = 0; i < articles.size(); i++) {
                BigDecimal totalCost = articles.get(i).getDerivativesCost().add(articles.get(i).getProductionCost().add(articles.get(i).getTaxes()));
                BigDecimal total = articles.get(i).getSellingPrice().subtract(totalCost);
                fileWriter.write("<articulo>" + "\n");
                fileWriter.write("<nombre>"+ articles.get(i).getArticleName() +"</nombre>" + "\n");
                fileWriter.write("<tipo>" + articles.get(i).getType() + "</tipo>" + "\n");
                fileWriter.write("<fecha>" + articles.get(i).getSaleDate() + "</fecha>" + "\n");
                fileWriter.write("<precio>" + articles.get(i).getSellingPrice() + "</precio>" + "\n");
                fileWriter.write("<coste>" + totalCost + "</coste>" + "\n");
                fileWriter.write("<beneficio>" + total + "</beneficio>" + "\n");
                fileWriter.write("</articulo>" + "\n");
            }
            fileWriter.write("</ventas>" + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> readLineXML(File file) {

        List<String> lines = new ArrayList<>();
            try {
                FileReader fr = new FileReader(file);
                BufferedReader bf = new BufferedReader(fr);
                String row;

                while ((row = bf.readLine()) != null) {
                    lines.add(row);
                }
                bf.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return lines;
    }
}