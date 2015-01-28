/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.entities.TblVraag;

/**
 *
 * @author yuri
 */
@Stateless
public class TblVraagFacade extends AbstractFacade<TblVraag> {
    @PersistenceContext(unitName = "WebAppQuizPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TblVraagFacade() {
        super(TblVraag.class);
    }
    
}
