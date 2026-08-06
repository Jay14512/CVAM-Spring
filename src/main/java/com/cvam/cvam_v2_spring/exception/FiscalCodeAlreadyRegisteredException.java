package com.cvam.cvam_v2_spring.exception;

//Extend RuntimeException so we don't force the service to use try/catch blocks everywhere

public class FiscalCodeAlreadyRegisteredException extends RuntimeException {
    public FiscalCodeAlreadyRegisteredException() {
        //Hard coding message since this exception has exactly one job
        super("Fiscal Code is already registered.");
    }
}
