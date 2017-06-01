package dao;

import controller.Controller;
import model.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.sql.SQLException;
import java.util.logging.Logger;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class PointDaoImplTest {

    private static final Logger logger = Logger.getLogger(Controller.class.getName());

    @BeforeEach
    public void getLogInfo(TestInfo info){
        logger.info(String.format("Executed --> %s", info.getDisplayName()));
    }

    @Test
    public void testAddPointMethodNotThrowException() throws SQLException {
        PointDao pointDaoMock = mock(PointDaoImpl.class);
        doNothing().when(pointDaoMock).addPoint(new Point(55, 55));
    }
}