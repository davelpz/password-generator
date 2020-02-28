package passgen;

import picocli.CommandLine;

public class Main {
	public static void main(String[] args) {
        CommandLine cmdLine = new CommandLine(new PasswordGenerator());
        
        int exitCode = cmdLine.execute(args);
        System.exit(exitCode);
	}

}
