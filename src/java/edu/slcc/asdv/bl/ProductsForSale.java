package edu.slcc.asdv.bl;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductsForSale<K, V extends Keyable>
        implements Product<Keyable>
{

    private Map<K, V> map = new HashMap<K, V>();
    

    @Override
    public void create(Keyable t)
    {
        map.put((K) t.getKey(), (V) t);
                try {
            Query.updateDBAdd((Item) t);
        } catch (SQLException ex) {
            Logger.getLogger(ProductsForSale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Keyable t)
    {
        map.remove(t.getKey());
                try {
            Query.updateDBDelete((Item) t);
        } catch (SQLException ex) {
            Logger.getLogger(ProductsForSale.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    @Override
    public void update(Keyable t)
    {
        map.replace((K) t.getKey(), (V) t);
        try {
            Query.updateDBSingle((Item) t);
        } catch (SQLException ex) {
            Logger.getLogger(ProductsForSale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Keyable find(Keyable t)
    {
        return map.get(t.getKey());
    } 

    @Override
    public Collection<Keyable> findAll()
    {
        return (Collection<Keyable>) map.values();
    }
    
        public Item find2(K t)
    {
        return (Item) map.get(t);
    }

    public Collection<Item> findAll2()
    {
        return (Collection<Item>) map.values();
    }
    public Item findAll2Arg(String key)
    {
        return (Item) map.get(key);
    }
}
