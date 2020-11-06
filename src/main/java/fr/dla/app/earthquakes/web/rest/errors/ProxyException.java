package fr.dla.app.earthquakes.web.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.zalando.problem.Status;

@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
public class ProxyException extends DlappException {

    public ProxyException(String defaultMessage, String entityName, String errorKey) {
        super(Status.PRECONDITION_FAILED, ErrorConstants.DEFAULT_TYPE, defaultMessage, entityName, errorKey);
    }
}
