package local.tszolny.services;

import com.google.gson.Gson;
import local.tszolny.entity.Event;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileProcessor.class);
    private final Gson gson = new Gson();
    private final EventProcessor eventProcessor;

    public FileProcessor(){
        this(new EventProcessor());
    }

    public FileProcessor(EventProcessor eventProcessor){
        this.eventProcessor = eventProcessor;
    }


    public void processFile(String inputFilePath){
        if(StringUtils.isBlank(inputFilePath)){
            throw new IllegalArgumentException("File path cannot be empty.");
        }
        Path path = Paths.get(inputFilePath);

        LOGGER.info("Processing file {}", inputFilePath);
        try (Stream<String> lines = Files.lines(path).parallel()) {
            lines.forEach(s -> {
                Event event = gson.fromJson(s, Event.class);
                LOGGER.debug("Processing event {}", event.getId());
                eventProcessor.processEvent(event);
            });
        } catch (IOException ex) {
            LOGGER.error("Can not read the input file.", ex);
        }

    }
}
