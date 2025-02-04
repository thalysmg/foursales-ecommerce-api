package br.com.foursales.ecommerce.api.exceptions;

public class TokenInvalidoException extends Exception {
    public TokenInvalidoException(String msg) {
        super(msg);
    }
}
