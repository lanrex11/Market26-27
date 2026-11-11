package patroiak;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Registered;
import gui.MainGUInonReg;
import testOperations.TestDataAccess;
import adapter.*;

public class SellerAdapterProba {

	// sut : system under test
		static DataAccess sut = new DataAccess(); 
		
		//additional operations needed to execute the test
		static TestDataAccess testDA= new TestDataAccess();
		static private String email = "seller1@gmail.com";
		static private Registered r;
		
	public static void main(String[] args) {
		testDA.open();
		testDA.createRegistered(email, "Aimar", "1234");
		testDA.addSaleToRegistered(email, "futbol baloia", "oso polita, gutxi erabilita", 2, 10,  null, null);
		testDA.addSaleToRegistered(email, "salomon mendiko botak", "44 zenbakia, 3 ateraldi",2,  20,  null, null);
		testDA.addSaleToRegistered(email, "samsung 42\" telebista", "berria, erabili gabe", 1, 175,  null, null);
		testDA.close();
		
		sut.open();
		if (sut.isRegistered(email)) {
			r = sut.getRegistered(email);
			SellerTable st = new SellerTable(r);
			st.setVisible(true);
		} else {
			System.out.println("Ez dago erabiltzailerik email horrekin");
		}
		sut.close();
		
		testDA.open();
		testDA.removeRegistered(email);
		testDA.close();
	}
}
