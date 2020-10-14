package command.action;

import command.ParamNode;
import constants.Constants;

import data.Item;
import data.SingleModule;
import data.Data;
import exceptions.GradeNotSpecifiedException;
import exceptions.ModuleNotFoundException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Calculate user's cap.
 */
public class CalculateCapAction extends Action {

    private HashMap<String, Double> modulesWithGrades = new HashMap<>();
    private boolean isCustom = true;

    @Override
    public String act(Data data) throws Exception {
        assert data != null : "Data cannot be null";
        double totalScore = 0;
        double totalMC = 0;
        if (isCustom) {
            for (Map.Entry<String, Double> m : modulesWithGrades.entrySet()) {
                Double grade = m.getValue();
                String moduleCode = m.getKey();
                SingleModule module = matchModule(moduleCode, data.mods);
                if (module == null) {
                    throw new ModuleNotFoundException();
                }
                totalMC += Double.parseDouble(module.getModuleMC());
                totalScore += Double.parseDouble(module.getModuleMC()) * grade;
            }
        } else {
            for (Item item : data.mods) {
                SingleModule module = (SingleModule) item;
                if (module.isTaken) {
                    if (module.grade == null) {
                        throw new GradeNotSpecifiedException();
                    }
                    double gradeValue = numerateGrade(module.grade.toUpperCase());
                    totalMC += Double.parseDouble(module.getModuleMC());
                    totalScore += Double.parseDouble(module.getModuleMC()) * gradeValue;
                }
            }
        }
        double capValue = totalScore / totalMC;
        assert capValue > 0 : "CAP need to be more than zero";
        return Constants.SHOW_CAP + new DecimalFormat("#.##").format(capValue);
    }

    @Override
    public void prepare(ParamNode args) throws Exception {
        super.prepare(args);
        ParamNode currData;

        //extract modules name
        currData = flattenedArgs[0];
        //input custom modules
        if (flattenedArgs[0].name.equals("m")) {
            isCustom = true;
            while (currData.thisData != null) {
                String moduleCode = currData.thisData.name.toUpperCase();
                Double grade = numerateGrade(currData.thisData.thisData.name.toUpperCase());
                modulesWithGrades.put(moduleCode, grade);
                currData = currData.thisData.thisData;
            }
        } else {
            // retrieve module data from user data
            isCustom = false;
        }
    }

    /**
     * Match module code to module.
     *
     * @param moduleCode input module code
     * @param mods module list
     * @return module selected
     */
    private SingleModule matchModule(String moduleCode, ArrayList<Item> mods) {
        for (Item item : mods) {
            SingleModule module = (SingleModule) item;
            if (moduleCode.equals(module.getName())) {
                return module;
            }
        }
        return null;
    }

    /**
     * Turn grade alphabet to numbers.
     *
     * @param grade grade alphabet
     * @return grade value in number
     */
    private double numerateGrade(String grade) throws GradeNotSpecifiedException {
        double gradeValue;
        assert grade != "" : "Grade should not be empty";
        switch (grade) {
        case "A+":
        case "A":
            gradeValue = 5;
            break;
        case "A-":
            gradeValue = 4.5;
            break;
        case "B+":
            gradeValue = 4;
            break;
        case "B":
            gradeValue = 3.5;
            break;
        case "B-":
            gradeValue = 3;
            break;
        case "C+":
            gradeValue = 2.5;
            break;
        case "C":
            gradeValue = 2;
            break;
        case "D+":
            gradeValue = 1.5;
            break;
        case "D":
            gradeValue = 1;
            break;
        default:
            throw new GradeNotSpecifiedException();
        }
        return gradeValue;
    }
}
