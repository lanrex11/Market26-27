import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.InjectMocks;

import dataAccess.DataAccess;
import domain.Registered;
import domain.Sale;

//Honekin JUnit-ri esaten diogu mockito erabiltzeko test bakoitza exekutatu baino lehen
@RunWith(MockitoJUnitRunner.class)
public class ToggleWishListMockWhiteTest {
	
	@Mock
	private EntityManager db;
	
	@Mock
	private EntityTransaction transaction;
	
	//Mockeatutako db eta transaction sut-ean txertatzeko, horrela hauek erabiliko ditu DB erreal bat beharrean
	@InjectMocks
	static DataAccess sut = new DataAccess();
	
	private Registered regist; 
	private String email = "aimar@gmail.com";
	private Sale sale;
	private String username = "Aimar";
	private String pass = "1234";
	private int saleNum = 10;

	@Before
	public void setUp() {
		when(db.getTransaction()).thenReturn(transaction);
		regist = new Registered(email,username,pass);
		sale = new Sale("Baloia", "Oso polita", 1, 20, null, null, regist);
		sale.setSaleNumber(saleNum);
		}
	
	@Test
	public void test1() {
		when(db.find(Registered.class, email)).thenReturn(null);
		when(db.find(Sale.class, saleNum)).thenReturn(sale);
		try {
			boolean result = sut.toggleWishList(email, saleNum);
			assertFalse(result);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Ez luke salbuespenik altxa behar");
			fail();
		}
	}
	
	@Test
	public void test2() {
		when(db.find(Registered.class, email)).thenReturn(regist);
		when(db.find(Sale.class, saleNum)).thenReturn(null);
		try {
			boolean result = sut.toggleWishList(email, saleNum);
			assertFalse(result);
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("Ez luke salbuespenik altxa behar");
			fail();
		}
	}
	
	@Test
	public void test3() {
		when(db.find(Registered.class, email)).thenReturn(regist);
		when(db.find(Sale.class, saleNum)).thenReturn(sale);
		try {
			boolean result = sut.toggleWishList(email, saleNum);
			assertTrue(result);
			assertTrue(regist.getWishList().contains(sale)); //Ikusi behar dugu ea wishList-era gehitu den edo ez
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Ez luke salbuespenik altxa behar");
			fail();
		}
	}
	
	@Test
	public void test4() {
		regist.addToWishList(sale);
		when(db.find(Registered.class, email)).thenReturn(regist);
		when(db.find(Sale.class, saleNum)).thenReturn(sale);
		try {
			boolean result = sut.toggleWishList(email, saleNum);
			assertTrue(result);
			assertFalse(regist.getWishList().contains(sale)); //Ikusi behar dugu ea wishList-etik ezabatu den edo ez
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Ez luke salbuespenik altxa behar");
			fail();
		}
	}
	
}
