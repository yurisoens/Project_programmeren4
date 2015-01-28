/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yuri
 */
@Entity
@Table(name = "tbl_vraag")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblVraag.findAll", query = "SELECT t FROM TblVraag t"),
    @NamedQuery(name = "TblVraag.findById", query = "SELECT t FROM TblVraag t WHERE t.id = :id"),
    @NamedQuery(name = "TblVraag.findByVraag", query = "SELECT t FROM TblVraag t WHERE t.vraag = :vraag"),
    @NamedQuery(name = "TblVraag.findByAntwoord", query = "SELECT t FROM TblVraag t WHERE t.antwoord = :antwoord"),
    @NamedQuery(name = "TblVraag.findByCategorie", query = "SELECT t FROM TblVraag t WHERE t.categorie = :categorie"),
    @NamedQuery(name = "TblVraag.findByJuisteAntwoorden", query = "SELECT t FROM TblVraag t WHERE t.juisteAntwoorden = :juisteAntwoorden"),
    @NamedQuery(name = "TblVraag.findByAantalGesteld", query = "SELECT t FROM TblVraag t WHERE t.aantalGesteld = :aantalGesteld"),
    @NamedQuery(name = "TblVraag.findByMoeilijkheidsgraad", query = "SELECT t FROM TblVraag t WHERE t.moeilijkheidsgraad = :moeilijkheidsgraad"),
    @NamedQuery(name = "TblVraag.findByExtraInfo", query = "SELECT t FROM TblVraag t WHERE t.extraInfo = :extraInfo"),
    @NamedQuery(name = "TblVraag.findByType", query = "SELECT t FROM TblVraag t WHERE t.type = :type")})
public class TblVraag implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "vraag")
    private String vraag;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "antwoord")
    private String antwoord;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "categorie")
    private String categorie;
    @Column(name = "juisteAntwoorden")
    private Integer juisteAntwoorden;
    @Column(name = "aantalGesteld")
    private Integer aantalGesteld;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "moeilijkheidsgraad")
    private Double moeilijkheidsgraad;
    @Size(max = 250)
    @Column(name = "extraInfo")
    private String extraInfo;
    @Size (max = 250)
    @Column(name = "media")
    private String media;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "type")
    private String type;

    public TblVraag() {
    }

    public TblVraag(Integer id) {
        this.id = id;
    }

    public TblVraag(Integer id, String vraag, String antwoord, String categorie, String type) {
        this.id = id;
        this.vraag = vraag;
        this.antwoord = antwoord;
        this.categorie = categorie;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVraag() {
        return vraag;
    }

    public void setVraag(String vraag) {
        this.vraag = vraag;
    }

    public String getAntwoord() {
        return antwoord;
    }

    public void setAntwoord(String antwoord) {
        this.antwoord = antwoord;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Integer getJuisteAntwoorden() {
        return juisteAntwoorden;
    }

    public void setJuisteAntwoorden(Integer juisteAntwoorden) {
        this.juisteAntwoorden = juisteAntwoorden;
    }

    public Integer getAantalGesteld() {
        return aantalGesteld;
    }

    public void setAantalGesteld(Integer aantalGesteld) {
        this.aantalGesteld = aantalGesteld;
    }

    public Double getMoeilijkheidsgraad() {
        return moeilijkheidsgraad;
    }

    public void setMoeilijkheidsgraad(Double moeilijkheidsgraad) {
        this.moeilijkheidsgraad = moeilijkheidsgraad;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblVraag)) {
            return false;
        }
        TblVraag other = (TblVraag) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.TblVraag[ id=" + id + " ]";
    }
    
}
