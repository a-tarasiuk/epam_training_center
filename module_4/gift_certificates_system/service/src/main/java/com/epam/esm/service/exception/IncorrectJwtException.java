package com.epam.esm.service.exception;

import lombok.Getter;

/**
 * Thrown to indicate that a method has been passed an illegal or inappropriate argument.
 */

@Getter
public class IncorrectJwtException extends RuntimeException {
    /**
     * Constructs an <code>IncorrectArgumentException</code> with no detail message.
     */
    public IncorrectJwtException() {
    }

    /**
     * Constructs an <code>IncorrectArgumentException</code> with the
     * specified detail message.
     *
     * @param message the detail message.
     */
    public IncorrectJwtException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * <p>Note that the detail message associated with <code>cause</code> is
     * <i>not</i> automatically incorporated in this exception's detail
     * message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link Throwable#getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link Throwable#getCause()} method).  (A {@code null} value
     *                is permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     */
    public IncorrectJwtException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail
     * message of {@code (cause==null ? null : cause.toString())} (which
     * typically contains the class and detail message of {@code cause}).
     * This constructor is useful for exceptions that are little more than
     * wrappers for other throwables (for example, {@link
     * java.security.PrivilegedActionException}).
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link Throwable#getCause()} method).  (A {@code null} value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     * @since 1.5
     */
    public IncorrectJwtException(Throwable cause) {
        super(cause);
    }
}
