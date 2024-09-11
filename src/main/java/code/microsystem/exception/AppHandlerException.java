package code.microsystem.exception;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class AppHandlerException {

    private Logger log= LoggerFactory.getLogger(AppHandlerException.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String>  handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        log.info("AppHandlerException: handlerMethodArgumentNotValidException is strated");
      //  System.out.println(ex);
        Map<String,String> map=new HashMap<String,String>();
        ex.getBindingResult().getFieldErrors().forEach(e->{
            map.put(e.getField(),e.getDefaultMessage());
        });
        log.info("AppHandlerException: handlerMethodArgumentNotValidException is end");
        return map;

    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PatientNotFoundException.class)
    public Set<String> handlePatientNotFoundException(PatientNotFoundException ex){

       // return  new ResponseEntity<String>(ex.getMessage(),HttpStatus.NOT_FOUND);
        return  Collections.singleton(ex.getMessage());

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Set<String> handleIllegalArgumentException(IllegalArgumentException ex) {

        // Creating a Set containing the exception message
    	return  Collections.singleton(ex.getMessage());
		

       
    }

}
