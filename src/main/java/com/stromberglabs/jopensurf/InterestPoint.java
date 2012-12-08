package com.stromberglabs.jopensurf;

import com.stromberglabs.cluster.Clusterable;

public interface InterestPoint extends Clusterable {
	public double getDistance(InterestPoint point);
}
