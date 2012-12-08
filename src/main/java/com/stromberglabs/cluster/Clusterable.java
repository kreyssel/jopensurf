package com.stromberglabs.cluster;

/**
 * A simple interface for anything that can be clustered together, all it has to
 * have is a location.
 * 
 * @author Andrew
 * 
 */
public interface Clusterable {
	public float[] getLocation();
}