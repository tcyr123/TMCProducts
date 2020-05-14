package edu.slcc.asdv.beans;

import edu.slcc.asdv.bl.Item;
import edu.slcc.asdv.bl.Query;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "queryBean")
@SessionScoped
public class QueryBean implements Serializable {

    

    private String searchQuery;
    private boolean isSearching = false;
    private boolean isSearchingManual = false;
        
    private int numSelected;


    //<editor-fold defaultstate="collapsed" desc="Basic Getters & Setters">
    /**
     * Getters and setters for everything
     * @return 
     */

    public boolean isIsSearchingManual() {
        return isSearchingManual;
    }

    public void setIsSearchingManual(boolean isSearchingManual) {
        this.isSearchingManual = isSearchingManual;
    }


    public int getNumSelected() {
        return numSelected;
    }

    public void setNumSelected(int numSelected) {
        this.numSelected = numSelected;
        this.isSearching = true;
        this.isSearchingManual=false;
    }

    public boolean isIsSearching() {
        return isSearching;
    }

    public void setIsSearching(boolean isSearching) {
        this.isSearching = isSearching;
        this.isSearchingManual = false;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }
//</editor-fold>
    public Collection<Item> findAllInfo2() throws SQLException
    {        
        return  Query.getPfs().findAll2();
    }

    /**
     * Gets current item on page's item_no then increases it or loops back to 1
     * (see getAllInfo for possible issue)
     * @throws SQLException 
     */
    public void roundaboutN() throws SQLException {
        if (InitializationBean.getWarehouse().getProducts().find2(String.valueOf(this.numSelected+1)) != null) 
        {
            this.numSelected += 1; //>increases item#
        } else {
            this.numSelected = 1; //>loops item# to beginning
        }
        searchNum(String.valueOf(this.numSelected));

    }
    /**
     * Gets current item on page's item_no then decreases it or loops back to
     * last number in inventory (see getAllInfo for possible issue)
     * @throws SQLException 
     */
    public void roundaboutP() throws SQLException {
        if (this.numSelected != 1) {
            this.numSelected -= 1; //>decreases item#
        } else {
            
            this.numSelected = InitializationBean.getWarehouse().getProducts().findAll2().size(); //>loops item# to end
        }
        searchNum(String.valueOf(this.numSelected));

    }
    

    //<editor-fold defaultstate="collapsed" desc="Major Searches">
    
    public void manualSearch()
    {
        this.isSearching = true;
        this.isSearchingManual = true;
    }
    /**
     * Used in search bar searches
     * Searches the allInformation[] for the specified title's data
     * If nothing contains exactly, remove a letter and re-search until 0
     * If STILL no results, then return all data
     * Sets the items data to projectedInformation / pushes to page
     * @return 
     * @throws SQLException 
     */
    public Collection<Item> search() throws SQLException {
        Collection<Item> t= Query.findSomeInfo(searchQuery);
        if(t.isEmpty())
            return Query.getPfs().findAll2();
        if(t.size() == 1)
            this.numSelected = Integer.valueOf(t.iterator().next().getItem_no());
        return t;
    }

    /**
     * Searches allInformation[] for the specific item_no's data
     * Sets the items data to this.projectedInformation / pushes to page
     * @param item_no
     * @return Item
     * @throws SQLException 
     */
    public Item searchNum(String item_no) throws SQLException {
        this.isSearching = true;
        return Query.search(item_no);
    }
    //</editor-fold>

}