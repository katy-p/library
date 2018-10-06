package katy.library.model;


import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

import java.time.LocalDate;

@Value
@Builder
public class Person {

    @Wither
    long id;
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
}
