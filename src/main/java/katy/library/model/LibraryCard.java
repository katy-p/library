package katy.library.model;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Wither
@Builder(toBuilder = true)
public class LibraryCard {

    long id;
    Book book;
    Person person;

}
