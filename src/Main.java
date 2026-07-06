import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Main {

    private static final String DEFAULT_SEARCH_TERM = "boat blue";

    public static void main(String[] args) throws InterruptedException {
        // Get search term from arguments, configuration, or use default
        Queue<String> searchQueries = SearchConfiguration.loadSearchQueries(args);
        
        if (searchQueries.isEmpty()) {
            System.out.println("No search queries found. Using default: " + DEFAULT_SEARCH_TERM);
            searchQueries.add(DEFAULT_SEARCH_TERM);
        }

        // Process each search query in the queue
        while (!searchQueries.isEmpty()) {
            String searchTerm = searchQueries.poll();
            
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                System.out.println("Warning: Skipping empty search term");
                continue;
            }
            
            System.out.println("\n========================================");
            System.out.println("Processing search: " + searchTerm);
            System.out.println("========================================");
            
            try {
                performSearch(searchTerm);
            } catch (Exception e) {
                System.err.println("Error during search for '" + searchTerm + "': " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void performSearch(String searchTerm) throws InterruptedException {
        FirefoxDriver driver = new FirefoxDriver();
        
        try {
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(10, SECONDS);
            driver.get("https://www.flipkart.com/");

            // Close modal if present
            try {
                driver.findElement(By.xpath("//div[@class='_2QfC02']/button")).click();
            } catch (Exception e) {
                System.out.println("Modal close button not found or already closed");
            }
            
            // Perform search
            try {
                WebElement searchBox = driver.findElement(By.xpath("//input[@name='q']"));
                searchBox.sendKeys(searchTerm);
                
                List<WebElement> searchList = driver.findElements(By.xpath("//ul[contains(@class,'col-12-12')]//li/div/a/div[2]"));

                if (searchList.isEmpty()) {
                    System.out.println("No search suggestions found for: " + searchTerm);
                    return;
                }

                Actions actions = new Actions(driver);
                Thread.sleep(1000);
                actions.moveToElement(searchList.get(searchList.size() - 1)).click().build().perform();

                System.out.println("FAssured Product Information for: " + searchTerm);
                HashMap<String, String> model = new HashMap<>();
                
                List<WebElement> fAssuredNames = driver.findElements(By.xpath("//div[@class='_32g5_j']/preceding-sibling::a[1]"));
                List<WebElement> fAssuredOPrice = driver.findElements(By.xpath("//div[@class='_32g5_j']/following-sibling::a[1]/div[1]/div[1]"));
                List<WebElement> fAssuredCPrice = driver.findElements(By.xpath("//div[@class='_32g5_j']/following-sibling::a[1]/div/div[2]"));
                List<WebElement> fAssuredDiscount = driver.findElements(By.xpath("//div[@class='_32g5_j']/following-sibling::a[1]/div/div[3]"));
                
                int itemCount = Math.min(10, Math.min(Math.min(fAssuredNames.size(), fAssuredOPrice.size()), 
                                                       Math.min(fAssuredCPrice.size(), fAssuredDiscount.size())));
                
                if (itemCount == 0) {
                    System.out.println("No FAssured products found for: " + searchTerm);
                    return;
                }
                
                for (int i = 1; i <= itemCount && i < 11; i++) {
                    try {
                        System.out.print("Model " + i + ": ");
                        model.put("Name", fAssuredNames.get(i).getText());
                        model.put("Original Price", fAssuredOPrice.get(i).getText());
                        model.put("Current Price", fAssuredCPrice.get(i).getText());
                        model.put("Discount", fAssuredDiscount.get(i).getText());
                        System.out.println(model);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Reached end of available products");
                        break;
                    }
                }
            } catch (Exception e) {
                System.err.println("Error during product extraction: " + e.getMessage());
                e.printStackTrace();
            }
        } finally {
            driver.quit();
        }
    }
}
