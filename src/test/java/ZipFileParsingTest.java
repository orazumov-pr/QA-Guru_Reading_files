import com.codeborne.pdftest.PDF;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipFileParsingTest {

    private ClassLoader cl = ZipFileParsingTest.class.getClassLoader();

    @Test
    void zipFileParsingTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("dataset.zip")
        )) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                System.out.println(entry.getName());
            }
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
}
