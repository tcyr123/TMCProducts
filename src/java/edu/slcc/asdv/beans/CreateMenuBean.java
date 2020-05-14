package edu.slcc.asdv.beans;

import edu.slcc.asdv.bl.Query;
import edu.slcc.asdv.bl.Item;
import java.io.Serializable;
import java.sql.SQLException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

@Named(value = "createMenuBean")
@SessionScoped
public class CreateMenuBean implements Serializable {
    private MenuModel model;
    
    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }
    
    /**
     * Creates left side menu from scratch
     * Talks to DB to get all current titles
     * @throws SQLException 
     */
    public void createMenu() throws SQLException {
        model = new DefaultMenuModel();

        firstSubmenu();//>add computers
        secondSubmenu();//>add phones

        //>add all items button
        DefaultMenuItem innerCSubmenu = new DefaultMenuItem("Show All");
        innerCSubmenu.setAjax(false);
        innerCSubmenu.setCommand("#{queryBean.setIsSearching(false)}");
        model.addElement(innerCSubmenu);
    }
    /**
     * Gets each computer title and adds it to the menu with an onClick command
     */
    public void firstSubmenu(){
        DefaultSubMenu firstSubmenu = new DefaultSubMenu("Computers");
        for(Item iC: Query.pfs.findAll2())
        {
            if("computer".equals(iC.getCategory())){
                DefaultMenuItem innerCSubmenu = //>adding title
                    new DefaultMenuItem(iC.getTitle());
                innerCSubmenu.setCommand("#{queryBean.setNumSelected("+
                        Integer.valueOf(iC.getItem_no()) + ")}");
            innerCSubmenu.setAjax(false);//>render page onClick
            firstSubmenu.getElements().add(innerCSubmenu);
            }     
        }
        model.addElement(firstSubmenu);
    }
    /**
     * Gets each phone title and adds it to the menu with an onClick command
     */
    public void secondSubmenu(){
        DefaultSubMenu secondSubmenu = new DefaultSubMenu("Phones");
        for(Item iC: Query.pfs.findAll2())
        {
            if("phone".equals(iC.getCategory())){
                DefaultMenuItem innerCSubmenu = //>adding title
                    new DefaultMenuItem(iC.getTitle());
                innerCSubmenu.setCommand("#{queryBean.setNumSelected("+
                        Integer.valueOf(iC.getItem_no()) + ")}");
            innerCSubmenu.setAjax(false);//>render page onClick
            secondSubmenu.getElements().add(innerCSubmenu);
            }     
        }
        
        model.addElement(secondSubmenu);
    }
    
}
