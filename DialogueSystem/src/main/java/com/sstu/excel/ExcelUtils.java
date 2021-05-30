package com.sstu.excel;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ExcelUtils {
    private static final String WIN_ENCODING = "windows-1251";
    private static final String DEFAULT_EXCEL_FILE_PATH = "C:/Users/Vlad/Desktop/Coursework.xlsx";
    public static final String COURSEWORK_FOLDER_PATH = "D:/University/6 semester/" +
            "Mathematical Foundations of Artificial Intelligence/" +
            "Coursework/v2/Prolog/";
    private static final HashMap<String, String> CACHED_WORDS = new HashMap<>();

    public static void createPrologFilesByExcel() throws IOException {
        createPrologFilesByExcel(DEFAULT_EXCEL_FILE_PATH);
    }

    public static void createPrologFilesByExcel (String excelFilePath) throws IOException {
        FileInputStream xlsxFile = new FileInputStream(excelFilePath);
        XSSFWorkbook xlsx = new XSSFWorkbook(xlsxFile);
        XSSFSheet worksheet = xlsx.getSheetAt(0);

        writeAllKeywordsPredicates(worksheet);
        writeAllAssociationsPredicates(worksheet);
        writeAllAnswersPredicates(worksheet);
        xlsxFile.close();
    }

    private static void writeAllKeywordsPredicates(XSSFSheet sourceSheet) throws IOException {
        PrintWriter writer = new PrintWriter(
                COURSEWORK_FOLDER_PATH + "keywords.pl",
                StandardCharsets.UTF_8);

        int rowCount = sourceSheet.getLastRowNum() + 1;

        writer.println(":-encoding(utf8).");

        for (int currentRow = 1; currentRow < rowCount; currentRow++) {
            XSSFRow row = sourceSheet.getRow(currentRow);
            if (row == null) continue;

            Cell cell = row.getCell(0);

            if (cell != null) {
                String[] words = cell.toString().split(", ");

                for (String word : words) {
                    writer.println("keyword(" +
                            getPredicateFormWord(word) + ","
                            + currentRow+ ").");
                }
            }

            System.out.println(currentRow + "/" + rowCount + " keyword completed");
        }
        writer.close();

        System.out.println("Keywords stage completed");
    }

    private static void writeAllAssociationsPredicates(XSSFSheet sourceSheet) throws IOException {
        PrintWriter writer = new PrintWriter(
                COURSEWORK_FOLDER_PATH + "associations.pl",
                StandardCharsets.UTF_8);

        int rowCount = sourceSheet.getLastRowNum() + 1;

        writer.println(":-encoding(utf8).");

        for (int currentRow = 1; currentRow < rowCount; currentRow++) {
            XSSFRow row = sourceSheet.getRow(currentRow);
            if (row == null) continue;

            Cell cell = row.getCell(1);

            if (cell != null) {
                String[] words = cell.toString().split(", ");

                if (words.length != 0 && !words[0].equals("")) {
                    for (String word : words) {
                        writer.println("association(" +
                                getPredicateFormWord(word) + ","
                                + currentRow+ ").");
                    }
                }
            }

            System.out.println(currentRow + "/" + rowCount + " association completed");
        }
        writer.close();

        System.out.println("Associations stage completed");
    }

    private static void writeAllAnswersPredicates(XSSFSheet sourceSheet) throws IOException {
        PrintWriter writer = new PrintWriter(
                COURSEWORK_FOLDER_PATH + "answers.pl",
                StandardCharsets.UTF_8);

        int rowCount = sourceSheet.getLastRowNum() + 1;

        writer.println(":-dynamic answers/2.");
        writer.println(":-encoding(utf8).");

        for (int currentRow = 1; currentRow < rowCount; currentRow++) {
            XSSFRow row = sourceSheet.getRow(currentRow);
            if (row == null) continue;

            Cell cell = row.getCell(2);

            if (cell != null) {
                String[] answers = cell.toString().split("/");

                List res = new ArrayList();

                for (String answer : answers) {
                    res.add("\"" + answer + "\"");
                }

                writer.println("answers("
                        + currentRow + ", "
                        + res + ").");
            }

            System.out.println(currentRow + "/" + rowCount + " answer completed");
        }
        writer.close();

        System.out.println("Associations stage completed");
    }

    private static String getPredicateFormWord(String string) {
        String[] words = string.toLowerCase(Locale.ROOT)
                .trim()
                .split(" ");

        List<String> wordsWithoutEnding = new ArrayList<>();

        for (String word : words) {
            wordsWithoutEnding.add("\"" + getWordWithoutEnding(word) + "\"");
        }

        if (words.length == 1) {
            return wordsWithoutEnding.get(0);
        } else {
            return wordsWithoutEnding.toString();
        }
    }

    @SneakyThrows
    private static String getWordWithoutEnding(String word) {
        String urlString = "http://www.solarix.ru/php/stemming-provider.php?query=";
        urlString += URLEncoder.encode(word, WIN_ENCODING);

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        InputStreamReader isr = new InputStreamReader(conn.getInputStream(), WIN_ENCODING);
        BufferedReader in = new BufferedReader(isr);

        String inputLine;
        StringBuilder resultHtml = new StringBuilder();


        while ((inputLine = in.readLine()) != null)
                resultHtml.append(inputLine);

        String res = resultHtml.toString();


        if (res.length() < 3 && word.length() != res.length()) {
            if (CACHED_WORDS.containsKey(word)) {
                res = CACHED_WORDS.get(word);
            } else {
                Scanner scanner = new Scanner(System.in);
                System.out.println("=========WARNING============");
                System.out.println("word has length less than 3");
                System.out.println(word + " / " + res);
                res = scanner.nextLine();
                CACHED_WORDS.put(word, res);
            }
        }

        try(in) {
            return res;
        }
    }
}