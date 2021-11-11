package Listners;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class AllureReportListner implements ITestListener {

        private static String getTestMethodName(ITestResult iTestResult) {
            return iTestResult.getMethod().getConstructorOrMethod().getName();
        }

        // Text attachments for Allure
        @Attachment(value = "{0}", type = "text/plain")
        public static String saveTextLog(String message) {
            return message;
        }

        // HTML attachments for Allure
        @Attachment(value = "{0}", type = "text/html")
        public static String attachHtml(String html) {
            return html;
        }

        @Override
        public void onStart(ITestContext iTestContext) {
            System.out.println("I am in onStart method " + iTestContext.getName());
//            iTestContext.setAttribute("WebDriver", BasePage.getDriver());
        }

        @Override
        public void onFinish(ITestContext iTestContext) {
            System.out.println("Run for the following Test Case just finished" + iTestContext.getName());
        }

        @Override
        public void onTestStart(ITestResult iTestResult) {
            System.out.println("Run for the following Test Case is just starting" + getTestMethodName(iTestResult));
        }

        @Override
        public void onTestSuccess(ITestResult iTestResult) {
            System.out.println("The following Test passed" + getTestMethodName(iTestResult));
        }

        @Override
        public void onTestFailure(ITestResult iTestResult) {
            System.out.println("The following Test Failed " + getTestMethodName(iTestResult));
            Object testClass = iTestResult.getInstance();

        }

        @Override
        public void onTestSkipped(ITestResult iTestResult) {
            System.out.println("I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
        }

        @Override
        public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
            System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
        }

}
