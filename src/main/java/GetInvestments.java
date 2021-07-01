import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.sql.*;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class GetInvestments {

    public static void waitFor(WebDriverWait wait, By by) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (TimeoutException e) {
            System.out.println("Page took too long to load");
            e.printStackTrace();
        }
    }

    public static void createDatabase() throws Exception {

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:investmentsdb.sqlite");
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE investments ("
                    + "date STRING,"
                    + "obligations REAL, "
                    + "obligations_units REAL, "
                    + "equity REAL, "
                    + "equity_units REAL, "
                    + "total REAL, "
                    + "unique (date))";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error while creating new table");
            e.printStackTrace();
        }

    }

    public static Connection connectDatabase(Connection conn) throws SQLException {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:C:\\users\\dangoth\\Desktop\\investmentsdb.sqlite");
        } catch (SQLException e) {
            System.out.println("SQL error while connecting to db");
            e.printStackTrace();
        }
        return conn;
    }


    public static void main(String[] args) throws Exception {

        Class.forName("org.sqlite.JDBC");
        // set chrome driver path
        String chromeDriverPath = "C:\\Users\\dangoth\\Desktop\\chromedriver_win32\\chromedriver.exe\\";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);

        String url = "https://sti24.tfi.bnpparibas.pl/client/login";

        driver.get(url);
        waitFor(wait, By.id("submitLogin"));

        driver.findElement(By.id("user-login")).sendKeys(Credentials.username);
        driver.findElement(By.id("user-password")).sendKeys(Credentials.password);
        driver.findElement(By.id("submitLogin")).click();
        waitFor(wait, By.className("register-detail"));

        driver.findElement(By.className("register-detail")).click();
        waitFor(wait, By.className("ui-table-tbody"));

        String[] table = driver.findElement(By.className("ui-table-tbody")).getText().split("\n");

        // parse data
        Float[] equity = Parser.parseEquity(table);
        Float[] obligations = Parser.parseObligations(table);
        Float total = equity[0] + obligations[0];


        File file = new File("C:\\Users\\dangoth\\Desktop\\investmentsdb.sqlite");
        if (!file.exists()) {
            createDatabase();
        }

        Connection conn = null;
        conn = connectDatabase(conn);
        System.out.println("Done creating table");
        Statement stmt = conn.createStatement();


        try {
            String data = "INSERT INTO investments (date, obligations, obligations_units, equity, equity_units, total) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(data);
            pstmt.setString(1, String.valueOf(LocalDate.now()));
            pstmt.setFloat(2, obligations[0]);
            pstmt.setFloat(3, obligations[1]);
            pstmt.setFloat(4, equity[0]);
            pstmt.setFloat(5, equity[1]);
            pstmt.setFloat(6, total);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while inserting the data");
            e.printStackTrace();

        } finally {
            stmt.close();
            conn.close();
            driver.quit();
        }
    }
}
