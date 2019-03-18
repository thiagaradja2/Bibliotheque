package com.example.bibliotheque;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Création d'une instance de la classe LivresBDD
        LivresBDD livreBdd = new LivresBDD(this);

        //Création d'un livre
        Livre livre = new Livre ("123456789", "Programme pour Android");

        //On ouvre la base des données pour écrire dedans
        livreBdd.open();

        Livre myOldLivre = livreBdd.getLivreWithTitre("Old Livre");
        //Si aucun livre n'est retourné, c'est le cas à la première execution de l'application
        if(myOldLivre == null){
            //On affiche un message indiquant que le livre n'existe pas dans la BDD
            Toast.makeText(this, "l'ancien livre n'existe pas", Toast.LENGTH_LONG).show();
        }
        //Si le livre existe, c'est la cas à partir de la deuxième execution de l'application
        else {
            //on affiche un message indiquant que le livre existe dans la BDD
            Toast.makeText(this, "l'ancien livre existe",Toast.LENGTH_LONG).show();
        }

        //on insère le livre que l'on vient de créer
        livreBdd.insertLivre(livre);

        //pour vérifier que l'on a bien créé notre livre dans la BDD
        //on extrait le livre de la BDD grâce au titre du livre que l'on a créé précédemment
        Livre livreFromBdd = livreBdd.getLivreWithTitre(livre.getTitre());

        //Si un livre est retourné (donc si le livre a bien été ajouté à la BDD)
        if(livreFromBdd != null){
            //on affiche les infos du livre dans un toast
            Toast.makeText(this, livreFromBdd.toString(), Toast.LENGTH_LONG).show();
            //on modifie le titre du livre
            livreFromBdd.setTitre("J'ai modifié le titre du livre");
            //puis on met à jour la BDD
            livreBdd.updateLivre(livreFromBdd.getId(), livreFromBdd);
        }

        //on extrait le livre de la BDD grâce au nouveau titre
        livreFromBdd = livreBdd.getLivreWithTitre("J'ai modifié le titre du livre");
        //S'il existe un livre possédant ce titre dans la BDD
        if(livreBdd != null){
            //on affiche les nouvelles infos du livre pour vérifier que le torre du livre a bien été à jour
            Toast.makeText(this, livreFromBdd.toString(),Toast.LENGTH_LONG).show();
            //on supprime le livre de la BDD grâce son ID
            livreBdd.removeLivreWithID(livreFromBdd.getId());
        }

        //on essaye d'extraire de nouveau le livre de la BDD tooujours grâce à son nouveau titre
        livreFromBdd = livreBdd.getLivreWithTitre("J'ai modifié le titre du livre");
        if(livreFromBdd == null){
            //on affiche un message indiquant que le livre n'existe pas dans la BDD
            Toast.makeText(this, "Ce livre n'existe pas dans la BDD", Toast.LENGTH_LONG).show();
        }
        //si le livre existe (normalement il ne devrait pas)
        else{
            //on affiche un message indiquant que le livre existe dans la BDD
            Toast.makeText(this, "Ce livre existe dans la BDD", Toast.LENGTH_LONG).show();
        }
        //on cée un livre que l'on voudra retrouver à la prochaine éxecution de l'application
        Livre livre2 = new Livre("123456789", "Old Livre");
        livreBdd.insertLivre(livre2);

        livreBdd.close();
    }
}
