package eapli.framework.infrastructure.authz.application.exceptions;

/**
 * Created by nuno on 22/03/16.
 */
public class UserSessionNotInitiatedException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 1740612291648275001L;

    public UserSessionNotInitiatedException() {
        super();
    }

    public UserSessionNotInitiatedException(String message) {
        super(message);
    }
}
