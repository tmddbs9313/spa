package com.ohgiraffers.restapi.section03.valid;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(UserNotFoundException e) {

        String code = "ERROR_CODE_0001";
        String description = "회원 정보 조회에 실패하셨습니다.";
        String detail = e.getMessage();

        return new ResponseEntity<>(new ErrorResponse(code, description, detail), HttpStatus.NOT_FOUND);
    }

    @PostMapping("/users")
    public ResponseEntity<?> registUser(@Valid @RequestBody UserDTO newUser) {

        System.out.println("user 들어오고 있니?" + newUser);

        return ResponseEntity.created(URI.create("/valid/users/" + newUser.getId())).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {

        String code = "";
        String description = "";
        String detail = "";

        /*유효성 검사에서 error 가 발생한다면?*/
        if (e.getBindingResult().hasErrors()) {
            detail = e.getBindingResult().getFieldError().getDefaultMessage(); // e.getMessage() 동일한 의미
            System.out.println("detail = " + detail);

            //NotNull, Size, NotBlank ...
            String bindResultCode = e.getBindingResult().getFieldError().getCode();

            switch (bindResultCode) {
                case "NotBlank":
                    code = "ERROR_CODE_0002";
                    description = "필수 값이 누락되었습니다!!!";
                    break;
                case "Size":
                    code = "ERROR_CODE_0003";
                    description = "글자 수를 확인해 주세요...";
                    break;
            }
        }

        return new ResponseEntity<>(new ErrorResponse(code, description, detail), HttpStatus.BAD_REQUEST);

    }

}
