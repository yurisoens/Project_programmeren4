package jpa.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-26T01:44:39")
@StaticMetamodel(TblVraag.class)
public class TblVraag_ { 

    public static volatile SingularAttribute<TblVraag, String> antwoord;
    public static volatile SingularAttribute<TblVraag, Integer> juisteAntwoorden;
    public static volatile SingularAttribute<TblVraag, Integer> aantalGesteld;
    public static volatile SingularAttribute<TblVraag, String> categorie;
    public static volatile SingularAttribute<TblVraag, Double> moeilijkheidsgraad;
    public static volatile SingularAttribute<TblVraag, Integer> id;
    public static volatile SingularAttribute<TblVraag, String> media;
    public static volatile SingularAttribute<TblVraag, String> type;
    public static volatile SingularAttribute<TblVraag, String> vraag;
    public static volatile SingularAttribute<TblVraag, String> extraInfo;

}