package command.action;

import command.Command;
import command.ParamNode;
import constants.Constants;
import data.Data;
import data.Item;
import data.SingleModule;
import data.jobs.Task;
import exceptions.CommandException;
import exceptions.ModuleNotFoundException;

import java.util.ArrayList;

public class StatsAction extends Action {
    boolean isMod = false;
    String userInput = "";
    String selectedModule;
    SingleModule tempModule = null;
    int doneItem = 0;

    @Override
    public String act(Data data) throws Exception {
        StringBuilder builder = new StringBuilder(Constants.STATS_HEAD);
        ArrayList<Item> targetList = new ArrayList<>(data.getTarget());

        if (isMod) {
            targetList = new ArrayList<>(data.mods);
            for (Item item : targetList) {
                if (item.getName().toLowerCase().equals(selectedModule.toLowerCase())){
                    tempModule = (SingleModule)item;
                }
            }
            if(tempModule == null){
                throw new ModuleNotFoundException();
            }
            for(Item item : tempModule.getTaskList()){
                Task t = (Task)item;
                if (t.getStatusIcon().equals(Constants.TICK)){
                    doneItem+=1;
                }
            }
            builder.append((double)doneItem/tempModule.getTaskList().size()).append(Constants.WIN_NEWLINE);
        } else {
            if(targetList == null){
                throw new ModuleNotFoundException();
            }
            else {
                for(Item item : targetList){
                    Task t = (Task)item;
                    if (t.getStatusIcon().equals(Constants.TICK)){
                        doneItem+=1;
                    }
                }
            }
            builder.append((double)doneItem/tempModule.getTaskList().size()).append(Constants.WIN_NEWLINE);
        }
        return builder.toString();

    }

    /**
     * Picking up optional parameter and check if user entered.
     *
     * @param args the args
     * @throws Exception to handle prepare exceptions.
     */
    @Override
    public void prepare(ParamNode args) throws Exception {
        super.prepare(args);
        int len = flattenedArgs.length;

        if (len == 0) {
            isMod = false;
            userInput = "";
        } else {
            userInput = flattenedArgs[0].toFlatString();
            String[] optionalParams = Constants.optionalParamMap.get(args.name);
            String mod = optionalParams[0];
            isMod = userInput.equals(mod);

            if (isMod) {
                selectedModule =  flattenedArgs[1].toFlatString();
                isMod = true;
                userInput = "";
            } else {
                throw new CommandException();
            }
        }

    }

}
