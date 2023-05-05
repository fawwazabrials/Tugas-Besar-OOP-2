package exception;

public class SimIsDeadException extends Exception {
    public SimIsDeadException() {
        super();
    }

    public SimIsDeadException(String message) {
        super(message);
    }
}
