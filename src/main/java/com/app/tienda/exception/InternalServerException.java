package com.app.tienda.exception;

//Excepcion para manejar errores internos del servidor - 500
public class InternalServerException extends RuntimeException {
  public InternalServerException(String message) {
    super(message); // con el super se hace el envio de informacion al constructor de RuntimeException
  }
  public InternalServerException(String message, Throwable cause) {
    super(message, cause); // con el super se hace el envio de informacion al constructor de RuntimeException
  }
}
