package katy.library.controllers.sun;

import katy.library.model.Person;
import katy.library.service.PersonServise;

import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.StringJoiner;

public class PersonHttpHandler extends AbstractHttpHandler{

    private  final PersonServise personService;

    public PersonHttpHandler(PersonServise personService) {
        this.personService = personService;
    }

    @Override
    public String path() {
        return "/persons/";
    }

    @Override
    protected String onPost(URI requestURI, InputStream requestBody) {
        throw new RuntimeException("Unsupported method: POST");
    }

    @Override
    protected String onPut(URI requestURI, InputStream requestBody) {
        throw new RuntimeException("Unsupported method: PUT");
    }

    @Override
    protected String onDelete(URI requestURI) {

        throw new RuntimeException("Unsupported method: DELET");
    }

    @Override
    protected String onGet(URI requestURI) {

        List<Person> personList = personService.findByNamePerson("Smith");

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("person list:");
        for (Person person : personList) {
            returnString.add(person.toString());
        }

        return returnString.toString();
    }
}
