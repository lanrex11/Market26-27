import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.booleanThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.Sale;
import domain.Registered;
import exceptions.FileNotUploadedException;
import exceptions.MustBeLaterThanTodayException;
import exceptions.SaleAlreadyExistException;
import testOperations.TestDataAccess;


public class ToggleWishListBDWhiteTest {
	// sut : system under test
	static DataAccess sut = new DataAccess(); 
	
	//additional operations needed to execute the test
	static TestDataAccess testDA= new TestDataAccess();
	
	@SuppressWarnings("unused")
	private Registered regist; 
	private String email = "aimar@gmail.com";
	private Sale sale;
	private String username = "Aimar";
	private String pass = "1234";
	
	@Before
	public void initTest() {
		testDA.open();
		testDA.createRegistered("salePortaduna", "seller-a", "1234");
		testDA.close();
	}
	
	@After
	public void finishTest() {
		testDA.open();
		testDA.removeRegistered("salePortaduna");
		testDA.close();
	}
	
	//Seller/Regsitered == null
	@Test
	public void test1() {
		//Sale-a sortu datu basean, toggleWishList-ek email eta sale number bat eskatzen dituelako.
		//Gainera lehen proban seller = null denean probatu nahi dut, sale ondo egonez
		testDA.open();
		sale = testDA.addSaleToRegistered("salePortaduna", "baloia", "oso polita", 1, 20, null, null);
		testDA.close();
		
		//email-ek sortu ez den usuario baten emaila da, hau da, find egitean null itzuliko du.
		try {
			sut.open();
			boolean result = sut.toggleWishList(email, sale.getSaleNumber());
			sut.close();
			
			//Ezabatu sortutako sale-a
			testDA.open();
			testDA.removeSale(sale.getSaleNumber());
			testDA.close();
			
			assertFalse(result);
		
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Ez luke salbuespenik altxa behar");
			testDA.open();
			testDA.removeSale(sale.getSaleNumber());
			testDA.close();
			fail();
		}
	}
	
	@Test
	public void test2() {
		testDA.open();
		testDA.createRegistered(email, username, pass);
		testDA.close();
		
		try {
			sut.open();
			boolean result = sut.toggleWishList(email, 99999999);
			sut.close();
			
			testDA.open();
			testDA.removeRegistered(email);
			testDA.close();
			
			assertFalse(result);
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Ez luke salbuespenik altxa beharko");
			testDA.open();
			testDA.removeSale(sale.getSaleNumber());
			testDA.close();
			fail();
		}
	}
	
	@Test
	public void test3() {
		testDA.open();
		regist = testDA.createRegistered(email, username, pass);
		sale = testDA.addSaleToRegistered("salePortaduna", "baloia", "oso polita", 1, 20, null, null);
		regist.addToWishList(sale);
		testDA.close();
		
		try {
			sut.open();
			boolean result = sut.toggleWishList(email, sale.getSaleNumber());
			sut.close();
			
			testDA.open();
			testDA.removeSale(sale.getSaleNumber());
			testDA.removeRegistered(email);
			testDA.close();
			
			assertTrue(result);
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Ez luke salbuespenik altxa beharko");
			testDA.open();
			testDA.removeSale(sale.getSaleNumber());
			testDA.removeRegistered(email);
			testDA.close();
			fail();
			
		}
	}
	
	@Test
	public void test4() {
		testDA.open();
		regist = testDA.createRegistered(email, username, pass);
		sale = testDA.addSaleToRegistered("salePortaduna", "baloia", "oso polita", 1, 20, null, null);
		testDA.close();
		
		try {
			sut.open();
			regist.addToWishList(sale);
			boolean result = sut.toggleWishList(email, sale.getSaleNumber());
			sut.close();
			
			testDA.open();
			testDA.removeSale(sale.getSaleNumber());
			testDA.removeRegistered(email);
			testDA.close();
			
			assertTrue(result);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Ez luke salbuespenik altxa beharko");
			testDA.open();
			testDA.removeSale(sale.getSaleNumber());
			testDA.removeRegistered(email);
			testDA.close();
			fail();
		}
		
	}
}