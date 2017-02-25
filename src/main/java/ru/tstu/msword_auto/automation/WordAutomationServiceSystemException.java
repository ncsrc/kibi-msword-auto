package ru.tstu.msword_automation.automation;

/**
 * Unchecked exception that thrown when client code performs some prohibited activity.
 * E.g. tries to get instance of service when it wasn't initialized yet, or
 * tries calls initialization of service more then once.
 */
public class WordAutomationServiceSystemException extends RuntimeException {

    public WordAutomationServiceSystemException(String message) {
        super(message);
    }

}
