package com.libedi.demo.framework.model;

/**
 * Validation Marker for using @Validated annotation
 * @author Sangjun, Park
 *
 */
public interface ValidationMarker {
	interface Create{};
	interface Retrieve{};
	interface Update{};
	interface Delete{};
}
