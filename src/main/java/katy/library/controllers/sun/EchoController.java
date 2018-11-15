package katy.library.controllers.sun;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class EchoController implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        final StringJoiner responseBuilder = new StringJoiner("\n");

        final String head = exchange.getRequestMethod() + " " + exchange.getRequestURI() + " " + exchange.getProtocol();

        responseBuilder.add(head);
        responseBuilder.add("");

        responseBuilder.add(exchange.getLocalAddress().toString());
        responseBuilder.add("");

        for (Map.Entry<String, List<String>> entry : exchange.getRequestHeaders().entrySet()) {

            for (String str : entry.getValue()) {

                responseBuilder.add(entry.getKey() + ": " + str);
            }
        }

        byte[] bytes = responseBuilder.toString().getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, bytes.length);
        exchange.getResponseBody().write(bytes);

    }
}
