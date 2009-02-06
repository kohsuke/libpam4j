package org.jvnet.libpam;

/**
 * Exception in PAM invoactions.
 * 
 * @author Kohsuke Kawaguchi
 */
public class PAMException extends Exception {
    public PAMException() {
    }

    public PAMException(String message) {
        super(message);
    }

    public PAMException(String message, Throwable cause) {
        super(message, cause);
    }

    public PAMException(Throwable cause) {
        super(cause);
    }

    private static final long serialVersionUID = 1L;
}
