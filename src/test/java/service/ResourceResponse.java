package service;

import io.restassured.response.Response;
import lombok.Getter;

import java.util.List;

@Getter
public class ResourceResponse<T> {
    private final T resource;
    private final Response rawResponse;

    public ResourceResponse(T resource, Response rawResponse) {
        this.resource = resource;
        this.rawResponse = rawResponse;
    }

    public List<Integer> getAllResoursesId(String path){
        System.out.println(rawResponse.asString());
        return rawResponse.jsonPath().getList(path);
    }

}
