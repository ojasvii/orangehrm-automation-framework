package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String reportPath = "reports/ExtentReport.html";
            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
            reporter.config().setDocumentTitle("Automation Report");
            reporter.config().setReportName("OrangeHRM Test Execution");

            extent = new ExtentReports();
            extent.attachReporter(reporter);
        }
        return extent;
    }
}
