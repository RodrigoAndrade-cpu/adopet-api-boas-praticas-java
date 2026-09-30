package br.com.alura.adopet.api.exceptions;

public class TutorJaExisteException extends RuntimeException {
    public TutorJaExisteException(String message) {
        super(message);
    }
}
