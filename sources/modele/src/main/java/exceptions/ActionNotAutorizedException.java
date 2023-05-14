package exceptions;

public class ActionNotAutorizedException extends Throwable {
    public ActionNotAutorizedException(String errorMessage) {
        super(errorMessage);
    }

    public ActionNotAutorizedException() {

    }
}

