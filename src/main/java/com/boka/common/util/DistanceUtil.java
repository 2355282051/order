package com.boka.common.util;

import ch.hsr.geohash.WGS84Point;
import ch.hsr.geohash.util.VincentyGeodesy;

public class DistanceUtil {

	public static double distance (double fooLat, double fooLng, double barLat, double barLng) {
		WGS84Point foo = new WGS84Point(fooLat, fooLng);
		WGS84Point bar = new WGS84Point(barLat, barLng);
		return VincentyGeodesy.distanceInMeters(foo, bar)/1000;
	}
	
}
