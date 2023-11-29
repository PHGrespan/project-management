package br.edu.unifaj.dto;

import lombok.Value;

import java.io.Serializable;

@Value
public class ResponseDto implements Serializable {

    String message;
}
