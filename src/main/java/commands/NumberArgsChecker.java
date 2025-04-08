package commands;

import exceptions.WrongNumberOfArgsException;

public class NumberArgsChecker {
    public static void checkArgs(String command, int Args) throws WrongNumberOfArgsException {
        boolean hasArgument = (command != null && !command.trim().isEmpty());
        if(Args == 0 && hasArgument || Args > 0 && !hasArgument){
            throw new WrongNumberOfArgsException("Эта команда принимает " + Args + " аргумент");
        }
    }
}
