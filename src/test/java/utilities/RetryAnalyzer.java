package utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    // Number of retries
    private int retryCount = 0;  

    // Maximum number of retries
    private static final int maxRetryCount = 2; 

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            return true;  // Retry the failed test
        }
        return false;  // Do not retry after max attempts
    }
}
