/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiz.BO.Services;

import java.util.List;
import quiz.BO.Vraag;
import quiz.DAL.DaVraag;

/**
 *
 * @author yuri
 */
public class VraagServices 
{
    private final DaVraag da = new DaVraag();
    public List<Vraag> getAllVragen()
    {
        return da.GetAll();
    }
    
    public void DeleteVraag(int ID, Vraag v)
    {
        DaVraag.delete(v);
    }
    
    public void UpdateVraag(int ID, Vraag v)
    {
        DaVraag.save(v);
    }
    
    public void SaveVraag(Vraag v)
    {
        DaVraag.save(v);
    }    
}
