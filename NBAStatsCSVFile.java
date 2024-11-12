/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.nbastatscsvfile;

/**
 *
 * @author Bruno
 */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NBAStatsCSVFile {

    public static void main(String[] args) {
        String url = "https://www.basketball-reference.com/leagues/NBA_2025_per_game.html"; // Per Game Stats
        String csvFile = "player_stats.csv";

        try {
            // Connect to the URL and fetch the HTML document
            Document doc = Jsoup.connect(url).get();

            // Locate the stats table (e.g., "per_game" table)
            Element table = doc.getElementById("div_per_game_stats");

            // Check if the table exists
            if (table == null) {
                System.out.println("Table not found.");
                return;
            }

            // Retrieve headers and rows for CSV
            List<String[]> data = new ArrayList<>();
            
            // Extract headers
            Elements headers = table.select("thead tr th");
            List<String> headerList = new ArrayList<>();
            for (Element header : headers) {
                headerList.add(header.text());
            }
            data.add(headerList.toArray(new String[0])); // Add headers as the first row in CSV

            // Extract rows
            Elements rows = table.select("tbody tr");
            for (Element row : rows) {
                Elements columns = row.select("td");
                List<String> rowData = new ArrayList<>();
                for (Element column : columns) {
                    rowData.add(column.text()); // Add each cell text to row
                }
                data.add(rowData.toArray(new String[0])); // Add row data to CSV
            }

            // Write data to CSV file using OpenCSV
            try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile))) {
                writer.writeAll(data); // Write all data rows to the CSV
                System.out.println("Data saved to " + csvFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

