package models;

import com.google.gson.JsonObject;

public class User {

    public String nome;
    public String email;
    public String password;
    public String _id;
    public String administrador;
    public String token;

    public User(String nome, String email, String password, String administrador){
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.administrador = administrador;
    }

    public void setUserId(String userId) { this._id = userId; }

    public void setUserToken(String token){
        this.token = token;
    }

    public String getUserToken(){
        return this.token;
    }

    public String getUserCredentials(){
        JsonObject userJsonRepresentation = new JsonObject();
        userJsonRepresentation.addProperty("email", this.email);
        userJsonRepresentation.addProperty("password", this.password);
        return userJsonRepresentation.toString();
    }

}
