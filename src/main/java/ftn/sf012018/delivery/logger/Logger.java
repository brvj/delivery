package ftn.sf012018.delivery.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logger {

    org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);

    @AfterReturning(value = "execution(* ftn..ActionController.index(..))", returning = "returnValue")
    public void logAfterIndexingAction(JoinPoint joinPoint, ResponseEntity<Void> returnValue){
        if(returnValue.getStatusCode() == HttpStatus.OK) logger.info("Successfully indexed Action");
        else if(returnValue.getStatusCode() == HttpStatus.BAD_REQUEST) logger.info("Error while indexing Action");
    }

    @AfterReturning(value = "execution(* ftn..ActionController.multiFileUploadModel(..))", returning = "returnValue")
    public void logAfterMultiFileUploadModel(JoinPoint joinPoint, ResponseEntity<Void> returnValue){
        if(returnValue.getStatusCode() == HttpStatus.OK) logger.info("Successfully indexed Article");
        else if(returnValue.getStatusCode() == HttpStatus.BAD_REQUEST) logger.info("Error while indexing Article");
        else if(returnValue.getStatusCode() == HttpStatus.UNAUTHORIZED) logger.info("Unauthorized indexing of Article");
    }

    @AfterReturning(value = "execution(* ftn..ActionController.delete(..))", returning = "returnValue")
    public void logAfterArticleDelete(JoinPoint joinPoint, ResponseEntity<String> returnValue){
        String id = returnValue.getBody();

        if(returnValue.getStatusCode() == HttpStatus.OK) logger.info("Successfully deleted Article with {} id", id);
        else if(returnValue.getStatusCode() == HttpStatus.BAD_REQUEST) logger.info("Error while deleting Article with {} id", id);
        else if(returnValue.getStatusCode() == HttpStatus.UNAUTHORIZED) logger.info("Unauthorized Article delete with {} id", id);
    }

    @AfterReturning(value = "execution(* ftn..ActionController.update(..))", returning = "returnValue")
    public void logAfterArticleUpdate(JoinPoint joinPoint, ResponseEntity<String> returnValue){
        String id = returnValue.getBody();

        if(returnValue.getStatusCode() == HttpStatus.OK) logger.info("Successfully updated Article with {} id", id);
        else if(returnValue.getStatusCode() == HttpStatus.BAD_REQUEST) logger.info("Error while updating Article with {} id", id);
        else if(returnValue.getStatusCode() == HttpStatus.UNAUTHORIZED) logger.info("Unauthorized Article update with {} id", id);
    }

    @AfterReturning(value = "execution(* ftn..CustomerController.block(..))", returning = "returnValue")
    public void logAfterCustomerBlock(JoinPoint joinPoint, ResponseEntity<String> returnValue){
        String id = returnValue.getBody();

        if(returnValue.getStatusCode() == HttpStatus.OK) logger.info("Successfully blocked Customer with {} id", id);
        else if(returnValue.getStatusCode() == HttpStatus.BAD_REQUEST) logger.info("Error while trying to block Customer with {} id", id);
        else if(returnValue.getStatusCode() == HttpStatus.UNAUTHORIZED) logger.info("Unauthorized Customer block with {} id", id);
    }

    @AfterReturning(value = "execution(* ftn..DefaultUserController.changeCustomerPassword(..))", returning = "returnValue")
    public void logAfterCustomerPasswordUpdate(JoinPoint joinPoint, ResponseEntity<String> returnValue){
        String id = returnValue.getBody();

        if(returnValue.getStatusCode() == HttpStatus.OK) logger.info("Successfully updated Customer password with {} id", id);
        else if(returnValue.getStatusCode() == HttpStatus.BAD_REQUEST) logger.info("Error while updating Customer password with {} id", id);
        else if(returnValue.getStatusCode() == HttpStatus.UNAUTHORIZED) logger.info("Unauthorized Customer password update with {} id", id);
    }

    @AfterReturning(value = "execution(* ftn..DefaultUserController.changeStorePassword(..))", returning = "returnValue")
    public void logAfterStorePasswordUpdate(JoinPoint joinPoint, ResponseEntity<String> returnValue){
        String id = returnValue.getBody();

        if(returnValue.getStatusCode() == HttpStatus.OK) logger.info("Successfully updated Store password with {} id", id);
        else if(returnValue.getStatusCode() == HttpStatus.BAD_REQUEST) logger.info("Error while updating Store password with {} id", id);
        else if(returnValue.getStatusCode() == HttpStatus.UNAUTHORIZED) logger.info("Unauthorized Store password update with {} id", id);
    }

    @AfterReturning(value = "execution(* ftn..DefaultUserController.updateCustomer(..))", returning = "returnValue")
    public void logAfterCustomerUpdate(JoinPoint joinPoint, ResponseEntity<String> returnValue){
        String id = returnValue.getBody();

        if(returnValue.getStatusCode() == HttpStatus.OK) logger.info("Successfully updated Customer with {} id", id);
        else if(returnValue.getStatusCode() == HttpStatus.BAD_REQUEST) logger.info("Error while updating Customer with {} id", id);
        else if(returnValue.getStatusCode() == HttpStatus.UNAUTHORIZED) logger.info("Unauthorized Customer update with {} id", id);
    }

    @AfterReturning(value = "execution(* ftn..DefaultUserController.updateStore(..))", returning = "returnValue")
    public void logAfterStoreUpdate(JoinPoint joinPoint, ResponseEntity<String> returnValue){
        String id = returnValue.getBody();

        if(returnValue.getStatusCode() == HttpStatus.OK) logger.info("Successfully updated Store with {} id", id);
        else if(returnValue.getStatusCode() == HttpStatus.BAD_REQUEST) logger.info("Error while updating Store with {} id", id);
        else if(returnValue.getStatusCode() == HttpStatus.UNAUTHORIZED) logger.info("Unauthorized Store update with {} id", id);
    }

    @AfterReturning(value = "execution(* ftn..OrderController.comment(..))", returning = "returnValue")
    public void logAfterOrderComment(JoinPoint joinPoint, ResponseEntity<String> returnValue){
        String id = returnValue.getBody();

        if(returnValue.getStatusCode() == HttpStatus.OK) logger.info("Successfully commented Order with {} id", id);
        else if(returnValue.getStatusCode() == HttpStatus.BAD_REQUEST) logger.info("Error while commenting Order with {} id", id);
        else if(returnValue.getStatusCode() == HttpStatus.UNAUTHORIZED) logger.info("Unauthorized Order comment with {} id", id);
    }

    @AfterReturning(value = "execution(* ftn..OrderController.setDeliveryStatus(..))", returning = "returnValue")
    public void logAfterOrderDeliveryStatus(JoinPoint joinPoint, ResponseEntity<String> returnValue){
        String id = returnValue.getBody();

        if(returnValue.getStatusCode() == HttpStatus.OK) logger.info("Successfully set delivery Status for Order with {} id", id);
        else if(returnValue.getStatusCode() == HttpStatus.BAD_REQUEST) logger.info("Error while setting delivery status for Order with {} id", id);
        else if(returnValue.getStatusCode() == HttpStatus.UNAUTHORIZED) logger.info("Unauthorized setting of delivery status for Order with {} id", id);
    }
}
