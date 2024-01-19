package drintau.accountmanager.assist;

/**
 * 启动参数处理程序
 */
public class LaunchArgsHandler {

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
