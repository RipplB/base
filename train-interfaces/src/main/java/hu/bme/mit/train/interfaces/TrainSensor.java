package hu.bme.mit.train.interfaces;

import com.google.guava.Table;

public interface TrainSensor {

	int getSpeedLimit();

	void overrideSpeedLimit(int speedLimit);

	void takeTachografSnapshot();

	Table getTachograftSnapshots();

}
