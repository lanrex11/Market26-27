import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.Sale;
import domain.Registered;
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
		sale = testDA.addSaleToRegistered("salePortaduna", "baloia", "oso polita", 1, 20, null, null);
		testDA.close();
	}
	
	@After
	public void finishTest() {
		testDA.open();
		testDA.removeRegistered(email);
		if (sale != null) {
			testDA.removeSale(sale.getSaleNumber());
		}
		testDA.removeRegistered("salePortaduna");
		testDA.close();
	}
	
	@Test
	public void test1() {
		try {
			sut.open();
			boolean result = sut.toggleWishList(email, sale.getSaleNumber());
			sut.close();
			assertFalse(result);
		
		}catch(Exception e) {
			e.printStackTrace();
			fail("Ez luke salbuespenik altxa beharko");
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
			assertFalse(result);
			
		}catch(Exception e) {
			e.printStackTrace();
			fail("Ez luke salbuespenik altxa beharko");
		}
	}
	
	@Test
	public void test3() {
		testDA.open();
		regist = testDA.createRegistered(email, username, pass);
		testDA.close();
		
		try {
			sut.open();
			boolean result = sut.toggleWishList(email, sale.getSaleNumber());
			sut.close();			
			assertTrue(result);
			
		}catch (Exception e) {
			e.printStackTrace();
			fail("Ez luke salbuespenik altxa beharko");
			
		}
	}
	
	@Test
	public void test4() {
		testDA.open();
		regist = testDA.createRegistered(email, username, pass);
		testDA.addSaleToWishList(email, sale.getSaleNumber());
		testDA.close();
		
		try {
			sut.open();
			boolean result = sut.toggleWishList(email, sale.getSaleNumber());
			sut.close();
			assertTrue(result);
			
		}catch (Exception e) {
			e.printStackTrace();
			fail("Ez luke salbuespenik altxa beharko");
		}
		
	}
}