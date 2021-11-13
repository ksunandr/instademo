package org.ksun.pets.udemy.course.instademo.payload.response;

public class InvalidLoginResponse {

    private String username;
    private String password;

    public InvalidLoginResponse(){
        this.username = "Invalid Username1";
        this.password = "Invalid Password";

    }
}
