package factory;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;

public class LocalFacadeFactory implements IFacadeFactory {

    @Override
    public BLFacade createFacade() {

        DataAccess da = new DataAccess();

        return new BLFacadeImplementation(da);
    }
}