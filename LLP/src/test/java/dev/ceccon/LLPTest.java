package dev.ceccon;

import dev.ceccon.config.LLMAPIConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LLPTest {

    @Test
    void cliOptionPortWithoutValueThrowsException() {
        String[] args = new String[]{"-p"};

        assertThrows(IllegalArgumentException.class, () -> {
            LLP.parseArguments(args, new LLMAPIConfig());
        });
    }

    @Test
    void cliOptionPortWithNonNumericValueThrowsException() {
        String port = "port";
        String[] args = new String[]{"-p", port};

        assertThrows(IllegalArgumentException.class, () -> {
            LLP.parseArguments(args, new LLMAPIConfig());
        });
    }

    @Test
    void cliOptionPortWithNumericValueSetsPortParameterOnApiConfig() {
        String port = "8089";
        String[] args = new String[]{"-p", port};

        LLMAPIConfig apiConfig = new LLMAPIConfig();

        LLP.parseArguments(args, apiConfig);

        String portOnApiConfig = apiConfig.getPort();

        assertEquals(port, portOnApiConfig);
    }

    @Test
    void cliOptionModelWithoutValueThrowsException() {
        String[] args = new String[]{"-m"};

        assertThrows(IllegalArgumentException.class, () -> {
            LLP.parseArguments(args, new LLMAPIConfig());
        });
    }

    @Test
    void cliOptionModelWithValueSetsModelParameterOnApiConfig() {
        String model = "test-model";
        String[] args = new String[]{"-m", model};

        LLMAPIConfig apiConfig = new LLMAPIConfig();

        LLP.parseArguments(args, apiConfig);

        String modelOnApiConfig = apiConfig.getModel();

        assertEquals(model, modelOnApiConfig);
    }

    @Test
    void cliOptionTemperatureWithoutValueThrowsException() {
        String[] args = new String[]{"-t"};

        assertThrows(IllegalArgumentException.class, () -> {
            LLP.parseArguments(args, new LLMAPIConfig());
        });
    }

    @Test
    void cliOptionTemperatureWithNonNumericValueThrowsException() {
        String temperature = "temperature";
        String[] args = new String[]{"-t", temperature};

        assertThrows(IllegalArgumentException.class, () -> {
            LLP.parseArguments(args, new LLMAPIConfig());
        });
    }

    @Test
    void cliOptionTemperatureWithWholeNumberSetsTemperatureOnApiConfig() {
        String temperature = "5";
        String[] args = new String[]{"-t", temperature};

        LLMAPIConfig apiConfig = new LLMAPIConfig();

        LLP.parseArguments(args, apiConfig);

        double temperatureOnApiConfig = apiConfig.getTemperature();

        assertEquals(Double.valueOf(temperature), temperatureOnApiConfig);
    }

    @Test
    void cliOptionTemperatureWithDecimalNumberSetsTemperatureOnApiConfig() {
        String temperature = "0.3";
        String[] args = new String[]{"-t", temperature};

        LLMAPIConfig apiConfig = new LLMAPIConfig();

        LLP.parseArguments(args, apiConfig);

        double temperatureOnApiConfig = apiConfig.getTemperature();

        assertEquals(Double.valueOf(temperature), temperatureOnApiConfig);
    }

    @Test
    void cliOptionInexistentThrowsException() {
        String[] args = new String[]{"-thisisnotanoption"};

        assertThrows(IllegalArgumentException.class, () -> {
            LLP.parseArguments(args, new LLMAPIConfig());
        });
    }

}
