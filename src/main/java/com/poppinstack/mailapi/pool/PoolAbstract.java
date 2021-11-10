package com.poppinstack.mailapi.pool;

import java.util.concurrent.TimeUnit;

import stormpot.BlazePool;
import stormpot.Config;
import stormpot.PoolException;
import stormpot.Poolable;
import stormpot.Timeout;

public class PoolAbstract<P extends Poolable> extends BlazePool<P> {

	private Timeout timeout;
	
	public PoolAbstract(Config<P> config) {
		super(config);
		timeout = new Timeout(4, TimeUnit.MINUTES);
	}

	public P claim() throws PoolException, InterruptedException {
		return super.claim(timeout);
	}
}