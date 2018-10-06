package katy.library.model;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Wither
@Builder(toBuilder = true)
public class Book {
    long id;
    String title;
    Author author;
}
