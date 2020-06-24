package com.zerocostproducts.hypenavigator.youtube;

/**
 * Exception to wrap any exceptions from http and deserialization
 */
public class ClientException extends RuntimeException {
    public ClientException(String message, Exception ex) {
        super(message, ex);
    }
}
