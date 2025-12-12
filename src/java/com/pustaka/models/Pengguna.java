/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pustaka.models;

public abstract class Pengguna {
    protected int id;
    protected String nama;
    protected String password;

    public Pengguna() {}

    public Pengguna(int id, String nama, String password) {
        this.id = id;
        this.nama = nama;
        this.password = password;
    }

    // Abstract method yang wajib ada di anak-anaknya
    public abstract boolean login(String identifier, String password);

    // Getter Setter Standar
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}