package command.action;

import command.ParamNode;
import constants.Constants;
import data.Data;
import exceptions.CommandException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Focus action.
 */
public class FocusAction extends Action {
    private static final Logger LOGGER = Logger.getLogger(FocusAction.class.getName());
    private String typeTask;

    @Override
    public String act(Data data) throws Exception {
        // LOGGER.entering(getClass().getName(), "changeFocus");
        // LOGGER.log(Level.INFO, "Type changed");
        data.setFlag(typeTask);
        String output = super.act(data);
        // LOGGER.exiting(getClass().getName(), "changeFocus");
        return output.replace(Constants.TEXT_PLACEHOLDER, typeTask);
    }

    @Override
    public void prepare(ParamNode args) throws Exception {
        super.prepare(args);
        if (args.thisData == null) {
            typeTask = Constants.TASK;
            assert typeTask == Constants.TASK : "type should be task";
        } else {
            typeTask = args.thisData.name;
            String[] options = Constants.optionalParamMap.get(args.name);
            int count = 0;
            for (String opt : options) {
                if (opt.equals(typeTask)) {
                    break;
                } else {
                    count++;
                }
            }
            if (count == options.length) {
                throw new CommandException();
            }
        }
    }
}
