package org.nifiopa.nifiopa;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OPAResponse {
	
    private String allowed;
    private boolean dumpCache;
    private String message;

    @JsonCreator
    public OPAResponse(
    		@JsonProperty("allowed") String allowed, 
    		@JsonProperty("dumpCache") boolean dumpCache,
    		@JsonProperty("message") String message
    		) {
    	this.allowed = allowed;
    	this.dumpCache = dumpCache;
    	this.message = message;
    }
    
    public String allowed() {
        return allowed;
    }
    
    public boolean dumpCache() {
    	return dumpCache;
    }
    
    public String message() {
    	return message;
    }
}
