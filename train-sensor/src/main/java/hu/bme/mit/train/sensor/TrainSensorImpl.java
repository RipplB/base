package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;

import com.google.common.collect.Table;
import com.google.common.collect.HashBasedTable;

public class TrainSensorImpl implements TrainSensor {

	private TrainController controller;
	private TrainUser user;
	private int speedLimit = 5;
	private Table<Long, Integer, Integer> tachograf;

	public TrainSensorImpl(TrainController controller, TrainUser user) {
		this.controller = controller;
		this.user = user;
		tachograf = HashBasedTable.create();
	}

	@Override
	public int getSpeedLimit() {
		return speedLimit;
	}

	@Override
	public void overrideSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
		controller.setSpeedLimit(speedLimit);
	}

	@Override
	public void takeTachografSnapshot() {
		tachograf.put(System.currentTimeMillis(), user.getJoystickPosition(), controller.getReferenceSpeed());
	}

	@Override
	public Table getTachograftSnapshots() {
		return tachograf;
	}

}
