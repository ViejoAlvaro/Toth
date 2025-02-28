package objects;

import java.util.concurrent.atomic.AtomicLong;

/** 
 * Provides a method to uniquely identify an object inside a system
 */
public class Identity {

	/**
	 * currentId	Current value of Id
	 */
	private static AtomicLong currentId = new AtomicLong(0L);

	/**
	 * Builds a new Identity
	 * @param newId	long New value of current Id
	 */
	public Identity(long newId) {
		if (currentId.get() > newId)
			throw new IllegalArgumentException("New value for current Id is invalid["+ currentId+ "]");

		currentId = new AtomicLong(newId);
	}

	/** 
	 * null constructor disabled
	 */
	@SuppressWarnings("unused")
	private Identity() {}


	/**
	 * Get current Identifier
	 * @return current value of the identifier
	 */
	public static long getId() {
		return currentId.get();
	}

	/**
	 * Resets the current Identifier value
	 * (Use only for test purposes)
	 * @param newId  new value for current identifier
	 */
	public static void setId( Long newId) {
		currentId.set(newId);
	}


	/**
	 * Assigns a unique Identifier
	 * @return new identifier
	 */
	public static long assignId() {
		return currentId.incrementAndGet();
	}

}
