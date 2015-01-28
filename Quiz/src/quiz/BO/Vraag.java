/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiz.BO;

/**
 *
 * @author yuri
 */
public class Vraag 
{
    private int id;
    private String vraag;
    private String antwoord;
    private String categorie;
    private int juisteAntwoorden;
    private int aantalGesteld;
    private double moeilijkheidsgraad;
    private String extraInfo;
    private String media;
    private String type;

    public Vraag(int id, String vraag, String antwoord, String categorie, int juisteAntwoorden, int aantalGesteld, double moeilijkheidsgraad, String extraInfo, String media, String type)
    {
        this.id = id;
        this.vraag = vraag;
        this.antwoord = antwoord;
        this.categorie = categorie;
        this.juisteAntwoorden = juisteAntwoorden;
        this.aantalGesteld = aantalGesteld;
        this.moeilijkheidsgraad = moeilijkheidsgraad;
        this.extraInfo = extraInfo;
        this.media = media;
        this.type = type;
    }

    public Vraag() {}
    
    public String toString()
    {
        return "" + id + " " + vraag;
    }
    /**
     * @return the id
     */
    public int getID() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * @return the vraag
     */
    public String getVraag() {
        return vraag;
    }

    /**
     * @param vraag the vraag to set
     */
    public void setVraag(String vraag) {
        this.vraag = vraag;
    }

    /**
     * @return the antwoord
     */
    public String getAntwoord() {
        return antwoord;
    }

    /**
     * @param antwoord the antwoord to set
     */
    public void setAntwoord(String antwoord) {
        this.antwoord = antwoord;
    }

    /**
     * @return the categorie
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * @param categorie the categorie to set
     */
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    /**
     * @return the juisteAntwoorden
     */
    public int getJuisteAntwoorden() {
        return juisteAntwoorden;
    }

    /**
     * @param juisteAntwoorden the juisteAntwoorden to set
     */
    public void setJuisteAntwoorden(int juisteAntwoorden) {
        this.juisteAntwoorden = juisteAntwoorden;
    }

    /**
     * @return the aantalGesteld
     */
    public int getAantalGesteld() {
        return aantalGesteld;
    }

    /**
     * @param aantalGesteld the aantalGesteld to set
     */
    public void setAantalGesteld(int aantalGesteld) {
        this.aantalGesteld = aantalGesteld;
    }

    /**
     * @return the moeilijkheidsgraad
     */
    public double getMoeilijkheidsgraad() {
        return moeilijkheidsgraad;
    }

    /**
     * @param moeilijkheidsgraad the moeilijkheidsgraad to set
     */
    public void setMoeilijkheidsgraad(double moeilijkheidsgraad) {
        this.moeilijkheidsgraad = moeilijkheidsgraad;
    }

    /**
     * @return the extraInfo
     */
    public String getExtraInfo() {
        return extraInfo;
    }

    /**
     * @param extraInfo the extraInfo to set
     */
    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    /**
     * @return the media
     */
    public String getMedia() {
        return media;
    }

    /**
     * @param media the media to set
     */
    public void setMedia(String media) {
        this.media = media;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    
}
