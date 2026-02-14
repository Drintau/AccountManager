package drintau.accountmanager.launcher;

/**
 * 启动参数处理程序
 */
public class ArgsHandler {

    public void execute(String[] args) {
        ArgHandlerFactory argHandlerFactory = new ArgHandlerFactory();
        for (String arg : args) {
            if (arg.startsWith("--")) {
                String optionText = arg.substring(2);
                ArgHandlerInterface argHandler = argHandlerFactory.create(optionText);
                if (argHandler != null) {
                    argHandler.execute();
                }
            }
        }
    }

}
