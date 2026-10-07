import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Registered;
import domain.Sale;

public class ToggleWishListMockBlackTest {
	
	static DataAccess sut;
	protected MockedStatic<Persistence> persistenceMock;
	@Mock
	protected EntityManagerFactory entityManagerFactory;
	@Mock
	protected EntityManager db;
	@Mock
	protected EntityTransaction et;
	
	private Registered regist; 
	private String email = "aimar@gmail.com";
	private Sale sale;
	private String username = "Aimar";
	private String pass = "1234";
	private int saleNum = 10;
	
	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
		persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() ->
		Persistence.createEntityManagerFactory(Mockito.any())).thenReturn(entityManagerFactory);
		Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
		
		sut = new DataAccess(db);

		regist = new Registered(email, username, pass);
		sale = new Sale("Baloia", "Oso polita", 1, 20, null, null, regist);
		sale.setSaleNumber(saleNum);
		Mockito.when(db.find(Registered.class, email)).thenReturn(regist);
		Mockito.when(db.find(Registered.class, "alain@gmail.com")).thenReturn(null);
		Mockito.when(db.find(Registered.class, null)).thenThrow(new IllegalArgumentException());
		Mockito.when(db.find(Sale.class, saleNum)).thenReturn(sale);
	}
	@After
	public void tearDown() {
		persistenceMock.close();
	}
	
	/*
	@Test
	public void test1() {		
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
		regist.addToWishList(sale);
		
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
			
			fail("IllegalArgumentException altxa beharko luke");
		}catch (IllegalArgumentException e) {
			sut.close();
			assertTrue(true);
		}
	}
	
	@Test
	public void test4() {
		try {
			sut.open();
			boolean result = sut.toggleWishList("alain@gmail.com", sale.getSaleNumber());
			sut.close();
			
			assertFalse(result);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Ez luke salbuespenik altxa beharko");
		}
	}
	
	@Test
	public void test5() {
		try {
			sut.open();
			boolean result = sut.toggleWishList(email, sale.getSaleNumber()-1);
			sut.close();
			
			assertFalse(result);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Ez luke salbuespenik altxa beharko");
		}
	}
	*/
}
