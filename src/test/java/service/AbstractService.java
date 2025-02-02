package service;

import com.example.exseption.ResourceException;
import com.example.util.URLCreator;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static io.restassured.RestAssured.given;

public class AbstractService {

    private final Logger logger = LoggerFactory.getLogger(AbstractService.class);

    private final String postRequest = "POST";
    private final String getRequest = "GET";
    private final String getAllRequest = "GET_ALL";
    private final String putRequest = "PUT";
    private final String deleteRequest = "DELETE";

    public  ValidatableResponse getValidateResponseResourceById(String endpoint, long resourceId){
        checkIsValidEndpoint(endpoint);
        checkIfZeroOrNegative(resourceId);
            try {
                logger.info("Fetching resource with ID: {} from endpoint: {}", resourceId, endpoint);

                ValidatableResponse response = given()
                        .pathParam("id", resourceId)
                        .when()
                        .get(endpoint)
                        .then()
                        .log().body();

                logger.info("Successfully fetched resource with ID: {}", resourceId);

                return response;

            } catch (IllegalArgumentException e ) {
                logger.error("Illegal argument provided", e);
                throw e;

            } catch (Exception e) {
                logger.error("Failed to fetch resource with ID: {} from endpoint: {}", resourceId, endpoint, e);
                throw new RuntimeException("Failed to fetch resource by ID: " + e.getMessage(), e);
            }
    }

    public <T> ResourceResponse<T> makeRequest(Class<T> resourceClass, T resource, String endpoint, long resourceId, String requestType) {

        try {
//            logger.info("Sending " + requestType +" request to endpoint: {} with playlistId: {} and resource: {}", endpoint, resourceId, resource);
            logger.info("Sending {} request to endpoint: {} with PropertyResourceId: {} and AcceptorResource: {}",requestType, endpoint, resource,resourceId);
            T updatedResource;
            Response response;
            switch (requestType) {
                case "POST" -> {
                    response = getPostResponse(endpoint, resource);
                    updatedResource = response.as((Class<T>) resource.getClass());
                }
                case "PUT" -> {
                    response = getPutResponse(endpoint, resource, resourceId);
                    updatedResource = response.as((Class<T>) resource.getClass());
                }

                case "DELETE" -> {
                    response = getDeleteResponse(endpoint, resource, resourceId);
                    updatedResource = response.as(resourceClass);
                }
                case "GET" -> {
                    response = getGetResponse(endpoint, resourceId);
                    updatedResource = response.as(resourceClass);
                }
                case "GET_ALL" -> {
                    response = getGetAllResponses(endpoint);
                    updatedResource = response.as(resourceClass);
                }

                default -> throw new IllegalStateException("Unexpected value: " + requestType);
            }
            logger.info("Response received from " + requestType + " request: {}", response.asString());

            return new ResourceResponse<>(updatedResource, response);
        }
        catch (IllegalArgumentException e){
            logger.error("Illegal argument provided", e);
            throw e;
        }catch (Exception e){
            logger.error("Failed to make request to Endpoint: {} Resource: {}",endpoint, resource, e);
            throw new RuntimeException("Failed to create resource: " + e.getMessage(), e);
        }
    }

    private <T> Response getDeleteResponse(String endpoint, T resource, long resourceId) {
    return given()
                .pathParam("id", resourceId)
                .when()
                .delete(endpoint);
    }

    private <T> Response getPutResponse(String endpoint, T resource, long resourceId) {
        return given()
                .pathParam("id", resourceId)
                .header("Content-Type", "application/json")
                .body(resource)
                .when()
                .put(endpoint);

    }

    private <T> Response getGetResponse(String endpoint, long resourceId) {
        return given()
                .header("Content-Type", "application/json")
                .pathParam("id", resourceId)
                .when()
                .get(endpoint);

    }
    private <T> Response getGetAllResponses(String endpoint) {
       return given()
                .header("Content-Type", "application/json")
                .when()
                .get(endpoint);

    }

    private <T> Response getPostResponse(String endpoint, T resource) {
        return given()
                .header("Content-Type", "application/json")
                .body(resource)
                .when()
                .post(endpoint);
    }


    public <T> ResourceResponse<T> createResource(T resource, String endpoint) {
        checkIfValidEndpointAndResource(endpoint, resource);
        return makeRequest(null, resource, endpoint, -1, postRequest);

    }

    public <T> ResourceResponse <T> updateResource(T resource, String endpoint, long resourceId){
        checkIfValidEndpointAndResource(endpoint, resource);
        checkIfZeroOrNegative(resourceId);
        ResourceResponse<T> response = makeRequest(null, resource, endpoint, resourceId, putRequest);

        return response;
    }


    public <T> T getResourceById(Class<T> resourceClass, String endpoint, long resourceId){
        checkIfValidEndpointAndResource(endpoint, resourceId);
        checkIfZeroOrNegative(resourceId);

        ResourceResponse<T> response = makeRequest(resourceClass ,null, endpoint, resourceId, getRequest);
        Response rawResponse = response.getRawResponse();
        if(isErrorResource(rawResponse, endpoint,resourceId)){
            throw new ResourceException("Resource: "+ resourceClass + " ID: " + resourceId +" is not found");
        }
        return rawResponse.as(resourceClass);

    }


