package com.example.bibliotheque;

public class Livre
{
    private int id;
    private String isbn;
    private String titre;

    public Livre(){

    }

    public Livre(String isbn, String titre){
        this.isbn = isbn;
        this.titre = titre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String toString (){
        return "ID"+this.id+"\nISBN : "+this.isbn+"\nTitre : "+this.titre;
    }
}
