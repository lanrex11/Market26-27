package factory;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import businessLogic.BLFacade;
import configuration.ConfigXML;

public class RemoteFacadeFactory implements IFacadeFactory {

    private ConfigXML config;

    public RemoteFacadeFactory(ConfigXML config) {
        this.config = config;
    }

    @Override
    public BLFacade createFacade() throws Exception {

        String serviceName =
            "http://" + config.getBusinessLogicNode()
            + ":" + config.getBusinessLogicPort()
            + "/ws/" + config.getBusinessLogicName()
            + "?wsdl";

        URL url = new URL(serviceName);

        QName qname = new QName(
            "http://businessLogic/",
            "BLFacadeImplementationService"
        );

        Service service = Service.create(url, qname);

        return service.getPort(BLFacade.class);
    }
}