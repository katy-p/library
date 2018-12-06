package katy.library.controllers.sun;

import katy.library.model.Person;
import katy.library.service.PersonServise;

import java.io.InputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalLong;
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

    private Person getPerson(String query){

        Map<String, String> queryParams = new HashMap<>();

        for (String q : query.split("&")) {
            String[] qa = q.split("=");

            queryParams.put(URLDecoder.decode(qa[0]), (qa.length == 2) ? URLDecoder.decode(qa[1]) : "");
        }

        final LocalDate dateOfBirth = LocalDate.parse(queryParams.get("dateOfBirth"), DateTimeFormatter.ISO_LOCAL_DATE);

        return Person.builder()
                .id(Long.parseLong(queryParams.get("id")))
                .firstName(queryParams.get("firstName"))
                .lastName(queryParams.get("lastName"))
                .dateOfBirth(dateOfBirth)
                .build();
    }

    private OptionalLong getPersonId(String path){

        String[] params = path.split("/");

        return (params.length == 3) ? OptionalLong.of(Long.parseLong(params[params.length - 1])) : OptionalLong.empty();
    }

    @Override
    protected String onPost(URI requestURI, InputStream requestBody) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("created person:");

        returnString.add(personService.createPerson(getPerson(requestURI.getQuery())).toString());

        return returnString.toString();
    }

    @Override
    protected String onPut(URI requestURI, InputStream requestBody) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("updated person:");

        returnString.add(personService.updatePerson(getPerson(requestURI.getQuery())).toString());

        return returnString.toString();
    }

    @Override
    protected String onDelete(URI requestURI) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("deleted person:");

        final long id = getPersonId(requestURI.getPath()).orElseThrow(() -> new RuntimeException("Invalid path"));
        returnString.add(personService.deletePerson(id).toString());

        return returnString.toString();
    }

    @Override
    protected String onGet(URI requestURI) {

        final StringJoiner returnString = new StringJoiner("\n");
        returnString.add("person list:");

        final long id = getPersonId(requestURI.getPath()).orElseThrow(() -> new RuntimeException("Invalid path"));
        returnString.add(personService.getByIdPerson(id).toString());

        return returnString.toString();
    }
}
