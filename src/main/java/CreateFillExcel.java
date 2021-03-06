import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.charts.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.chart.*;
import org.openxmlformats.schemas.drawingml.x2006.main.CTRegularTextRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextParagraph;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class CreateFillExcel {

    private String outputFilename;

    public final void setOutputFilename(String outputFilename) {
        this.outputFilename = outputFilename + ".xlsx";
    }

    public void writeValueToExcel(ArrayList<ContainerOfSortTime> container) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFilename)) {
            Workbook workbook = new XSSFWorkbook();
            int indexOfArrayContainer = 0;
            fillSheet(container, workbook.createSheet(container.get(0).getTypeOfArray()), indexOfArrayContainer);
            for (int i = 1; i < container.size(); i++) {
                if (!Objects.equals(container.get(i).getTypeOfArray(), container.get(i - 1).getTypeOfArray())) {
                    fillSheet(container, workbook.createSheet(container.get(i).getTypeOfArray()), indexOfArrayContainer += Reflection.countOfRunArray * Reflection.countOfSortMethod);
                }
            }
            workbook.write(fileOutputStream);
            System.out.println("Excel file created: " + System.getProperty("user.dir") + File.separator + outputFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillSheet(ArrayList<ContainerOfSortTime> container, Sheet sheet, int id) {
        Row row;
        Cell cell;
        for (int rowIndex = 0; rowIndex <= Reflection.countOfSortMethod; rowIndex++) {
            row = sheet.createRow(rowIndex);
            int count = id;
            for (int colIndex = 1; colIndex <= Reflection.countOfRunArray; colIndex++) {
                cell = row.createCell(colIndex);
                if (rowIndex == 0) {
                    cell.setCellValue(container.get(count).getLengthOfArray());
                } else {
                    cell.setCellValue(container.get(count).getTimeOfSort());
                }
                count = count + Reflection.countOfSortMethod;
            }

            if (rowIndex == 0) {
                cell = row.createCell(0);
                cell.setCellValue("Length of array");
                sheet.setColumnWidth(0, 6500);
            } else {
                cell = row.createCell(0);
                cell.setCellValue(container.get(id++).getNameOfSort());
                sheet.setColumnWidth(0, 6500);

            }
        }

        Drawing<?> drawing = sheet.createDrawingPatriarch();
        ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 8, 16, 35);
        Chart chart = drawing.createChart(anchor);
        ChartLegend legend = chart.getOrCreateLegend();
        legend.setPosition(LegendPosition.BOTTOM);
        LineChartData data = chart.getChartDataFactory().createLineChartData();
        ChartAxis bottomAxis = chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
        ValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
        leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
        bottomAxis.setCrosses(AxisCrosses.AUTO_ZERO);

        ChartDataSource<Number> xs = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(0, 0, 1, Reflection.countOfRunArray));
        for (int i = 1; i <= Reflection.countOfSortMethod; i++) {
            LineChartSeries series = data.addSeries(xs, DataSources.fromNumericCellRange(sheet, new CellRangeAddress(i, i, 1, Reflection.countOfRunArray)));
            row = sheet.getRow(i);
            cell = row.getCell(0);
            series.setTitle(cell.getStringCellValue());
        }

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
        setValueAxisTitle(xchart);
        setCatAxisTitle(xchart);
    }


    private void setCatAxisTitle(XSSFChart chart) {
        CTCatAx valAx = chart.getCTChart().getPlotArea().getCatAxArray(0);
        CTTitle ctTitle = valAx.addNewTitle();
        ctTitle.addNewLayout();
        ctTitle.addNewOverlay().setVal(false);
        CTTextBody rich = ctTitle.addNewTx().addNewRich();
        rich.addNewBodyPr();
        rich.addNewLstStyle();
        CTTextParagraph p = rich.addNewP();
        p.addNewPPr().addNewDefRPr();
        p.addNewR().setT("Size of sorting array");
        p.addNewEndParaRPr();
    }


    private void setValueAxisTitle(XSSFChart chart) {
        CTValAx valAx = chart.getCTChart().getPlotArea().getValAxArray(0);
        CTTitle ctTitle = valAx.addNewTitle();
        ctTitle.addNewLayout();
        ctTitle.addNewOverlay().setVal(false);
        CTTextBody rich = ctTitle.addNewTx().addNewRich();
        rich.addNewBodyPr();
        rich.addNewLstStyle();
        CTTextParagraph p = rich.addNewP();
        p.addNewPPr().addNewDefRPr();
        p.addNewR().setT("Time of sorting array, nanosec");
        p.addNewEndParaRPr();
    }

}
