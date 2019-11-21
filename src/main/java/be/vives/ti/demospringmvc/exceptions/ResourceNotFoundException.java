package be.vives.ti.demospringmvc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private String resource;
    private String id;

    public ResourceNotFoundException(String id) {
        super(String.format("Resource not found : %s",id));
    }

    public ResourceNotFoundException(String id, String resource) {
        super(String.format("Resource %s not found : %s", resource, id));
    }

    public String getId() {
        return this.id;
    }
}
