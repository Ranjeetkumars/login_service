package com.pro.login.globlaexceptionhandler;


import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pro.login.controllerdto.Response;
import com.pro.login.enums.HttpStatus;
import com.pro.login.exceptions.DataNotFoundException;
import com.pro.login.exceptions.InSufficientInputException;

import org.hibernate.HibernateException;

/* @author VENKAT
* @since 2019-03-30
* @Time 3:58PM
*
*Global Exception Handler for All Exceptions
*/

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(DataNotFoundException.class)  
  public final Response handleUserNotFoundException(DataNotFoundException ex, WebRequest request) {
	  Response response = new Response();
	  response.setResponseCode(HttpStatus.NO_DATA_FOUND.value());
	  response.setStatus(HttpStatus.NO_DATA_FOUND.getReasonPhrase());
	  response.setErrorsMsgs(HttpStatus.NO_DATA_FOUND.getErrorMessages());
    return response ;
  }
  
  @ExceptionHandler(SocketException.class)  
  public final Response handleSocketException(SocketException ex, WebRequest request) {
	  Response response = new Response();
	  response.setResponseCode(HttpStatus.SOCKET_TIME_OUT.value());
	  response.setStatus(HttpStatus.SOCKET_TIME_OUT.getReasonPhrase());
	  response.setErrorsMsgs(HttpStatus.SOCKET_TIME_OUT.getErrorMessages());
    return response ;
  }
  @ExceptionHandler(HibernateException.class)  
  public final Response handleHibernateException(HibernateException ex, WebRequest request) {
	  Response response = new Response();
	  response.setResponseCode(HttpStatus.POSTGRES_CONNECTION_CLOSED.value());
	  response.setStatus(HttpStatus.POSTGRES_CONNECTION_CLOSED.getReasonPhrase());
	  List<String> errMsg=new ArrayList<String>();
	  errMsg.add(ex.toString());
	  response.setErrorsMsgs(errMsg);
    return response ;
  }
  
  @ExceptionHandler(InSufficientInputException.class)  
  public final Response handleInSufficientInputException(InSufficientInputException ex, WebRequest request) {
	  Response response = new Response();
	  response.setResponseCode(HttpStatus.INSUFFICIEN_INPUT.value());
	  response.setStatus(HttpStatus.INSUFFICIEN_INPUT.getReasonPhrase());
	  List<String> errMsg=new ArrayList<String>();
	  errMsg.add(ex.toString());
	  response.setErrorsMsgs(errMsg);
    return response ;
  }
}