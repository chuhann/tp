package command.action;

import constants.Constants;
import org.junit.jupiter.api.Test;
import seedu.duke.Duke;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Focus Action Test.
 */
class FocusActionTest {
    private String[] testCommand = {"focus", "focus deadline", "focus todo", "focus event", "focus task"};

    @Test
    void act_customFocusInput_testInput() {
        Duke d = new Duke(false, System.out, System.in, Constants.PATH, Constants.FILENAME);
        assertAll("FocusActionTest",
            () -> assertTrue(d.testSut(testCommand[0]).equals("Now we are focusing on:\r\ntask")),
            () -> assertTrue(d.testSut(testCommand[1]).equals("Now we are focusing on:\r\ndeadline")),
            () -> assertTrue(d.testSut(testCommand[2]).equals("Now we are focusing on:\r\ntodo")),
            () -> assertTrue(d.testSut(testCommand[3]).equals("Now we are focusing on:\r\nevent")),
            () -> assertTrue(d.testSut(testCommand[4]).equals("Now we are focusing on:\r\ntask"))
        );
    }

}