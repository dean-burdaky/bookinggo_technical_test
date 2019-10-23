package application.integration;

import application.CommandLineArguments;
import application.ConsoleApplication;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ConsoleApplicationRunTest
{
    public static final String[] TEST_ARGS = {"0.014531", "25.67546", "-90", "136.25", "5"};

    @Test
    void testRun()
    {
        CommandLineArguments cla = new CommandLineArguments(System.err, TEST_ARGS);
        ConsoleApplication app = new ConsoleApplication(cla, System.out);
        assertDoesNotThrow(app::run);
    }
}
