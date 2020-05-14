
package edu.slcc.asdv.bl;

public interface Categorable<T extends CharSequence>
{
    public T getCategory();
    public void setCategory( T t );
    
}
