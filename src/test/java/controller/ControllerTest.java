package controller;

import model.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import java.util.logging.Logger;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private static final Logger logger = Logger.getLogger(Controller.class.getName());

    @BeforeEach
    public void getLogInfo(TestInfo info){
        logger.info(String.format("Executed --> %s", info.getDisplayName()));
    }

    @Test
    public void testCalculateNearestReturnWithExpectedCoord(){
        Controller controllerMock = mock(Controller.class);
        when(controllerMock.calculateNearestPoint(2135908.5646355287, 6008305.212678739)).
                thenReturn(new Point(2135859, 6008350));
        assertEquals(2135859,
                controllerMock.
                        calculateNearestPoint(2135908.5646355287, 6008305.212678739).getX());
        assertEquals(6008350,
                controllerMock.
                        calculateNearestPoint(2135908.5646355287, 6008305.212678739).getY());
    }

    @Test
    public void testCalculateNearestReturnWithExpectedName() {
        Controller controllerMock = mock(Controller.class);
        when(controllerMock.calculateNearestPoint(2135908.5646355287, 6008305.212678739)).
                thenReturn(new Point("Propi", "restaurant", "2330611592"));

        assertEquals("Propi",
                controllerMock.
                        calculateNearestPoint(2135908.5646355287, 6008305.212678739).getName());
    }
}