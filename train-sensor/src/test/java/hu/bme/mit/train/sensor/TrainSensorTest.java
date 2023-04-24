package hu.bme.mit.train.sensor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;

import static org.mockito.Mockito.*;

public class TrainSensorTest {

    TrainController controller;
    TrainUser user;
    TrainSensorImpl sensor;

    @Before
    public void before() {
        controller = mock(TrainController.class);
        user = mock(TrainUser.class);
        sensor = new TrainSensorImpl(controller, user);
    }

    @Test
    public void overrideSpeedLimit_setsUserAlarmTrue_ifNewSpeedLimitBelow0() {
        sensor.overrideSpeedLimit(-1);
        verify(user).setAlarmState(true);
    }

    @Test
    public void overrideSpeedLimit_setsUserAlarmFalse_ifNewSpeedLimitIs0() {
        sensor.overrideSpeedLimit(0);
        verify(user).setAlarmState(false);
    }

    @Test
    public void overrideSpeedLimit_setsUserAlarmTrue_ifNewSpeedLimitAbove500() {
        sensor.overrideSpeedLimit(501);
        verify(user).setAlarmState(true);
    }

    @Test
    public void overrideSpeedLimit_setsUserAlarmFalse_ifNewSpeedLimitIs500() {
        sensor.overrideSpeedLimit(500);
        verify(user).setAlarmState(false);
    }

    @Test
    public void overrideSpeedLimit_setsUserAlarmTrue_ifNewSpeedLimitLessThanHalfOfRefSpeed() {
        when(controller.getReferenceSpeed()).thenReturn(40);

        sensor.overrideSpeedLimit(19);
        verify(user).setAlarmState(true);;
    }

}
