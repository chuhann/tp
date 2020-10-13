package command.action;

import constants.Constants;
import org.junit.jupiter.api.Test;
import seedu.duke.Duke;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ReminderActionTest {

    @Test
    public void act_reminderInput_testOutput() {
        Duke d = new Duke(false, System.out, System.in, Constants.PATH, Constants.TEST_FILENAME);
        assertTrue(d.testSut("reminder", false, true).contains(Constants.REMINDER_HEAD));
    }
}