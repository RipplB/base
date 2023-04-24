package hu.bme.mit.train.user;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;

public class TrainUserImpl implements TrainUser {

	private static final long POLLING_INTERVAL = 200;

	private TrainController controller;
	private int joystickPosition;

	public TrainUserImpl(TrainController controller) {
		this.controller = controller;
		new Thread() {
			public void run() {
				while (true) {
					controller.setJoystickPosition(joystickPosition);
					try {
						Thread.sleep(POLLING_INTERVAL);
					} catch (Exception e) {
						System.out.println("Error in TrainUser joystick polling");
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	@Override
	public boolean getAlarmFlag() {
		return false;
	}

	@Override
	public int getJoystickPosition() {
		return joystickPosition;
	}

	@Override
	public void overrideJoystickPosition(int joystickPosition) {
		this.joystickPosition = joystickPosition;
		controller.setJoystickPosition(joystickPosition);
	}

}
