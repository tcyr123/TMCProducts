package edu.slcc.asdv.bl;


public abstract class Factory<T extends Keyable> {
public abstract Product<T> createDao();
}
