package testOperations;
//aldaketak ikusteko ea sonar aldatzen den push egitean
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.Sale;
import domain.Registered;


public class TestDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();


	public TestDataAccess()  {

		System.out.println("TestDataAccess created");

		//open();

	}


	public void open(){


		String fileName=c.getDbFilename();

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			db = emf.createEntityManager();
		}
		System.out.println("TestDataAccess opened");


	}
	public void close(){
		db.close();
		System.out.println("TestDataAccess closed");
	}

	public boolean removeRegistered(String email) {
		System.out.println(">> TestDataAccess: removeSeller");
		Registered d = db.find(Registered.class, email);
		if (d!=null) {
			db.getTransaction().begin();
			db.remove(d);
			db.getTransaction().commit();
			return true;
		} else 
			return false;
	}
	public Registered createRegistered(String email, String name, String pass) {
		System.out.println(">> TestDataAccess: addSeller");
		Registered reg = null;
		db.getTransaction().begin();
		try {
			reg = db.find(Registered.class, email);

			//Ez bada existizen sortu
			if(reg == null) {
				reg = new Registered(email,name,pass);
				db.persist(reg);
			}
			db.getTransaction().commit();
			return reg;
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public boolean setBalance(String email, double balance) {
		Registered reg = null;
		boolean ondo = false;
		db.getTransaction().begin();
		try {
			reg = db.find(Registered.class, email);

			//Ez bada existizen sortu
			if(reg != null) {
				reg.setBalance(balance);
				db.persist(reg);
				ondo = true;
			}
			db.getTransaction().commit();
			return ondo;
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return false;

	}

	public boolean existRegistered(String email) {
		return db.find(Registered.class, email)!=null;


	}

	public Sale addSaleToRegistered(String email, String title, String description, int status, float price, Date pubDate, File file) {
		Sale sale = null;
		db.getTransaction().begin();
		try {
			Registered reg = db.find(Registered.class, email);
			if (reg != null) {
				sale = reg.addSale(title, description, status, price, pubDate, file);
			}
			db.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sale;
	}

	/*
	public boolean existSale(String sellerEmail, String title) {
		Registered s = db.find(Registered.class, sellerEmail);
		if (s!=null) {
			return s.doesSaleExist(title);
		} else 
			return false;
	}
	 */

	public Sale removeSale(int saleNumber) {
		System.out.println(">> TestDataAccess: removeRide");
		Sale s = db.find(Sale.class, saleNumber);
		if (s!=null) {
			db.getTransaction().begin();
			db.remove(s);
			db.getTransaction().commit();
			System.out.println("Removed");
			return s;

		} else 
			return null;

	}

	public void addSaleToWishList(String email, int saleNumber) {
		db.getTransaction().begin();
		Registered reg = db.find(Registered.class, email);
		Sale s = db.find(Sale.class, saleNumber);
		if (reg != null && s != null) {
			reg.addToWishList(s);
		}
		db.getTransaction().commit();
	}

}