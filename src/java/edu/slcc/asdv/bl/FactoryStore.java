package edu.slcc.asdv.bl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FactoryStore<K, V extends Keyable> extends Factory<Keyable>{
    
    private Map<K, V> map = new HashMap<>();

    @Override
    public Product<Keyable> createDao() {
        return new Product<Keyable>() {
            @Override
            public void create(Keyable t) {
                map.put((K) t.getKey(), (V)t);
            }

            @Override
            public void delete(Keyable t) {
                map.remove((K) t.getKey());
            }

            @Override
            public void update(Keyable t) {
                map.replace((K) t.getKey(), (V)t);
            }

            @Override
            public Keyable find(Keyable t) {
                return map.put((K) t.getKey(), (V)t);
            }

            @Override
            public Collection<Keyable> findAll() {
                return (Collection<Keyable>) map.values();
            }
            
//     public Item find2(K t)
//    {
//        return (Item) map.get(t);
//    }
//
//    public Collection<Item> findAll2()
//    {
//        return (Collection<Item>) map.values();
//    }
//    public Item findAll2Arg(String key)
//    {
//        return (Item) map.get(key);
//    }

//            @Override
//            public boolean updateDB() {
//                return true;
//            }
        };
    }
    
    

}