    public <T> T deleteResourceById(Class<T> resourceClass, String endpoint, long resourceId){
        checkIsValidEndpoint(endpoint);
        ResourceResponse<T> response = makeRequest(resourceClass ,null, endpoint, resourceId, deleteRequest);
        Response rawResponse = response.getRawResponse();
        if(isErrorResource(rawResponse, endpoint,resourceId)){
            throw new ResourceException("Resource: "+ resourceClass + " ID: " + resourceId +" is not found");
        }
        return rawResponse.as(resourceClass);
    }

 public boolean isPresentResource(String endpoint, long resourceId) {
        checkIfZeroOrNegative(resourceId);
        String resourceEndpoint = endpoint + "/" + resourceId;

        try {
            logger.info("Checking if resource with ID: {} exists at {}", resourceId, resourceEndpoint);
            Response response = getGetAllResponses(resourceEndpoint);

            if (notSuccessfulResponse(response)) {
                logger.warn("Resource with ID {} not found (HTTP Status: {}).", resourceId, response.statusCode());
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error("An error occurred while checking resource presence: {}", e.getMessage(), e);
            throw new RuntimeException("Error checking resource presence: " + e.getMessage(), e);
        }
    }






    public  long getNumberOfResources(long id, long trackId, String endpoint, String listData) {
        checkIsValidEndpoint(endpoint);
        try {
            logger.info("Sending GET request to endpoint: {}", endpoint);
            Response response = getGetResponse(endpoint, id);

            if (notSuccessfulResponse(response)) {
                logger.error("Failed to retrieve data - GET request. HTTP Status Code: {}", response.statusCode());
                throw new RuntimeException("Failed to retrieve data: " + response.statusCode());
            }

            List<Integer> playlistIds = response.jsonPath().getList(listData);
            logger.info("Playlist IDs retrieved: {}", playlistIds);

            Map<Long, Long> tracksMap = putTracksIdsIntoMap(playlistIds);

            if (tracksMap.containsKey(trackId)) {
                return tracksMap.get(trackId);
            } else {
                logger.error("No resource IDs found in the response.");
                return 0;
            }
        } catch (IllegalArgumentException e) {
            logger.error("Illegal argument provided", e);
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred while retrieving resource number from endpoint: {}", endpoint, e);
            throw new RuntimeException("An error occurred while retrieving resource number: " + e.getMessage(), e);
        }

    }

    private Map<Long, Long> putTracksIdsIntoMap(List<Integer> playlistIds) {
        logger.info("Size of playlist IDs: {}", playlistIds.size());
        Map<Long, Long> tracksMap = new HashMap<>();
        if (!playlistIds.isEmpty()) {
            for (long trId : playlistIds) {
                if (!tracksMap.containsKey(trId)) {
                    tracksMap.put(trId, 1L);
                } else {
                    long numIds = tracksMap.get(trId) + 1;
                    tracksMap.put(trId, numIds);
                }
            }
        }

        return tracksMap;
    }

    private boolean isErrorResource(Response response, String endpoint, long resourceId) {
            int statusCode = response.getStatusCode();

            switch (statusCode) {
                case 400:
                    logger.error("Bad request at endpoint: {} with resourceId: {}", endpoint, resourceId);
                    return true;
                case 401:
                    logger.error("Unauthorized access to endpoint: {} with resourceId: {}", endpoint, resourceId);
                    return true;
                case 403:
                    logger.error("Forbidden access to endpoint: {} with resourceId: {}", endpoint, resourceId);
                    return true;
                case 404:
                    logger.error("Resource not found at endpoint: {} with resourceId: {}", endpoint, resourceId);
                    return true;
                case 500:
                    logger.error("Internal server error at endpoint: {} with resourceId: {}", endpoint, resourceId);
                    return true;
                default:
                    return false;
        }

    }

    private boolean notSuccessfulResponse(Response response){
        return response.statusCode() < 200 || response.statusCode() > 299;
    }

    protected void checkIfZeroOrNegative(long resourceId) {
        if (resourceId <= 0) {
            logger.error("Invalid playlistId: {}", resourceId);
            throw new IllegalArgumentException("Resource ID must be greater than 0");
        }
    }

    private <T> void checkIfValidEndpointAndResource(String endpoint, T resource) {
        if(resource == null || endpoint == null || endpoint.isBlank()){
            logger.error("Illegal value for resource or endpoint. Resource: {}, Endpoint: {}", resource, endpoint);
            throw new IllegalArgumentException("Illegal value for resource or endpoint");
        }
    }

    private void checkIsValidEndpoint(String endpoint) {
        if (endpoint == null || endpoint.isBlank() ) {
            logger.error("Invalid endpoint: {}", endpoint);
            throw new IllegalArgumentException("Endpoint cannot be null or blank");
        }
    }

    public  <T> List<Integer> getAllResourcesIds(Class<T> resourseClass, String endpoint) {
        checkIsValidEndpoint(endpoint);
        ResourceResponse<T> response = makeRequest(resourseClass, null, endpoint, 0,getAllRequest );

        List<Integer> allResources = response.getAllResoursesId("tracks.id");
        logger.info("All Resources List: {} ",allResources);
        return allResources;
    }

    public <T> Integer getRandomResourceId(Class<T> resourseClass, String endpoint){
        List<Integer> resourceIds = getAllResourcesIds(resourseClass, endpoint);

        Random random = new Random();
        int randomPosition =random.nextInt(resourceIds.size());

        return resourceIds.get(randomPosition);
    }
}
