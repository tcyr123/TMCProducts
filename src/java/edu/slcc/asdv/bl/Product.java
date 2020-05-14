
package edu.slcc.asdv.bl;

import java.util.Collection;

public interface Product<T extends Keyable>
{

    public void create(T t);

    public void delete(T t);

    public void update(T t);

    public T find(T t);

    public Collection<T> findAll();

}
