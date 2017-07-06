package com.ltchen.demo.hadoop.exception;

public class HdfsRestApiException extends Exception {
	
	private static final long serialVersionUID = 4639201464987267880L;

	public HdfsRestApiException() {
        super();
    }

    public HdfsRestApiException(String message) {
        super(message);
    }
    
    public HdfsRestApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public HdfsRestApiException(Throwable cause) {
    	super(cause);
    }
}
