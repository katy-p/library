package katy.library.controllers.sun;

import katy.library.service.PersonServise;

import java.io.InputStream;
import java.net.URI;
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

        String[] params = requestURI.getPath().split("/");

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("deleted person:");

        if (params.length == 3) {
            long id = Long.parseLong(params[params.length - 1]);
            returnString.add(personService.getByIdPerson(id).toString());
        }

        return returnString.toString();
    }

    @Override
    protected String onGet(URI requestURI) {

        String[] params = requestURI.getPath().split("/");

        //List<Person> personList = personService.findByNamePerson("Smith");

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("person list:");
        //for (Person person : personList) {
        //   returnString.add(person.toString());
        //}

        if (params.length == 3) {
            long id = Long.parseLong(params[params.length - 1]);
            returnString.add(personService.getByIdPerson(id).toString());
        }

        return returnString.toString();
    }
}
