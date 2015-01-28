package jsf;

import jpa.entities.TblVraag;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.session.TblVraagFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import jpa.entities.TblUser;

@ManagedBean(name = "tblVraagController")
@SessionScoped
public class TblVraagController implements Serializable {
    
    private TblVraag current;
    private DataModel items = null;
    @EJB
    private jpa.session.TblVraagFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String difficulty;
    private double difficultyHigh;
    private double difficultyLow;
    private List<TblVraag> vragen = new ArrayList<>();
    private List<TblVraag> vragenCollectie = new ArrayList<>();
    private int indexVragen;
    private String gegevenAntwoord;
    private double userScore = 0;
    private int countVragen;
    private int countJuist;
    private String multipleChoiceAnswer;
    private String multipleChoiceA;
    private String multipleChoiceB;
    private String multipleChoiceC;
    private String multipleChoiceD;

    public TblVraagController() {
    }
    
    public void checkAntwoord()
    {
        if(current.getAntwoord().equalsIgnoreCase(gegevenAntwoord))
        {
            int count = current.getJuisteAntwoorden();
            count++;
            current.setJuisteAntwoorden(count);
            
            int countGesteld = current.getAantalGesteld();
            countGesteld++;
            current.setAantalGesteld(countGesteld);
            
            double average = count/countGesteld*100;
            
            current.setMoeilijkheidsgraad(average);
            
            setCountVragen(getCountVragen() + 1);
            setCountJuist(getCountJuist() + 1);
            setUserScore(countJuist*100/countVragen);
        }
        else
        {
            int count = current.getJuisteAntwoorden();
            
            int countGesteld = current.getAantalGesteld();
            current.setAantalGesteld(countGesteld++);
            
            double average = count/countGesteld*100;
            
            current.setMoeilijkheidsgraad(average);
            
            setCountVragen(getCountVragen() + 1);
            setUserScore(countJuist*100/countVragen);
        }
    }
    
    public String nextQuestion()
    {
        checkAntwoord();
        updateLijst();
        if(indexVragen == 0)
        {
            return "/index";
        }
        else if(current.getType().equalsIgnoreCase("Normaal"))
        {
            return"/questions/normalQuestion";
        }
        else if(current.getType().equalsIgnoreCase("Multiplechoice"))
        {
            String string = current.getExtraInfo();
            String[] parts = string.split(";");
            multipleChoiceA = parts[0];
            multipleChoiceB = parts[1];
            multipleChoiceC = parts[2];
            multipleChoiceD = parts[3];

            return"/questions/multiplechoiceQuestion";
        }
        else
        {
            return"/questions/photoQuestion";
        }
        
    }
    
    public String firstQuestion()
    {
        userScore=0;
        countJuist =0;
        countVragen=0;
        
        difficultyToDouble();
        setVragenLijst();
        if(current.getType().equalsIgnoreCase("Normaal"))
        {
            return"/questions/normalQuestion";
        }
        else if(current.getType().equalsIgnoreCase("Multiplechoice"))
        {
            return"/questions/multiplechoiceQuestion";
        }
        else
        {
            return"/questions/photoQuestion";
        }
    }
    
    public void updateLijst()
    { 
        current = vragen.get(indexVragen-1);
        vragen.remove(indexVragen);
        indexVragen--;
    }
    
    public void setVragenLijst()
    {
        indexVragen = 9;
        List<TblVraag> list = new ArrayList<>();
        list = getFacade().findAll();
        
        for(TblVraag target: list)
        {
            if(target.getMoeilijkheidsgraad() >= getDifficultyLow() && target.getMoeilijkheidsgraad() <= getDifficultyHigh())
            {
                vragenCollectie.add(target);
            }
        }
        
        Random rand = new Random();
        for(int i = 0; i < 10; i++)
        {
            int n = rand.nextInt(vragenCollectie.size());
            vragen.add(vragenCollectie.get(n));
        }
        current = vragen.get(indexVragen);
    }
    
    public void difficultyToDouble()
    {
        if(difficulty.equalsIgnoreCase("3"))
        {
            setDifficultyLow(67.0);
            setDifficultyHigh(100.0);
        }
        else if(difficulty.equalsIgnoreCase("2"))
        {
            setDifficultyLow(34.0);
            setDifficultyHigh(66.0);
        }
        else
        {
            setDifficultyLow(0.0);
            setDifficultyHigh(33.0);
        }
    }

