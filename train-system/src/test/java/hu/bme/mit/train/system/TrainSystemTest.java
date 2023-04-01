package hu.bme.mit.train.system;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.system.TrainSystem;

import java.util.Map;

public class TrainSystemTest {

	TrainController controller;
	TrainSensor sensor;
	TrainUser user;

	static final int SPEED_LIMIT = 50;
	
	@Before
	public void before() {
		TrainSystem system = new TrainSystem();
		controller = system.getController();
		sensor = system.getSensor();
		user = system.getUser();

		sensor.overrideSpeedLimit(SPEED_LIMIT);
	}
	
	@Test
	public void OverridingJoystickPosition_IncreasesReferenceSpeed() {
		Assert.assertEquals(0, controller.getReferenceSpeed());
		
		user.overrideJoystickPosition(5);

		controller.followSpeed();
		Assert.assertEquals(5, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(15, controller.getReferenceSpeed());
	}

	@Test
	public void OverridingJoystickPositionToNegative_SetsReferenceSpeedToZero() {
		user.overrideJoystickPosition(4);
		controller.followSpeed();
		user.overrideJoystickPosition(-5);
		controller.followSpeed();
		Assert.assertEquals(0, controller.getReferenceSpeed());
	}

	@Test
	public void OverridingJoystickPositionToPositiveAtSpeedLimit_ReferenceSpeedStaysSpeedLimit() {
		user.overrideJoystickPosition(10);
		for (int i = 0; i < 6; i++) {
			controller.followSpeed();
		}
		Assert.assertEquals(50, controller.getReferenceSpeed());
	}

	@Test
	public void OverridingSpeedLimit_SetsReferenceSpeedToNewSpeedLimit() {
		user.overrideJoystickPosition(100);
		sensor.overrideSpeedLimit(100);
		controller.followSpeed();
		sensor.overrideSpeedLimit(50);
		controller.followSpeed();
		Assert.assertEquals(50, controller.getReferenceSpeed());
	}

	@Test
	public void Tachograf_TakesSnapshots() {
		user.overrideJoystickPosition(10);
		controller.followSpeed();
		sensor.takeTachografSnapshot();
		controller.followSpeed();
		user.overrideJoystickPosition(5);
		sensor.takeTachografSnapshot();
		Assert.assertEquals(20, controller.getReferenceSpeed());

		Assert.assertEquals(sensor.getTachograftSnapshots().size(), 2);
		Map<Integer,Map<Long,Integer>> columnMap = sensor.getTachograftSnapshots().columnMap();
		Assert.assertTrue(columnMap.containsKey(10));
		Assert.assertTrue(columnMap.containsKey(5));
		Assert.assertTrue(columnMap.get(10).containsValue(10));
		Assert.assertTrue(columnMap.get(5).containsValue(20));
	}
	
}
