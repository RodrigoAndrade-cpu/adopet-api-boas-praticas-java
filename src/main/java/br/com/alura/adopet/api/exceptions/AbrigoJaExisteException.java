package br.com.alura.adopet.api.exceptions;

public class AbrigoJaExisteException extends RuntimeException {
    public AbrigoJaExisteException(String message) {
        super(message);
    }
}