    public TblVraag getSelected() {
        if (current == null) {
            current = new TblVraag();
            selectedItemIndex = -1;
        }
        return current;
    }

    private TblVraagFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (TblVraag) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new TblVraag();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("TblVraagCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (TblVraag) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public void update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("TblVraagUpdated"));
            nextQuestion();
            
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public String destroy() {
        current = (TblVraag) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("TblVraagDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    /**
     * @return the difficulty
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * @param difficulty the difficulty to set
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * @return the difficultyHigh
     */
    public double getDifficultyHigh() {
        return difficultyHigh;
    }

    /**
     * @param difficultyHigh the difficultyHigh to set
     */
    public void setDifficultyHigh(double difficultyHigh) {
        this.difficultyHigh = difficultyHigh;
    }

    /**
     * @return the difficultyLow
     */
    public double getDifficultyLow() {
        return difficultyLow;
    }

    /**
     * @param difficultyLow the difficultyLow to set
     */
    public void setDifficultyLow(double difficultyLow) {
        this.difficultyLow = difficultyLow;
    }

    /**
     * @return the gegevenAntwoord
     */
    public String getGegevenAntwoord() {
        return gegevenAntwoord;
    }

    /**
     * @param gegevenAntwoord the gegevenAntwoord to set
     */
    public void setGegevenAntwoord(String gegevenAntwoord) {
        this.gegevenAntwoord = gegevenAntwoord;
    }

    /**
     * @return the userScore
     */
    public double getUserScore() {
        return userScore;
    }

    /**
     * @param userScore the userScore to set
     */
    public void setUserScore(double userScore) {
        this.userScore = userScore;
    }

    /**
     * @return the countVragen
     */
    public int getCountVragen() {
        return countVragen;
    }

    /**
     * @param countVragen the countVragen to set
     */
    public void setCountVragen(int countVragen) {
        this.countVragen = countVragen;
    }

    /**
     * @return the countJuist
     */
    public int getCountJuist() {
        return countJuist;
    }

    /**
     * @param countJuist the countJuist to set
     */
    public void setCountJuist(int countJuist) {
        this.countJuist = countJuist;
    }

    /**
     * @return the multipleChoiceAnswer
     */
    public String getMultipleChoiceAnswer() {
        return multipleChoiceAnswer;
    }

    /**
     * @param multipleChoiceAnswer the multipleChoiceAnswer to set
     */
    public void setMultipleChoiceAnswer(String multipleChoiceAnswer) {
        this.multipleChoiceAnswer = multipleChoiceAnswer;
    }

    /**
     * @return the multipleChoiceA
     */
    public String getMultipleChoiceA() {
        return multipleChoiceA;
    }

    /**
     * @param multipleChoiceA the multipleChoiceA to set
     */
    public void setMultipleChoiceA(String multipleChoiceA) {
        this.multipleChoiceA = multipleChoiceA;
    }

    /**
     * @return the multipleChoiceB
     */
    public String getMultipleChoiceB() {
        return multipleChoiceB;
    }

    /**
     * @param multipleChoiceB the multipleChoiceB to set
     */
    public void setMultipleChoiceB(String multipleChoiceB) {
        this.multipleChoiceB = multipleChoiceB;
    }

    /**
     * @return the multipleChoiceC
     */
    public String getMultipleChoiceC() {
        return multipleChoiceC;
    }

    /**
     * @param multipleChoiceC the multipleChoiceC to set
     */
    public void setMultipleChoiceC(String multipleChoiceC) {
        this.multipleChoiceC = multipleChoiceC;
    }

    /**
     * @return the multiplChoiceD
     */
    public String getMultipleChoiceD() {
        return multipleChoiceD;
    }

    /**
     * @param multiplChoiceD the multiplChoiceD to set
     */
    public void setMultipleChoiceD(String multipleChoiceD) {
        this.multipleChoiceD = multipleChoiceD;
    }

    @FacesConverter(forClass = TblVraag.class)
    public static class TblVraagControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TblVraagController controller = (TblVraagController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tblVraagController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof TblVraag) {
                TblVraag o = (TblVraag) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + TblVraag.class.getName());
            }
        }

    }

}
