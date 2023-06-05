package com.sky.exception;

/**
 * @author NXRUI
 * @version 1.0
 * @date 2023/6/5 17:20
 * @description:
 */
public class IdNumberFormatException extends RuntimeException {
    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public IdNumberFormatException() {
        super();
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public IdNumberFormatException(String message) {
        super(message);
    }
}
