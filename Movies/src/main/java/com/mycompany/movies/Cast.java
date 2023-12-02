/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.movies;

/**
 *
 * @author danny
 */
public class Cast {
    private String name;
    private String role;

    public Cast(String name, String role) {
        this.name = name;
        this.role = role;
    }

 
    public String getName() {
        return name;
        }
    
    public void setName(String name) {
        this.name = name;
        }
    
    public String getRole() {
        return role;
        }
    
    public void setRole(String role) {
        this.role = role;
        }
}