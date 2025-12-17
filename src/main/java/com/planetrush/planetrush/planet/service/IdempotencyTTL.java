package com.planetrush.planetrush.planet.service;

import java.util.concurrent.TimeUnit;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IdempotencyTTL {

	REGISTER_RESIDENT("idempotent:register-resident", 10, TimeUnit.SECONDS),
	DELETE_RESIDENT("idempotent:delete-resident", 10, TimeUnit.SECONDS),
	;

	final String prefix;
	final int amount;
	final TimeUnit unit;
}
