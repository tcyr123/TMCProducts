package edu.slcc.asdv.bl;

import java.util.Collection;

public class ShoppingCart
    {
        FactoryStore fs = new FactoryStore<>();
        Product<Keyable> cart;
        
        public ShoppingCart(){
            fs = new FactoryStore<>();
            cart = fs.createDao();
        }
        public synchronized void addToCart(Item item){
            this.cart.create(item);
        }
        public synchronized void addToCart(Item item, int qty){
            for (int i = 0; i < qty; i++) {
                this.cart.create(item);
            }
        }
        public synchronized void removeFromCart(String item_no){
            cart.delete(findInShoppingCart(item_no));
        }
        
        public synchronized Item findInShoppingCart(String item_no)
        {
            for (Keyable e: listAll())
            {
                if(e.getKey().toString().equals(item_no)){
                    return (Item)e;
                }
            }
            return null;
        }
        public synchronized void plusOneQty(String item_no)
        {
            Item toBemodified = findInShoppingCart(item_no);
            toBemodified.setQty(String.valueOf(Integer.valueOf(toBemodified.getQty()) + 1));
        }
        
        public synchronized void subOneQty(String item_no)
        {
            Item toBemodified = findInShoppingCart(item_no);
            if(Integer.valueOf(toBemodified.getQty()) > 1)
                toBemodified.setQty(String.valueOf(Integer.valueOf(toBemodified.getQty()) - 1));
            else
                removeFromCart(item_no);
        }
        public synchronized Collection<Keyable> listAll(){
            return cart.findAll();
        }
        
        
        
//        public static void main(String[] args) {
//        ShoppingCart sp = new ShoppingCart();
//        Item chosenOne = Query.pfs.findAll2Arg("5");
//        
//        sp.addToCart(chosenOne);
        
//        sp.addToCart(new Item("iphone 11 Pro", "999.99", 
//                "phone", "pic_ref", "45", "1", "a phone"));
//        sp.addToCart(new Item("Android bean", "799.99", 
//                "phone", "pic_ref", "55", "2", "a phone again"));
//        sp.addToCart(new Item("Macbook Pro", "1999.99", 
//                "computer", "pic_ref", "20", "3", "a computer"));
//        sp.removeFromCart(new Item("2"));
        
        //System.out.println(sp.checkOut());
        
        
    }
    
