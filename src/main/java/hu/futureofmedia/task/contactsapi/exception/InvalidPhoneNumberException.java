package hu.futureofmedia.task.contactsapi.exception;

import com.google.i18n.phonenumbers.NumberParseException;

public class InvalidPhoneNumberException extends RuntimeException {
    public InvalidPhoneNumberException(String phoneNumber, NumberParseException e) {
        super("Phonenumber is invalid :" + phoneNumber, e);
    }

    public InvalidPhoneNumberException(String phoneNumber) {
        super("Phonenumber is invalid :" + phoneNumber);
    }
}
