package factory;

import configuration.ConfigXML;

public class FacadeFactory {

    public static IFacadeFactory getFactory(ConfigXML config) {

        if (config.isBusinessLogicLocal()) {
            return new LocalFacadeFactory();
        }

        return new RemoteFacadeFactory(config);
    }
}