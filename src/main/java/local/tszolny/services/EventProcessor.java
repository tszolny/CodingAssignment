package local.tszolny.services;

import local.tszolny.entity.Event;
import local.tszolny.repositories.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class EventProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventProcessor.class);
    private final Map<String, Long> cache;
    private final EventRepository eventRepository;

    EventProcessor() {
        this(new EventRepository(), new ConcurrentHashMap<>());
    }

    EventProcessor(EventRepository eventRepository, Map<String, Long> cache) {
        this.eventRepository = eventRepository;
        this.cache = cache;
    }

    void processEvent(Event event){
        if(event == null){
            throw new IllegalArgumentException("Event cannot be null.");
        }

        String key = event.getId();
        Long cachedTimestamp = cache.putIfAbsent(key, event.getTimestamp());
        if(cachedTimestamp != null){
            LOGGER.debug("Found match in the map, processing data.");
            long tmstmpToCalculate = cache.remove(key);

            long eventDuration = Math.abs(event.getTimestamp() - tmstmpToCalculate);
            event.setDuration(eventDuration);
            if(eventDuration > 4){
                LOGGER.debug("eventDuration > 4, setting alert to true.");
                event.setAlert(true);
            }
            eventRepository.save(event);
        }
    }
}
