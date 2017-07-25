import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.charts.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.chart.*;
import org.openxmlformats.schemas.drawingml.x2006.main.CTRegularTextRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextParagraph;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class CreateFillExcel {

    public static void writeValueToExcel(ArrayList<ContainerOfSortTime> container) throws IOException {
    FileOutputStream fileOutputStream = new FileOutputStream("arrays.xlsx");
    Workbook workbook = new XSSFWorkbook();
    int id = 0;//start position for selecting specific kind of sort in container
    fillSheet(container, workbook.createSheet(container.get(0).getTypeOfArray()), id);
        for (int i = 1; i < container.size(); i++) {
            if (!Objects.equals(container.get(i).getTypeOfArray(), container.get(i - 1).getTypeOfArray())) {
                fillSheet(container, workbook.createSheet(container.get(i).getTypeOfArray()), id += 40);
            }
        }
        workbook.write(fileOutputStream);
        fileOutputStream.close();
}

    private static Sheet fillSheet(ArrayList<ContainerOfSortTime> container, Sheet sheet, int id) {
        Row row;
        Cell cell;

        for (int rowIndex = 0; rowIndex < 6; rowIndex++) {
            row = sheet.createRow(rowIndex);
            int count = id;

            for (int colIndex = 1; colIndex < 9; colIndex++) {
                cell = row.createCell(colIndex);
                if (rowIndex == 0) cell.setCellValue(container.get(count).getLengthOfArray());
                if (rowIndex == 1) cell.setCellValue(container.get(count).getTimeOfSort());
                if (rowIndex == 2) cell.setCellValue(container.get(count).getTimeOfSort());
                if (rowIndex == 3) cell.setCellValue(container.get(count).getTimeOfSort());
                if (rowIndex == 4) cell.setCellValue(container.get(count).getTimeOfSort());
                if (rowIndex == 5) cell.setCellValue(container.get(count).getTimeOfSort());
                count=count+5;
            }

            if (rowIndex == 0) {
                cell = row.createCell(0);
                cell.setCellValue("Length of array");
                sheet.setColumnWidth(0, 6500);
            }
            if (rowIndex > 0) {
                cell = row.createCell(0);
                cell.setCellValue(container.get(id++).getNameOfSort());
                sheet.setColumnWidth(0, 6500);

            }
        }

        Drawing<?> drawing = sheet.createDrawingPatriarch();
        ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 7, 12, 25);

        Chart chart = drawing.createChart(anchor);

        ChartLegend legend = chart.getOrCreateLegend();
        legend.setPosition(LegendPosition.BOTTOM);

        LineChartData data = chart.getChartDataFactory().createLineChartData();

        ChartAxis bottomAxis = chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
        ValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
        leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
        bottomAxis.setCrosses(AxisCrosses.AUTO_ZERO);


        ChartDataSource<Number> xs = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(0, 0, 1, 8));
        ChartDataSource<Number> ys1 = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(1, 1, 1, 8));
        ChartDataSource<Number> ys2 = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(2, 2, 1, 8));
        ChartDataSource<Number> ys3 = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(3, 3, 1, 8));
        ChartDataSource<Number> ys4 = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(4, 4, 1, 8));
        ChartDataSource<Number> ys5 = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(5, 5, 1, 8));

        LineChartSeries series1 = data.addSeries(xs, ys1);
        row = sheet.getRow(1);
        cell = row.getCell(0);
        series1.setTitle(cell.getStringCellValue());
        LineChartSeries series2 = data.addSeries(xs, ys2);
        row = sheet.getRow(2);
        cell = row.getCell(0);
        series2.setTitle(cell.getStringCellValue());
        LineChartSeries series3 = data.addSeries(xs, ys3);
        row = sheet.getRow(3);
        cell = row.getCell(0);
        series3.setTitle(cell.getStringCellValue());
        LineChartSeries series4 = data.addSeries(xs, ys4);
        row = sheet.getRow(4);
        cell = row.getCell(0);
        series4.setTitle(cell.getStringCellValue());
        LineChartSeries series5 = data.addSeries(xs, ys5);
        row = sheet.getRow(5);
        cell = row.getCell(0);
        series5.setTitle(cell.getStringCellValue());
        chart.plot(data, bottomAxis, leftAxis);

        XSSFChart xchart = (XSSFChart) chart;
        CTChart ctChart = xchart.getCTChart();
        CTTitle title = ctChart.addNewTitle();
        CTTx tx = title.addNewTx();
        CTTextBody rich = tx.addNewRich();
        rich.addNewBodyPr();
        CTTextParagraph para = rich.addNewP();
        CTRegularTextRun r = para.addNewR();
        r.setT(sheet.getSheetName());
        setValueAxisTitle(xchart, 0, "Time of sorting array, nanosec");
        setCatAxisTitle(xchart, 0, "Size of sorting array");

        return sheet;
    }


    public static void setCatAxisTitle(XSSFChart chart, int axisIdx, String title) {
        CTCatAx valAx = chart.getCTChart().getPlotArea().getCatAxArray(axisIdx);
        CTTitle ctTitle = valAx.addNewTitle();
        ctTitle.addNewLayout();
        ctTitle.addNewOverlay().setVal(false);
        CTTextBody rich = ctTitle.addNewTx().addNewRich();
        rich.addNewBodyPr();
        rich.addNewLstStyle();
        CTTextParagraph p = rich.addNewP();
        p.addNewPPr().addNewDefRPr();
        p.addNewR().setT(title);
        p.addNewEndParaRPr();
    }


    public static void setValueAxisTitle(XSSFChart chart, int axisIdx, String title) {
        CTValAx valAx = chart.getCTChart().getPlotArea().getValAxArray(axisIdx);
        CTTitle ctTitle = valAx.addNewTitle();
        ctTitle.addNewLayout();
        ctTitle.addNewOverlay().setVal(false);
        CTTextBody rich = ctTitle.addNewTx().addNewRich();
        rich.addNewBodyPr();
        rich.addNewLstStyle();
        CTTextParagraph p = rich.addNewP();
        p.addNewPPr().addNewDefRPr();
        p.addNewR().setT(title);
        p.addNewEndParaRPr();
    }

}