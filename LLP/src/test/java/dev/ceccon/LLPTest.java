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

        String modelOnApi = apiConfig.getModel();

        assertEquals(model, modelOnApi);
    }

}
