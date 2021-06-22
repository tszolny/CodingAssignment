package local.tszolny;

import local.tszolny.repositories.EventRepository;
import local.tszolny.services.FileProcessor;
import local.tszolny.utils.JpaUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class LogProcessingApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogProcessingApp.class);

    public static void main(String[] args) {
        if (ArrayUtils.isEmpty(args)){
            throw new IllegalArgumentException("Input file path is missing.");
        }
        LOGGER.info("Application processing server logs started.");
        String inputFilePath = args[0];
        FileProcessor fileProcessor = new FileProcessor();
        fileProcessor.processFile(inputFilePath);

        if(LOGGER.isDebugEnabled()){
            EventRepository eventRepository = new EventRepository();
            eventRepository.getEvents().forEach(event -> LOGGER.debug(event.toString()));
        }
        JpaUtil.closeEntityManagerFactory();
        LOGGER.info("Application processing server logs stopped.");
    }
}