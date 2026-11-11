package factory;

import businessLogic.BLFacade;

public interface IFacadeFactory {

    BLFacade createFacade() throws Exception;

}