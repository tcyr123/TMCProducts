
package edu.slcc.asdv.bl;
import java.util.Objects;

public class Item 
        implements Keyable<String>, Comparable<Item>, Categorable<String>
{   
    private String title;  
    private String price;
    private String category;
    private String picture_ref;
    private String qty;
    private String item_no;
    private String description;
    
    public Item(){}
    public Item(String item_no){
    this.item_no = item_no;
}
    public Item(String title, String price, String category, String picture_ref,
            String qty, String item_no, String description) {
        this.title = title;
        this.price = price;
        this.category = category;
        this.picture_ref = picture_ref;
        this.qty = qty;
        this.item_no = item_no;
        this.description = description;
    }
    
    //<editor-fold defaultstate="collapsed" desc="getters&setters">
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPicture_ref() {
        return picture_ref;
    }

    public void setPicture_ref(String picture_ref) {
        this.picture_ref = picture_ref;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
//</editor-fold>


    @Override public String getKey(){return getItem_no();}
    @Override public void setKey(String key){ setItem_no(key);}

    @Override
    public String getCategory()
    {
        return category;
    }

    @Override
    public void setCategory(String t)
    {
         this.category = t;
    }

    @Override
    public int compareTo(Item o)
    {
        return this.item_no.compareTo(o.item_no);
    }

  

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
          {
            return true;
          }
        if (obj == null)
          {
            return false;
          }
        if (getClass() != obj.getClass())
          {
            return false;
          }
        final Item other = (Item) obj;
        if (!Objects.equals(this.item_no, other.item_no))
          {
            return false;
          }
        return true;
    }

    @Override
    public String toString()
    {
        return "Item{" + "category=" + category + ", item_no=" + item_no +
                ", title=" + title + ", description=" + description +
                ", price=" + price + ", qty=" + qty +'}';
    }
    
    
}
