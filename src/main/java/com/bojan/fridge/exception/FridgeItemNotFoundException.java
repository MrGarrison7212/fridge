package com.bojan.fridge.exception;

public class FridgeItemNotFoundException extends FridgeException{

    public FridgeItemNotFoundException(Long id) {
        super("Fridge item not found with id: " + id);
    }
}
