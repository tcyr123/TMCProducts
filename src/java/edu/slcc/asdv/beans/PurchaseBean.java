package edu.slcc.asdv.beans;

import edu.slcc.asdv.bl.Item;
import edu.slcc.asdv.bl.Keyable;
import edu.slcc.asdv.bl.Query;
import edu.slcc.asdv.bl.ShoppingCart;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Destroyed;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;

@Named(value = "purchaseBean")
@SessionScoped
public class PurchaseBean implements Serializable{
    private boolean successfulPurchase = false;
    private String gonnaBuy, outcome;
    private ShoppingCart sc = new ShoppingCart();
    
    public boolean isSuccessfulPurchase() {
        return successfulPurchase;
    }

    public void setSuccessfulPurchase(boolean successfulPurchase) {
        this.successfulPurchase = successfulPurchase;
    }
        public String getGonnaBuy() {
        return gonnaBuy;
    }

    public void setGonnaBuy(String gonnaBuy) {
        this.gonnaBuy = gonnaBuy;
    }

    public String getOutcome() {
        return outcome;
    }

    public String setOutcome(String outcome) {
        this.outcome = outcome;
        return outcome;
    }

    public ShoppingCart getSc() {
        return sc;
    }

    public void setSc(ShoppingCart sc) {
        this.sc = sc;
    }

    
    public Collection<Keyable> cartList(){
        return getSc().listAll();
       //return sc.listAll();
    }


    /**
     * Setter for the current item the user is interested in (this.gonnaBuy)
     * @param item_no
     * @return buy.xhtml page
     * @throws SQLException 
     */
    public String buy(String item_no) throws SQLException {
        this.gonnaBuy = item_no;
        return "buy.xhtml";
    }
    /**
     * Talk to DB to subtract 1 from qty for the specified item
     * @throws SQLException 
     */
    public void subOne() throws SQLException {
        int qty = Integer.valueOf(
                InitializationBean.getWarehouse().getProducts().find2(this.gonnaBuy).getQty()) - 1;
        System.out.println("NEW QTY: " + qty);
        
        InitializationBean.getWarehouse().getProducts().find2(this.gonnaBuy).setQty(String.valueOf(qty));

        if (qty > 0) {
            Query.updateDBPurchase();
            successfulPurchase = true;//>shows "success" on page after buy
        }
        
    }
    private Item subtractFromPFS(String item_no, String addSub)
    {
        Item toBeCopied = Query.pfs.findAll2Arg(item_no);
        if(addSub.equals("add")){ 
            toBeCopied.setQty(String.valueOf(Integer.valueOf(toBeCopied.getQty())+1));
        }
        else if(addSub.equals("sub"))
        {
            toBeCopied.setQty(String.valueOf(Integer.valueOf(toBeCopied.getQty())-1)); 
        }
        else
        { //find qty in cart >> add PFS qty = qty in cart AKA resets the store to normal
           toBeCopied.setQty(String.valueOf(Integer.valueOf(toBeCopied.getQty()) + 
                   Integer.valueOf(sc.findInShoppingCart(item_no).getQty()))); 
        }
        return toBeCopied;
    }
    
    public void addToCart(String item_no){
        //check to see if item is already in cart >> if not >> add to cart
        if(sc.findInShoppingCart(item_no) == null){  
        Item toBeCopied = subtractFromPFS(item_no, "sub");
        Item chosenOne = new Item(toBeCopied.getTitle(), toBeCopied.getPrice(),
                toBeCopied.getCategory(), toBeCopied.getPicture_ref(),
                "1", toBeCopied.getItem_no(), toBeCopied.getDescription());
        sc.addToCart(chosenOne);
        }
    }
    public void plusOneQty(String item_no){
        sc.plusOneQty(item_no);
        subtractFromPFS(item_no, "sub");
    }
    public void subOneQty(String item_no){
        sc.subOneQty(item_no);
        subtractFromPFS(item_no, "add");
    }
    public void remove(String item_no){
        subtractFromPFS(item_no, "reset");
        sc.removeFromCart(item_no);
    }
    public void removeAll(){
        System.out.println("Remove all called");
        for(Keyable k: sc.listAll())
        {
            Item tempItem = sc.findInShoppingCart(k.getKey().toString());
            subtractFromPFS(tempItem.getItem_no(), "reset");
        }
        sc = new ShoppingCart();
    }
    
    public double totalAmount(){
        double total = 0.0;
        for(Keyable t: sc.listAll()){
            Item tempItem = sc.findInShoppingCart(t.getKey().toString());
            total+= Integer.valueOf(tempItem.getQty()) * Double.valueOf(tempItem.getPrice());
        }
        return total;
    }
    
    public int totalItems(){
        return sc.listAll().size();
    }
    
    public String checkOut(String myOutcome){
        if(myOutcome.equals("thanks")){
        //update DB
             try {
                 Query.updateDBPurchase();
                 System.out.println("UPDATE DB SUCCESS-------------------");
             } catch (SQLException ex) {
                 Logger.getLogger(PurchaseBean.class.getName()).log(Level.SEVERE, null, ex);
             }
             System.out.println("STEP 2-------------");
         //New Cart
         this.sc = new ShoppingCart();
    }
        return myOutcome;
    }
    
    
}
