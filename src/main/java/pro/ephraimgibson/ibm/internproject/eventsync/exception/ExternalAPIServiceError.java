package pro.ephraimgibson.ibm.internproject.eventsync.exception;

public class ExternalAPIServiceError extends RuntimeException{
    public ExternalAPIServiceError(){
        super("Error while retrieving sentiment from External Api, Recheck your Token");
    }
}
