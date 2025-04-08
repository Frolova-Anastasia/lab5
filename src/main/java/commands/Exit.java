package commands;

import exceptions.WrongNumberOfArgsException;

public class Exit implements Command{
    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "завершает программу без сохранения в файл";
    }

    @Override
    public void execute(String commandArgs) {
        try{
            NumberArgsChecker.checkArgs(commandArgs, 0);
            System.exit(0);
        } catch (WrongNumberOfArgsException e){
            System.out.println(e.getMessage());
        }
    }
}
