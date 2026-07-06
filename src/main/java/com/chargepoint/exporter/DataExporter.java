package com.chargepoint.exporter;

import com.chargepoint.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Exports product data to various formats.
 * Handles data export functionality.
 */
public class DataExporter {
    private static final Logger logger = LoggerFactory.getLogger(DataExporter.class);

    /**
     * Export products to CSV file
     * @param products the list of products to export
     * @param filename the output filename
     */
    public void exportToCSV(List<Product> products, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            // Write header
            writer.write("Name,Original Price,Current Price,Discount\n");
            
            // Write data
            for (Product product : products) {
                writer.write(escapeCSV(product.getName()) + "," +
                        escapeCSV(product.getOriginalPrice()) + "," +
                        escapeCSV(product.getCurrentPrice()) + "," +
                        escapeCSV(product.getDiscount()) + "\n");
            }
            
            logger.info("Data exported to CSV: " + filename);
        } catch (IOException e) {
            logger.error("Error exporting data to CSV", e);
            throw new RuntimeException("Failed to export data to CSV", e);
        }
    }

    /**
     * Export products to JSON format (simple format)
     * @param products the list of products to export
     * @param filename the output filename
     */
    public void exportToJSON(List<Product> products, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("{\n  \"products\": [\n");
            
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                writer.write("    {\n");
                writer.write("      \"name\": \"" + escapeJSON(product.getName()) + "\",\n");
                writer.write("      \"originalPrice\": \"" + escapeJSON(product.getOriginalPrice()) + "\",\n");
                writer.write("      \"currentPrice\": \"" + escapeJSON(product.getCurrentPrice()) + "\",\n");
                writer.write("      \"discount\": \"" + escapeJSON(product.getDiscount()) + "\"\n");
                writer.write("    }");
                if (i < products.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }
            
            writer.write("  ]\n}");
            logger.info("Data exported to JSON: " + filename);
        } catch (IOException e) {
            logger.error("Error exporting data to JSON", e);
            throw new RuntimeException("Failed to export data to JSON", e);
        }
    }

    /**
     * Escape special characters for CSV
     * @param value the string value to escape
     * @return escaped value
     */
    private String escapeCSV(String value) {
        if (value == null) {
            return "";
        }
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    /**
     * Escape special characters for JSON
     * @param value the string value to escape
     * @return escaped value
     */
    private String escapeJSON(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}