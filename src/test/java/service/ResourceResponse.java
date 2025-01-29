package service;

import io.restassured.response.Response;
import lombok.Getter;

@Getter
public class ResourceResponse<T> {
    private final T resource;
    private final Response rawResponse;

    public ResourceResponse(T resource, Response rawResponse) {
        this.resource = resource;
        this.rawResponse = rawResponse;
    }

    public T getResource() {
        return resource;
    }

    public Response getRawResponse() {
        return rawResponse;
    }
}
