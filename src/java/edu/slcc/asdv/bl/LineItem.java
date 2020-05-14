package edu.slcc.asdv.bl;

import java.io.Serializable;
import java.text.NumberFormat;

public class LineItem implements Serializable
{

    private Item product;
    private int quantity;

    public LineItem()
    {
    }

    public void setProduct(Item p)
    {
        product = p;
    }

    public Item getProduct()
    {
        return product;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public double getTotal()
    {
        double total = Double.valueOf(product.getPrice())* quantity;
        return total;
    }

    public String getTotalCurrencyFormat()
    {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(this.getTotal());
    }
}
