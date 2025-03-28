package dev.ceccon;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import dev.ceccon.config.LLMAPIConfig;
import dev.ceccon.config.PracticeSessionConfig;
import dev.ceccon.gui.MainView;

import javax.swing.*;

public class LLP {
    public static void main( String[] args ) {
        System.out.println( "Launching LLP - Local Language Practice application." );

        LLMAPIConfig llmapiConfig = new LLMAPIConfig();
        parseArguments(args, llmapiConfig);

        PracticeSessionConfig sessionConfig = PracticeSessionConfig.getInstance();
        sessionConfig.setLlmApiConfig(llmapiConfig);

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

        if (llpArgs.hasModel()) {
            String model = llpArgs.getModel();
            apiConfig.setModel(model);
        }

        if (llpArgs.hasTemperature()) {
            Double temperature = llpArgs.getTemperature();
            apiConfig.setTemperature(temperature);
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

        @Parameter(
                names = {"-m", "--model"},
                description = "LLM server model",
                required = false,
                arity = 1
        )
        private String model;

        @Parameter(
                names = {"-t", "--temperature"},
                description = "Temperature value to use when generating answers.",
                required = false,
                arity = 1
        )
        private Double temperature;

        public Integer getPort() {
            return port;
        }

        public String getModel() {
            return model;
        }

        public Double getTemperature() {
            return temperature;
        }

        public boolean hasPort() {
            return port != null;
        }

        public boolean hasModel() {
            return model != null;
        }

        public boolean hasTemperature() {
            return temperature != null;
        }

    }
}
