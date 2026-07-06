package com.chargepoint.exporter;

import com.chargepoint.model.Product;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Test suite for DataExporter class.
 * Tests data export functionality for CSV and JSON formats.
 */
public class DataExporterTest {
    private DataExporter exporter;
    private List<Product> testProducts;
    private String csvTestFile = "test_products.csv";
    private String jsonTestFile = "test_products.json";

    @BeforeMethod
    public void setUp() {
        exporter = new DataExporter();
        testProducts = new ArrayList<>();
        testProducts.add(new Product("Boat Earphones", "₹3000", "₹2400", "20%"));
        testProducts.add(new Product("Sony Headphones", "₹5000", "₹4000", "20%"));
    }

    @AfterMethod
    public void tearDown() {
        // Clean up test files
        deleteFile(csvTestFile);
        deleteFile(jsonTestFile);
    }

    @Test
    public void testExportToCSV() {
        exporter.exportToCSV(testProducts, csvTestFile);
        assertTrue(Files.exists(Paths.get(csvTestFile)));
    }

    @Test
    public void testExportToCSVContent() throws IOException {
        exporter.exportToCSV(testProducts, csvTestFile);
        String content = new String(Files.readAllBytes(Paths.get(csvTestFile)));
        
        assertTrue(content.contains("Name,Original Price,Current Price,Discount"));
        assertTrue(content.contains("Boat Earphones"));
        assertTrue(content.contains("Sony Headphones"));
    }

    @Test
    public void testExportToCSVWithSpecialCharacters() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Product, with comma", "₹100", "₹80", "20%"));
        
        exporter.exportToCSV(products, csvTestFile);
        assertTrue(Files.exists(Paths.get(csvTestFile)));
    }

    @Test
    public void testExportToJSON() {
        exporter.exportToJSON(testProducts, jsonTestFile);
        assertTrue(Files.exists(Paths.get(jsonTestFile)));
    }

    @Test
    public void testExportToJSONContent() throws IOException {
        exporter.exportToJSON(testProducts, jsonTestFile);
        String content = new String(Files.readAllBytes(Paths.get(jsonTestFile)));
        
        assertTrue(content.contains("\"products\""));
        assertTrue(content.contains("Boat Earphones"));
        assertTrue(content.contains("Sony Headphones"));
        assertTrue(content.startsWith("{"));
        assertTrue(content.endsWith("}"));
    }

    @Test
    public void testExportToJSONStructure() throws IOException {
        exporter.exportToJSON(testProducts, jsonTestFile);
        String content = new String(Files.readAllBytes(Paths.get(jsonTestFile)));
        
        assertTrue(content.contains("\"name\""));
        assertTrue(content.contains("\"originalPrice\""));
        assertTrue(content.contains("\"currentPrice\""));
        assertTrue(content.contains("\"discount\""));
    }

    @Test
    public void testExportEmptyProductList() {
        exporter.exportToCSV(new ArrayList<>(), csvTestFile);
        assertTrue(Files.exists(Paths.get(csvTestFile)));
    }

    @Test
    public void testExportToJSONWithSpecialCharacters() throws IOException {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Product \"quoted\"", "₹100", "₹80", "20%"));
        
        exporter.exportToJSON(products, jsonTestFile);
        String content = new String(Files.readAllBytes(Paths.get(jsonTestFile)));
        assertTrue(content.contains("\\\""));
    }

    /**
     * Helper method to delete a file
     */
    private void deleteFile(String filename) {
        try {
            Files.deleteIfExists(Paths.get(filename));
        } catch (IOException e) {
            System.err.println("Failed to delete file: " + filename);
        }
    }
}