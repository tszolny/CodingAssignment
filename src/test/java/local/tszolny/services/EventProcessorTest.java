package local.tszolny.services;

import local.tszolny.entity.Event;
import local.tszolny.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class EventProcessorTest {
    private EventRepository eventRepository;
    private Map<String, Long> cache;
    private EventProcessor eventProcessor;

    @BeforeEach
    private void setup(){
        eventRepository = Mockito.mock(EventRepository.class);
        cache = new HashMap<>();

        eventProcessor = new EventProcessor(eventRepository, cache);
    }


    @Test
    void processEvent_ShouldThrowExceptionWhenEventIsNull(){
        assertThrows(IllegalArgumentException.class, () -> eventProcessor.processEvent(null));
    }

    @Test
    void processEvent_ShouldCacheFirstEventForIdAndDoesNotSaveIt(){
        //given
        Event event = new Event();
        event.setId("abcd");
        event.setTimestamp(1491377495213L);

        //when
        eventProcessor.processEvent(event);

        //then
        assertTrue(cache.containsKey(event.getId()));
        assertEquals(event.getTimestamp(), cache.get(event.getId()).longValue());
        verify(eventRepository, never()).save(event);
    }

    @Test
    void processEvent_ShouldRemoveCacheEntryAndSaveEventWhenPairFound(){
        //given
        String id = "abcd";

        Event event1 = new Event();
        event1.setId(id);

        Event event2 = new Event();
        event2.setId(id);

        //when
        eventProcessor.processEvent(event1);
        eventProcessor.processEvent(event2);

        //then
        assertFalse(cache.containsKey(id));
        verify(eventRepository).save(event2);
    }


    @Test
    void processEvent_ShouldSaveEventProperties(){
        //given
        Event event1 = createEvent(497, "STARTED");
        Event event2 = createEvent(500, "FINISHED");

        //when
        eventProcessor.processEvent(event1);
        eventProcessor.processEvent(event2);

        //then
        verify(eventRepository).save(event2);
        assertEquals("abcd", event2.getId());
        assertEquals("APPLICATION_LOG", event2.getType());
        assertEquals("host1234", event2.getHost());
        assertEquals(500L, event2.getTimestamp());
        assertEquals("FINISHED", event2.getState());
    }

    @Test
    void processEvent_ShouldSetAlertToTrueWhenDurationMoreThan4(){
        //given
        Event event1 = createEvent(495, "STARTED");
        Event event2 = createEvent(500, "FINISHED");

        //when
        eventProcessor.processEvent(event1);
        eventProcessor.processEvent(event2);

        //then
        assertTrue(event2.isAlert());
    }

    @Test
    void processEvent_ShouldSetAlertToFalseWhenDurationLessOrEqual4(){
        //given
        Event event1 = createEvent(496, "STARTED");
        Event event2 = createEvent(500, "FINISHED");

        //when
        eventProcessor.processEvent(event1);
        eventProcessor.processEvent(event2);

        //then
        assertFalse(event2.isAlert());
    }

    private Event createEvent(long timestamp, String state){
        Event event = new Event();
        event.setId("abcd");
        event.setType("APPLICATION_LOG");
        event.setHost("host1234");
        event.setTimestamp(timestamp);
        event.setState(state);

        return event;
    }
}
