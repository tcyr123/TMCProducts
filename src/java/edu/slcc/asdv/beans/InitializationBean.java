package edu.slcc.asdv.beans;

import edu.slcc.asdv.bl.Keyable;
import edu.slcc.asdv.bl.ProductsForSale;
import edu.slcc.asdv.bl.Query;
import edu.slcc.asdv.bl.WarehouseSingleton;
import java.sql.SQLException;

import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;

@Named(value = "initializationBean")
@ApplicationScoped
public class InitializationBean {
private static WarehouseSingleton ws;
private static ProductsForSale<String, Keyable> pfs;
    
    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) throws SQLException{
        System.out.println("initialize singleton");
        ws = WarehouseSingleton.instantiateWarehouse();
        pfs = ws.getProducts();
        Query.getAllInfo2();
    }
    public static WarehouseSingleton getWarehouse(){
        return ws;
    }

    public static ProductsForSale<String, Keyable> getProducts() {
        return pfs;
    }
    
    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object init){
        //cleanup and save
    }
    public InitializationBean() {
        
    }
    
}
