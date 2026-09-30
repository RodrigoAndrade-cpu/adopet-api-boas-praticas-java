package br.com.alura.adopet.api.exceptions;

public class TutorNaoEncontradoException extends RuntimeException {
    public TutorNaoEncontradoException(String message) {
        super(message);
    }
}
