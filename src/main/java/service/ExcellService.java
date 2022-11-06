package service;

import dao.FileDAO;
import dao.FileDAOImpl;
import entity.Article;
import entity.Orders;
import entity.OrdersToExcel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import settings.Configuration;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExcellService {


    FileDAO fileDAO = new FileDAOImpl();
    public void createExcell(String pathXml) throws ParseException {

        List<OrdersToExcel> ordersToExcels = loadInfo();
        try (Workbook workbook = new XSSFWorkbook()){
            Sheet sheet = workbook.createSheet();
            createHeader(sheet,workbook.createCellStyle(),workbook.createFont());
            createRow(ordersToExcels, sheet);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private void createRow(List<OrdersToExcel> orders, Sheet sheet) {
        for (int i = 0; i < orders.size() ; i++) {
            OrdersToExcel ordersToExcel = orders.get(i);
            Row row = sheet.createRow(i + 1);
            Cell cell = row.createCell(0);
            RichTextString text = new XSSFRichTextString(ordersToExcel.getName());
            cell.setCellValue(text);

            cell = row.createCell(1);
            text = new XSSFRichTextString(ordersToExcel.getType());
            cell.setCellValue(text);

            cell = row.createCell(2);
            text = new XSSFRichTextString(String.valueOf(ordersToExcel.getSaleDate()));
            cell.setCellValue(text);

            cell = row.createCell(3);
            text = new XSSFRichTextString(String.valueOf(ordersToExcel.getDeliverDate()));
            cell.setCellValue(text);

            cell = row.createCell(4);
            text = new XSSFRichTextString(ordersToExcel.getProductStatus());
            cell.setCellValue(text);

            cell = row.createCell(5);
            text = new XSSFRichTextString(ordersToExcel.getDeliverStatus());
            cell.setCellValue(text);
        }
    }

    private void createHeader (Sheet sheet, CellStyle cellStyle, Font font) {
        cellStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBold(true);

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        RichTextString text = new XSSFRichTextString("Articulo");
        cell.setCellValue(text);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        text = new XSSFRichTextString("Tipo");
        cell.setCellValue(text);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        text = new XSSFRichTextString("Fecha de Venta");
        cell.setCellValue(text);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3);
        text = new XSSFRichTextString("fecha de entrega");
        cell.setCellValue(text);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(4);
        text = new XSSFRichTextString("Estado del pedido");
        cell.setCellValue(text);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(5);
        text = new XSSFRichTextString("Estado de la entrega");
        cell.setCellValue(text);
        cell.setCellStyle(cellStyle);
    }

    private List<OrdersToExcel> loadInfo() throws ParseException {

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();

        File OrderFile = new File(Configuration.PATH_XML);
        List<String> lines = fileDAO.readLineXML(OrderFile);
        List<Orders> ordersList = new ArrayList<>();
        List<OrdersToExcel> ordersToExcels = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String[] splitLine = lines.get(i).split("< >");
            ordersList.add(new Orders(splitLine[0], splitLine[1], splitLine[2],splitLine[3], splitLine[4]));
            String saleDateString = splitLine[2];
            String deliverDateString = splitLine[3];
            //int sD= Integer.parseInt(splitLine[2]);
            //int dD= Integer.parseInt(splitLine[3]);
            Date deliverStatusDate = formato.parse(deliverDateString);
            Date saleStatusDate = formato.parse(saleDateString);
            String productStatus = "";
            Date today = calendar.getTime();
            Date todayFormat = formato.parse(String.valueOf(today));

            if (deliverStatusDate.before(todayFormat) && splitLine[4].equals("confirmado")){
                productStatus = "en origen";
            }
            if (deliverStatusDate.before(todayFormat) && splitLine[4].equals("pendiente")){
                productStatus = "en recogida";
            }
            if (deliverStatusDate.before(todayFormat) && splitLine[4].equals("pendiente")) {
                productStatus = "en recogida";
            }

            ordersToExcels.add(new OrdersToExcel(splitLine[0],splitLine[1], saleStatusDate, deliverStatusDate, splitLine[4],productStatus ));

        }


        return ordersToExcels;
    }
}
