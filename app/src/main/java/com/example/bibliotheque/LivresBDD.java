package com.example.bibliotheque;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LivresBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "eleves.db";

    private static final String TABLE_LIVRES = "table_livres";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_ISBN = "ISBN";
    private static final int NUM_COL_ISBN = 1;
    private static final String COL_TITRE = "Titre";
    private static final int NUM_COL_TITRE = 2;

    private SQLiteDatabase bdd;

    private MaBaseSqLite maBaseSqLite;

    public LivresBDD(Context context){
        //on crée la BDD et sa table
        maBaseSqLite = new MaBaseSqLite(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = maBaseSqLite.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBdd(){
        return bdd;
    }

    public long insertLivre(Livre livre){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé
        values.put(COL_ISBN, livre.getIsbn());
        values.put(COL_TITRE, livre.getTitre());
        // on insère l'objet das la BDD via le ConetentValues
        return bdd.insert(TABLE_LIVRES, null, values);
    }

    public int updateLivre(int id, Livre livre){
        //La mise à jour d'un livre
        //on précise quel livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_ISBN,livre.getIsbn());
        values.put(COL_TITRE,livre.getTitre());
        return bdd.update(TABLE_LIVRES, values, COL_ID +" = " +id,null);
    }

    public int removeLivreWithID(int id){
        //Supression d'un livre de la BDD grâce à son ID
        return bdd.delete(TABLE_LIVRES, COL_ID + " = " +id, null);
    }

    public Livre getLivreWithTitre(String titre){
        //on récupère dans un Cursor les valeurs correspondant à in livre contenu dans la BDD (ici on séléctionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_LIVRES, new String[] {COL_ID, COL_ISBN, COL_TITRE},
                COL_TITRE + " LIKE \"" + titre+"\"", null,null,null,null);
        return cursorToLivre(c);
    }

    //Cette méthode permet de convertir un cursor en un livre
    private Livre cursorToLivre(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoi null
        if (c.getCount() == 0) {
            return null;
        } else {
            //Sinon on se place sur le premier élément
            c.moveToFirst();
            //On créé un livre
            Livre livre = new Livre();
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            livre.setId(c.getInt(NUM_COL_ID));
            livre.setIsbn(c.getString(NUM_COL_ISBN));
            livre.setTitre(c.getString(NUM_COL_TITRE));
            //on ferme le cursor
            c.close();

            //on retourne le livre
            return livre;
        }
    }
}
