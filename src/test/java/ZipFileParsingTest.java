import com.codeborne.pdftest.PDF;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipFileParsingTest {

    private final ClassLoader cl = ZipFileParsingTest.class.getClassLoader();

    @Test
    void zipFileListingTest() throws Exception {
        // Тест для вывода списка файлов в архиве
        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("dataset.zip")
        )) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                System.out.println(entry.getName());
            }
        }
    }

    @Test
    void pdfFileParsingTest() throws Exception {
        // Тест для проверки PDF файла
        try (ZipFile zip = new ZipFile(cl.getResource("dataset.zip").getFile())) {
            Enumeration<ZipArchiveEntry> entries = zip.getEntries();
            boolean pdfFound = false;

            while (entries.hasMoreElements()) {
                ZipArchiveEntry entry = entries.nextElement();
                if (entry.getName().toLowerCase().endsWith(".pdf")) {
                    pdfFound = true;
                    parseAndValidatePdf(zip, entry);
                }
            }

            Assertions.assertTrue(pdfFound, "PDF файл не найден в архиве");
        }
    }

    @Test
    void csvFileParsingTest() throws Exception {
        // Тест для проверки CSV файла
        try (ZipFile zip = new ZipFile(cl.getResource("dataset.zip").getFile())) {
            Enumeration<ZipArchiveEntry> entries = zip.getEntries();
            boolean csvFound = false;

            while (entries.hasMoreElements()) {
                ZipArchiveEntry entry = entries.nextElement();
                if (entry.getName().toLowerCase().endsWith(".csv")) {
                    csvFound = true;
                    parseAndValidateCsv(zip, entry);
                }
            }

            Assertions.assertTrue(csvFound, "CSV файл не найден в архиве");
        }
    }

    @Test
    void xlsFileParsingTest() throws Exception {
        // Тест для проверки XLSX файла
        try (ZipFile zip = new ZipFile(cl.getResource("dataset.zip").getFile())) {
            Enumeration<ZipArchiveEntry> entries = zip.getEntries();
            boolean xlsFound = false;

            while (entries.hasMoreElements()) {
                ZipArchiveEntry entry = entries.nextElement();
                if (entry.getName().toLowerCase().endsWith(".xls")) {
                    xlsFound = true;
                    parseAndValidateXls(zip, entry);
                }
            }

            Assertions.assertTrue(xlsFound, "XLS файл не найден в архиве");
        }
    }

    private void parseAndValidatePdf(ZipFile zip, ZipArchiveEntry entry) throws Exception {
        System.out.println("Проверка PDF файла: " + entry.getName());

        // Извлекаем PDF из ZIP во временный файл
        File tempPdf = File.createTempFile("temp", ".pdf");
        try (InputStream is = zip.getInputStream(entry);
             FileOutputStream fos = new FileOutputStream(tempPdf)) {
            is.transferTo(fos);
        }

        // Проверяем содержимое PDF
        PDF pdf = new PDF(tempPdf);

        System.out.println();

        // Проверяем, что PDF не пустой
        Assertions.assertTrue(pdf.text.length() > 0, "PDF файл пустой");
        // Проверяем наличие текста
        Assertions.assertTrue(pdf.text.contains("HireDate"));

        tempPdf.deleteOnExit();
    }

    private void parseAndValidateCsv(ZipFile zip, ZipArchiveEntry entry) throws Exception {
        System.out.println("Проверка CSV файла: " + entry.getName());

        // Читаем CSV из ZIP
        try (InputStream is = zip.getInputStream(entry);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            // Проверяем, что CSV не пустой
            String header = reader.readLine();
            Assertions.assertNotNull(header, "CSV файл пустой");
            // Проверяем наличие вкладок
            String[] headers = header.split(",");
            Assertions.assertArrayEquals(
                    new String[]{"ID", "Name", "Age", "HireDate"},
                    headers,
                    "Заголовки CSV не соответствуют ожидаемым"
            );

        }
    }

    private void parseAndValidateXls(ZipFile zip, ZipArchiveEntry entry) throws Exception {
        System.out.println("Проверка XLS файла: " + entry.getName());

        // Извлекаем XLS из ZIP во временный файл
        File tempXls = File.createTempFile("temp", ".xls");
        try (InputStream is = zip.getInputStream(entry);
             FileOutputStream fos = new FileOutputStream(tempXls)) {
            is.transferTo(fos);
        }

        // Читаем XLS файл
        try (InputStream is = new FileInputStream(tempXls);
             Workbook workbook = new HSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            Assertions.assertNotNull(sheet, "Лист не найден в XLS файле");

            // Проверяем заголовки
            Row headerRow = sheet.getRow(0);
            Assertions.assertNotNull(headerRow, "Заголовки не найдены");

        } finally {
            tempXls.deleteOnExit();
        }
    }
}