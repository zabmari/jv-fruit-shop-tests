package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.ReportGenerationServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGenerationServiceImplTest {

    private final ReportGenerationServiceImpl reportGenerationServiceImplTest =
            new ReportGenerationServiceImpl();
    private Map<String, Integer> fruits;

    @BeforeEach
    void setUp() {
        fruits = new HashMap<>();
    }

    @Test
    void generateReport_emptyStorage_shouldReturnEmptyReport() {
        String actualReport = reportGenerationServiceImplTest.generateReport(fruits);
        String expectedReport = "fruit,quantity\n";
        assertEquals(expectedReport, actualReport,
                "Report for empty storage should be empty");
    }

    @Test
    void generateReport_singleFruit_shouldReturnCorrectReport() {
        fruits.put("apple", 10);
        String actualReport = reportGenerationServiceImplTest.generateReport(fruits);
        String expectedReport = "fruit,quantity\napple,10\n";
        assertEquals(expectedReport, actualReport,
                "Report for one fruit should be correct");
    }

    @Test
    void generateReport_multipleFruits_shouldReturnCorrectReport() {
        fruits.put("raspberry", 50);
        fruits.put("strawberry", 100);
        String actualReport = reportGenerationServiceImplTest.generateReport(fruits);
        String expectedReportFirstLine = "raspberry,50\n";
        String expectedReportSecondLine = "strawberry,100\n";

        assertTrue(actualReport.contains(expectedReportFirstLine),
                "Report should contain raspberry");
        assertTrue(actualReport.contains(expectedReportSecondLine),
                "Report should contain strawberry");
    }
}
