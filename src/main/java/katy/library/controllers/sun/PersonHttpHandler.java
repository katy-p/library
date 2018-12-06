package katy.library.controllers.sun;

import katy.library.exception.ResourceNotFoundException;
import katy.library.model.Person;
import katy.library.service.PersonServise;

import java.io.InputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PersonHttpHandler extends AbstractHttpHandler{

    private  final PersonServise personService;

    public PersonHttpHandler(PersonServise personService) {
        this.personService = personService;
    }

    @Override
    public String path() {

        return "/persons/";
    }

    private Person getPerson(URI requestURI){

        Map<String, String> queryParams = getParameters(requestURI);

        final LocalDate dateOfBirth = LocalDate.parse(queryParams.get("dateOfBirth"), DateTimeFormatter.ISO_LOCAL_DATE);

        return Person.builder()
                .id(Long.parseLong(queryParams.get("id")))
                .firstName(queryParams.get("firstName"))
                .lastName(queryParams.get("lastName"))
                .dateOfBirth(dateOfBirth)
                .build();
    }

    @Override
    protected String onPost(URI requestURI, InputStream requestBody) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("created person:");

        returnString.add(personService.createPerson(getPerson(requestURI)).toString());

        return returnString.toString();
    }

    @Override
    protected String onPut(URI requestURI, InputStream requestBody) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("updated person:");

        returnString.add(personService.updatePerson(getPerson(requestURI)).toString());

        return returnString.toString();
    }

    @Override
    protected String onDelete(URI requestURI) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("deleted person:");

        final long id = getId(requestURI).orElseThrow(() -> new ResourceNotFoundException("Can't delete author without id."));
        returnString.add(personService.deletePerson(id).toString());

        return returnString.toString();
    }

    @Override
    protected String onGet(URI requestURI) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("person list:");

        final OptionalLong id = getId(requestURI);

        if (id.isPresent()) {
            returnString.add(personService.getByIdPerson(id.getAsLong()).toString());

        } else {
            final List<Person> personList = personService.fullListOfPersons();
            personList.forEach(person -> returnString.add(person.toString()));
        }

        return returnString.toString();
    }
}
