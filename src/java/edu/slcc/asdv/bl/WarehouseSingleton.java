package edu.slcc.asdv.bl;

public class WarehouseSingleton<K, V extends Keyable>

{
    private static ProductsForSale<String, Keyable> productsForSale;
    private static WarehouseSingleton warehouse;

    private WarehouseSingleton()
    {
        productsForSale = new ProductsForSale();

    }

    public static WarehouseSingleton instantiateWarehouse()
    {
        if (warehouse == null)
          {
            warehouse = new WarehouseSingleton();
          }

        return warehouse;
    }

    public ProductsForSale<String, Keyable> getProducts()
    {
        return productsForSale;
    }
    


}
