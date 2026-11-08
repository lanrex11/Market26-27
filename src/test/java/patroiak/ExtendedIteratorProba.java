package patroiak;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import domain.Sale;
import enums.SaleType;
import iterator.ExtendedIterator;

public class ExtendedIteratorProba {
	public static void main(String args[]) {

		//Datuak prestatu
		String desc = ""; 									//Ez da iragazirik idatzi 
		Date pubDate = new Date(); 							//Momentuko data
		SaleType query = SaleType.PUBLISHED_SALES; 			//Erosteko publikazioak
		String email = "";									//Erregistratugabeko erabiltzailea
		String sellerEmail = "";							//Oraindik ez da saltzaile baten produkturik aukeratu
		ArrayList<Sale> basket = new ArrayList<Sale>();		//Ez da produkturik sartu saskian

		
		BLFacade facade = new BLFacadeImplementation();

		//Oinarrizko metodoa 
		List<Sale> ls = facade.getQuery(desc, pubDate, query, email, sellerEmail, basket);
		System.out.println(ls);
		
		//Metodoari deitu
		ExtendedIterator<Sale> it = facade.getQueryIterator(desc, pubDate, query, email, sellerEmail, basket);

		Sale s = null;

		System.out.println("\nAtzetik aurrera");
		it.goLast();
		String reversed = "";
		while (it.hasPrevious()) {
			s = it.previous();
			System.out.println(s);
			reversed = s.toString() + reversed;
		}
		
		String normal = "";
		System.out.println("\nAurretik atzera");
		it.goFirst();

		while (it.hasNext()) {
			s = it.next();
			System.out.println(s);
			normal = normal + s.toString();
		}
		
		System.out.println("Berdinak? " + (reversed.equals(normal) && !reversed.equals("")));
		
	}

}
