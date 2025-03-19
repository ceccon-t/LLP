package dev.ceccon;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import dev.ceccon.config.LLMAPIConfig;
import dev.ceccon.conversation.Chat;
import dev.ceccon.gui.MainView;

import javax.swing.*;

public class LLP {
    public static void main( String[] args ) {
        System.out.println( "Launching LLP - Local Language Practice application." );

        SwingUtilities.invokeLater(() -> {
            new MainView();
        });
    }

    public static void parseArguments(String[] args, LLMAPIConfig apiConfig) throws IllegalArgumentException {
        LLPArgs llpArgs = new LLPArgs();
        JCommander cmd = JCommander.newBuilder()
                .addObject(llpArgs)
                .build();

        try {
            cmd.parse(args);
        } catch(ParameterException e) {
            throw new IllegalArgumentException("Could not parse parameter.");
        }

        if (llpArgs.hasPort()) {
            Integer port = llpArgs.getPort();
            apiConfig.setPort(port.toString());
        }

    }

    static class LLPArgs {
        @Parameter(
                names = {"-p", "--port"},
                description = "LLM server port",
                required = false,
                arity = 1
        )
        private Integer port;

        public Integer getPort() {
            return port;
        }

        public boolean hasPort() {
            return port != null;
        }
    }
}
