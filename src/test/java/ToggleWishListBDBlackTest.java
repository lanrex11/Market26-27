import static org.junit.Assert.assertEquals;
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

public class ToggleWishListBDBlackTest {
	static DataAccess sut = new DataAccess();
	
	static TestDataAccess testDa = new TestDataAccess();
	
	@SuppressWarnings("unused")
	private Registered regist; 
	private String email = "aimar@gmail.com";
	private Sale sale;
	private String username = "Aimar";
	private String pass = "1234";
	
	@Before
	public void initTest() {
		testDa.open();
		testDa.createRegistered("salePortaduna", "seller-a", "1234");
		sale = testDa.addSaleToRegistered("salePortaduna", "baloia", "oso polita", 1, 20, null, null);
		testDa.close();
	}
	
	@After
	public void finishTest() {
		testDa.open();
		testDa.removeRegistered(email);
		if (sale != null) {
			testDa.removeSale(sale.getSaleNumber());
		}
		testDa.removeRegistered("salePortaduna");
		testDa.close();
	}
	
	
	@Test
	public void test1() {
		testDa.open();
		testDa.createRegistered(email, username, pass);
		testDa.close();
		
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
	public void test2() {
		testDa.open();
		testDa.createRegistered(email, username, pass);
		testDa.addSaleToWishList(email, sale.getSaleNumber());
		testDa.close();
		
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
	public void test3() {
		try {
			sut.open();
			boolean result = sut.toggleWishList(null, sale.getSaleNumber());
			sut.close();
			
			assertFalse(result);
		}catch (Exception e) {
			e.printStackTrace();
			fail("Ez luke salbuespenik altxa beharko");
		}
	}
	
}
