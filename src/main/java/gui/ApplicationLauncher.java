package gui;

import java.awt.Color;
import java.net.URL;
import java.util.Locale;

import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import factory.FacadeFactory;
import factory.IFacadeFactory;
import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;

public class ApplicationLauncher { 
	
	
	
	public static void main(String[] args) {

		ConfigXML c=ConfigXML.getInstance();		
		Locale.setDefault(new Locale(c.getLocale()));
		



		try {
			
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			
			// Obtener la fábrica correspondiente
	        IFacadeFactory factory = FacadeFactory.getFactory(c);

	        // Crear la fachada
	        BLFacade appFacadeInterface = factory.createFacade();


			MainGUInonReg.setBussinessLogic(appFacadeInterface);
			
			MainGUInonReg a=new MainGUInonReg("seller3@gmail.com");
			a.setVisible(true);
			//ErreklamatuGUI g=new ErreklamatuGUI();
			//g.setVisible(true);
			
		}catch (Exception e) {
		//	a.jLabelSelectOption.setText("Error: "+e.toString());
		//	a.jLabelSelectOption.setForeground(Color.RED);	
			
			System.out.println("Error in ApplicationLauncher: "+e.toString());
		}


	}

}
