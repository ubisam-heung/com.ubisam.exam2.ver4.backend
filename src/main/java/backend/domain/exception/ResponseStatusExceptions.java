package backend.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ResponseStatusExceptions {

    public static Exception BAD_REQUEST = new BAD_REQUEST(); //400
    public static Exception UNAUTHORIZED = new UNAUTHORIZED(); //401
    public static Exception FORBIDDEN = new FORBIDDEN(); //403
    public static Exception NOT_FOUND = new NOT_FOUND(); //404
    public static Exception CONFLICT = new CONFLICT(); //409
    
    public static Exception INTERNAL_SERVER_ERROR = new INTERNAL_SERVER_ERROR();

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public static class BAD_REQUEST extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    public static class CONFLICT extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public static class NOT_FOUND extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public static class FORBIDDEN extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public static class UNAUTHORIZED extends RuntimeException {
    }


    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public static class INTERNAL_SERVER_ERROR extends RuntimeException {
    }

}
