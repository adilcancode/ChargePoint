import java.io.*;
import java.util.*;

public class SearchConfiguration {
    
    private static final String CONFIG_FILE = "search.properties";
    private static final String QUERIES_KEY = "search.queries";

    /**
     * Load search queries from command-line arguments or configuration file
     * 
     * Priority:
     * 1. Command-line arguments (each argument is a separate search query)
     * 2. Configuration file (search.properties)
     * 3. Empty queue (caller should handle with default)
     * 
     * @param args Command-line arguments
     * @return Queue of search queries
     */
    public static Queue<String> loadSearchQueries(String[] args) {
        Queue<String> queries = new LinkedList<>();

        // Priority 1: Load from command-line arguments
        if (args != null && args.length > 0) {
            for (String arg : args) {
                if (arg != null && !arg.trim().isEmpty()) {
                    queries.add(arg.trim());
                }
            }
            if (!queries.isEmpty()) {
                System.out.println("Loaded " + queries.size() + " search queries from command-line arguments");
                return queries;
            }
        }

        // Priority 2: Load from configuration file
        queries = loadFromConfigFile();
        if (!queries.isEmpty()) {
            System.out.println("Loaded " + queries.size() + " search queries from configuration file");
            return queries;
        }

        System.out.println("No search queries provided via arguments or configuration file");
        return queries;
    }

    /**
     * Load search queries from properties file
     * 
     * File format:
     * search.queries=query1,query2,query3
     * 
     * @return Queue of search queries
     */
    private static Queue<String> loadFromConfigFile() {
        Queue<String> queries = new LinkedList<>();
        
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            Properties prop = new Properties();
            prop.load(input);
            
            String queriesString = prop.getProperty(QUERIES_KEY, "").trim();
            if (!queriesString.isEmpty()) {
                String[] queryArray = queriesString.split(",");
                for (String query : queryArray) {
                    String trimmedQuery = query.trim();
                    if (!trimmedQuery.isEmpty()) {
                        queries.add(trimmedQuery);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Configuration file not found: " + CONFIG_FILE);
        } catch (IOException e) {
            System.err.println("Error reading configuration file: " + e.getMessage());
        }
        
        return queries;
    }

    /**
     * Save search queries to properties file
     * 
     * @param queries Queue of search queries to save
     */
    public static void saveQueriesToConfigFile(Queue<String> queries) {
        if (queries == null || queries.isEmpty()) {
            System.out.println("No queries to save");
            return;
        }

        try (OutputStream output = new FileOutputStream(CONFIG_FILE)) {
            Properties prop = new Properties();
            
            StringBuilder sb = new StringBuilder();
            Iterator<String> iterator = queries.iterator();
            while (iterator.hasNext()) {
                sb.append(iterator.next());
                if (iterator.hasNext()) {
                    sb.append(", ");
                }
            }
            
            prop.setProperty(QUERIES_KEY, sb.toString());
            prop.store(output, "ChargePoint Search Queries Configuration");
            System.out.println("Search queries saved to " + CONFIG_FILE);
        } catch (IOException e) {
            System.err.println("Error saving configuration file: " + e.getMessage());
        }
    }
}
