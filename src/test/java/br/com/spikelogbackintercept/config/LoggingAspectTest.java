package br.com.spikelogbackintercept.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoggingAspectTest {

    public static final String JSON_EMPTY = "{}";
    public static final String JSON_MASKED = "{***}";
    @Autowired
    LoggingAspect loggingAspect;

    @Autowired
    ObjectMapper mapper;

    private String getJsonExpected(String value) throws JsonProcessingException {
        return mapper.writeValueAsString(value).replaceAll("\"", "");
    }
}