package katy.library.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ValidationException extends ResourceNotFoundException {
    String fieldName;

    public ValidationException(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }
}
