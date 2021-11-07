package org.ksun.pets.udemy.course.instademo.payload.request;

import lombok.Data;
import org.ksun.pets.udemy.course.instademo.annotations.PasswordMatches;
import org.ksun.pets.udemy.course.instademo.annotations.ValidEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class SignupRequest {

    @Email(message = "It should have email format")
    @NotBlank(message = "User email is required")
    @ValidEmail
    private String email;

    @NotEmpty(message = "User firstname is required")
    private String firstname;

    @NotEmpty(message = "User lastname is required")
    private String lastname;

    @NotEmpty(message = "User username is required")
    private String username;

    @NotEmpty(message = "User password is required")
    @Size(min = 6)
    private String password;

//    @NotEmpty(message = "User confirmPassword is required")
    private String confirmPassword;


}
