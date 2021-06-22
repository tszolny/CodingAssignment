package local.tszolny.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FileProcessorTest {
    private FileProcessor fileProcessor;

    @BeforeEach
    private void setup(){
        EventProcessor eventProcessor;
        eventProcessor = Mockito.mock(EventProcessor.class);
        fileProcessor = new FileProcessor(eventProcessor);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void processFile_ShouldThrowExceptionWhenInputFilePathIsNullOrEmpty(String input){
        assertThrows(IllegalArgumentException.class, () -> fileProcessor.processFile(input));
    }
}
