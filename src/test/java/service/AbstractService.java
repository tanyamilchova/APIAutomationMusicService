package service;

import com.example.exseption.NotFoundException;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AbstractService {

    private final Logger logger = LoggerFactory.getLogger(AbstractService.class);

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

    public <T> ResourceResponse<T> createResource(T resource, String endpoint) {
        checkIfValidEndpointAndResource(endpoint, resource);
        try {
            Response response = given()
                    .header("Content-Type", "application/json")
                    .body(resource)
                    .when()
                    .post(endpoint);

            T updatedResource = response.as((Class<T>) resource.getClass());
            return new ResourceResponse<>(updatedResource, response);
        }
        catch (IllegalArgumentException e){
            logger.error("Illegal argument provided", e);
            throw e;
        }catch (Exception e){
            logger.error("Failed to create resource. Resource: {}, Endpoint: {}", resource,endpoint, e);
            throw new RuntimeException("Failed to create resource: " + e.getMessage(), e);
        }
    }



    public <T> ResourceResponse <T> updateResource(T resource, String endpoint, long resourceId){
        checkIfValidEndpointAndResource(endpoint, resource);
        checkIfZeroOrNegative(resourceId);
        try {
            logger.info("Sending PUT request to endpoint: {} with playlistId: {} and resource: {}", endpoint, resourceId, resource);
            Response response = given()
                    .pathParam("id", resourceId)
                    .header("Content-Type", "application/json")
                    .body(resource)
                    .when()
                    .put(endpoint);

            logger.info("Response received from PUT request: {}", response.asString());

            if(isNotFoundResource(response, endpoint,resourceId)){
                throw new NotFoundException("Resource: "+ resource + " ID: " + resourceId +" is not found");
            }

            T updatedResource = response.as((Class<T>) resource.getClass());
            return new ResourceResponse<>(updatedResource, response);

        } catch (IllegalArgumentException e) {
            logger.error("Illegal argument provided", e);
            throw e;
        } catch (Exception e) {
            logger.error("Failed to update resource at endpoint: {} with playlistId: {}. Resource: {}", endpoint, resourceId, resource, e);
            throw new RuntimeException("Failed to update resource: " + e.getMessage(), e);
        }
    }



    public <T> T getResourceById(Class<T> resourceClass, String endpoint, long resourceId){
        checkIfValidEndpointAndResource(endpoint, resourceId);
        checkIfZeroOrNegative(resourceId);

        try {
            logger.info("Sending GET request to endpoint: {} with resourceId: {}", endpoint, resourceId);
            Response response = given()
                    .pathParam("id", resourceId)
                    .when()
                    .get(endpoint);

            logger.info("Response received from GET request: {}", response.asString());

            if(isNotFoundResource(response, endpoint,resourceId)){
                throw new NotFoundException("Resource: "+ resourceClass + " ID: " + resourceId +" is not found");
            }
            return response.as(resourceClass);
        } catch (IllegalArgumentException e) {
            logger.error("Illegal argument provided", e);
            throw e;
        } catch (Exception e) {
            logger.error("Failed to get resource at endpoint: {} with resourceId: {}", endpoint, resourceId, e);
            throw new RuntimeException("Failed to get resource: " + e.getMessage(), e);
        }
    }


    public  boolean isPresentResource(String endpoint, long resourceId, String listPath) {
        checkIsValidEndpoint(endpoint);
        checkIfZeroOrNegative(resourceId);
        try {
            logger.info("Sending GET request to endpoint: {} to check if resource with ID: {} exists", endpoint, resourceId);
            Response response = given()
                    .header("Content-Type", "application/json")
                    .when()
                    .get(endpoint);
            if (errorResponse(response)) {
                logger.error("Failed to retrieve data. HTTP Status Code: {}", response.statusCode());
                throw new RuntimeException("Failed to retrieve data: " + response.statusCode());
            }

            List<Object> playlistIds = response.jsonPath().getList(listPath);
            if (playlistIds != null) {
                logger.info("Playlist IDs count: {}", playlistIds.size());
            } else {
                logger.warn("No playlist IDs found in the response.");
            }
            if (playlistIds != null && !playlistIds.isEmpty()) {
                return playlistIds.contains(resourceId);
            } else {
                logger.error("Failed to retrieve 'id' list from the response.");
                throw new RuntimeException("Failed to retrieve 'id' list from the response.");
            }

        } catch (IllegalArgumentException e) {
            logger.error("Illegal argument provided", e);
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred while checking if resource is present at endpoint: {} with resourceId: {}", endpoint, resourceId, e);
            throw new RuntimeException("An error occurred while checking if resource is present: " + e.getMessage(), e);
        }
    }


    public  long getNumberOfResources(long id, long trackId, String endpoint, String listData) {
        checkIsValidEndpoint(endpoint);
        try {
            logger.info("Sending GET request to endpoint: {}", endpoint);
            Response response = given()
                    .pathParams("id", id)
                    .header("Content-Type", "application/json")
                    .when()
                    .get(endpoint);
            if (errorResponse(response)) {
                logger.error("Failed to retrieve data - GET request. HTTP Status Code: {}", response.statusCode());
                throw new RuntimeException("Failed to retrieve data: " + response.statusCode());
            }
            logger.info("Response body: {}", response.getBody().asString());
            List<Integer> playlistIds = response.jsonPath().getList(listData);
            logger.info("Playlist IDs retrieved: {}", playlistIds);

            Map<Long, Long> tracksMap = putTracksIdsIntoMap(playlistIds);

            logger.info("Size of playlist IDs: {}", playlistIds.size());
            if (tracksMap.containsKey(trackId)) {
                return tracksMap.get(trackId);
            } else {
                logger.warn("No resource IDs found in the response.");
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

    public <T> T deleteResourceById(Class<T> resourceClass, String endpoint, long resourceId){
        checkIsValidEndpoint(endpoint);
        try {
            logger.info("Sending DELETE request to endpoint: {} for resourceId: {}", endpoint, resourceId);
            Response response = given()
                    .pathParam("id", resourceId)
                    .when()
                    .delete(endpoint);
            logger.info("Received response with status code: {}", response.statusCode());

            if (errorResponse(response)) {
                logger.error("Failed to delete resource. HTTP Status Code: {}", response.statusCode());
                throw new RuntimeException("Failed to delete resource: " + resourceId + " ,statusCode: " + response.statusCode());
            }
            logger.info("Response body: {}", response.asString());

            if(isNotFoundResource(response, endpoint, resourceId)) {
                return response.as(resourceClass);
            }
        } catch (IllegalArgumentException e) {
            logger.error("Illegal argument provided", e);
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred while deleting resource from endpoint: {} with resourceId: {}", endpoint, resourceId, e);
            throw new RuntimeException("An error occurred while deleting resource: " + e.getMessage(), e);
        }
        return null;
    }


    private boolean isNotFoundResource(Response response, String endpoint, long resourceId) {
        if (response.getStatusCode() == 404) {
            logger.error("Resource not found at endpoint: {} with resourceId: {}", endpoint, resourceId);
            return true;
        }
        return false;
    }

    private boolean errorResponse(Response response){
        return response.statusCode() < 200 || response.statusCode() > 299;
    }

    private void checkIfZeroOrNegative(long resourceId) {
        if (resourceId <= 0) {
            logger.error("Invalid playlistId: {}", resourceId);
            throw new IllegalArgumentException("Playlist ID must be greater than 0");
        }
    }

    private <T> void checkIfValidEndpointAndResource(String endpoint, T resource) {
        if(resource == null || endpoint == null || endpoint.isBlank()){
            logger.error("Illegal value for resource or endpoint. Resource: {}, Endpoint: {}", resource, endpoint);
            throw new IllegalArgumentException("Illegal value for resource or endpoint");
        }
    }

    private boolean checkIsValidEndpoint(String endpoint) {
        if (endpoint == null || endpoint.isBlank() ) {
            logger.error("Invalid endpoint: {}", endpoint);
            throw new IllegalArgumentException("Endpoint cannot be null or blank");
        }
        return true;
    }
}
