package com.amairovi.cli;

import com.amairovi.Core;
import com.amairovi.cli.command.*;
import com.amairovi.cli.formatter.FeedConfigsFormatter;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.LogManager;

public class Cli {
    private final Scanner scanner;

    private final Map<String, CommandProcessor> commandToProcessor;

    private final UnknownCommandProcessor unknownCommandProcessor;

    public static void main(String[] args) {
        LogManager.getLogManager().reset();
        Core core = new Core();
        new Cli(core).start();
    }

    public Cli(Core core) {
        scanner = new Scanner(System.in);
        commandToProcessor = new HashMap<>();
        unknownCommandProcessor = new UnknownCommandProcessor();

        FeedConfigsFormatter feedConfigsFormatter = new FeedConfigsFormatter();

        commandToProcessor.put("list", new ListProcessor(core));
        commandToProcessor.put("create", new CreateProcessor(core, feedConfigsFormatter, scanner));
        commandToProcessor.put("stop", new StopProcessor(core));
        commandToProcessor.put("show", new ShowPropertyProcessor(core));
        commandToProcessor.put("hide", new HidePropertyProcessor(core));
        commandToProcessor.put("poll", new PollProcessor(core));
        commandToProcessor.put("set", new SetProcessor(core));
    }

    public void start() {

        while (true) {
            String commandLine = scanner.nextLine().trim();
            try {
                String[] params = commandLine.split(" ");

                String command = params[0];

                CommandProcessor processor = commandToProcessor.getOrDefault(command, unknownCommandProcessor);
                processor.process(params);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

//            String result;
//
//
//            switch (command) {
//                case "delete":
//                    id = toId(params[1]);
//                    core.delete(id);
//                    result = "success";
//                    break;
//                case "describe":
//                    id = toId(params[1]);
//                    result = core.describe(id);
//                    break;
//                case "translate":
//                    String filename = params[1];
//                    id = toId(params[2]);
//                    core.redirectFeedTo(id, filename);
//                    result = "success";
//                    break;
//            }
        }
    }

    private int toId(String str) {
        return Integer.valueOf(str);
    }
}
