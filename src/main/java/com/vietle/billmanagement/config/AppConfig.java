package com.vietle.billmanagement.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.*;

@Configuration
@Data
public class AppConfig {
    @Value("${bill.directory}")
    private String fileDirectory;

    @Value("${bill.file.name}")
    private String fileName;

    public ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter();
        return objectMapper;
    }

    public BufferedWriter getBufferedWriter(String fullFilePathWithName) throws IOException {
       return new BufferedWriter(new FileWriter(fullFilePathWithName));
    }

    public BufferedReader getBufferedReader(String fullFilePathWithName) throws IOException {
        return new BufferedReader(new FileReader(fullFilePathWithName));
    }
}
