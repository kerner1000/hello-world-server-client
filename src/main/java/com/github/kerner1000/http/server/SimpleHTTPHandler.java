package com.github.kerner1000.http.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A simple HTTP request handler.
 *
 * Every request will be answered by a greeting and a copy of any query parameter.
 * The answer will be a simple, not formatted string.
 * For example, {@code http://localhost:8080/test?q=4}
 * will return {@code Hi there! Your request param was: 4}
 */
public class SimpleHTTPHandler implements HttpHandler {

    private static final Logger logger = LoggerFactory.getLogger(SimpleHTTPHandler.class);

    public static final String DEFAULT_NO_PARAMS_FOUND_MSG = "<no params found>";

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        printRequestInfo(httpExchange);
        String queryParam = getQueryParam(httpExchange);
        String response = "Hi there! Your request param was: " + queryParam;
        httpExchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String getQueryParam(HttpExchange httpExchange) {
        String query = httpExchange.getRequestURI().getQuery();
        if (query != null) {
            // keys and values are both contained in the array.
            // The first value is therefore at index 1.
            String[] params = query.split("=");
            if (params.length > 1) {
                return params[1];
            }
        }
        return DEFAULT_NO_PARAMS_FOUND_MSG;
    }

    private static void printRequestInfo(HttpExchange exchange) {
        logger.info("Headers: {}", exchange.getRequestHeaders().entrySet());
        logger.info("Principle: {}", exchange.getPrincipal());
        logger.info("HTTP method: {}", exchange.getRequestMethod());
        logger.info("Query: {}", exchange.getRequestURI().getQuery());
    }
}
