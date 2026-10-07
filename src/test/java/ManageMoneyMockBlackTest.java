import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

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
import enums.MovementType;

import exceptions.NotEnoughMoneyException;


public class ManageMoneyMockBlackTest {

	static DataAccess sut;
	protected MockedStatic<Persistence> persistenceMock;
	@Mock
	protected EntityManagerFactory entityManagerFactory;
	@Mock
	protected EntityManager db;
	@Mock
	protected EntityTransaction et;

	@SuppressWarnings("unused")
	private  Registered reg; 
	private String email,username,pass;
	private double balance;


	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
		persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() ->
		Persistence.createEntityManagerFactory(Mockito.any())).thenReturn(entityManagerFactory);
		Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
		
		sut = new DataAccess(db);

		email = "user1@gmail.com";
		username = "User1";
		pass = "123";
		balance = 20;
		reg = new Registered(email, username, pass);
		reg.setBalance(balance);
		Mockito.when(db.find(Registered.class, email)).thenReturn(reg);
		Mockito.when(db.find(Registered.class, null)).thenThrow(new IllegalArgumentException());



	}
	@After
	public void tearDown() {
		persistenceMock.close();
	}

	@Test
	public void test1() {
		try {
			sut.open();
			sut.manageMoney(email, 30.0, MovementType.WITHDRAW);
			sut.close();
			fail("NotEnoughMoneyException altxatu beharko litzateke");

		}catch(NotEnoughMoneyException ex) {
			assertTrue(true);
		}
	} 

	@Test
	public void test2() {
		try {
			double expected = 10.0;
			sut.open();
			Registered r = sut.manageMoney(email, 10.0, MovementType.WITHDRAW);
			sut.close();
			assertEquals(expected, r.getBalance(),0.000001);
		}catch(Exception e){
			fail("Ez litzateke honera iritsi beharko");
		}
	} 

	@Test
	public void test3() {
		try {
			double expected = 50.0;
			sut.open();
			Registered r = sut.manageMoney(email, 30.0, MovementType.DEPOSIT);
			sut.close();
			assertEquals(expected, r.getBalance(),0.000001);
		}catch(Exception e){
			fail("Ez litzateke honera iritsi beharko");
		}
	} 
	/*
	@Test
	public void test4() {
		try {
			double expected = balance;
			sut.open();
			Registered r = sut.manageMoney(email, -30.0, MovementType.WITHDRAW);
			sut.close();
			assertEquals(expected,r.getBalance(),0.00001);
		}catch(Exception e){
			fail("Ez litzateke honera iritsi beharko");
		}
	}*/
	
	@Test
	public void test5() {
	    try {
	        sut.open();
	        sut.manageMoney(null, 30.0, MovementType.DEPOSIT);
	        sut.close();
	        fail("IllegalArgumentException bota beharko luke");
	    }catch(NotEnoughMoneyException ex) {
	        fail("IllegalArgumentException bota beharko luke");
	    }
	    catch (IllegalArgumentException e) {
	        assertTrue(true);
	    }
	}
	
	@Test
	public void test6() {
		try {
			sut.open();
			Registered r = sut.manageMoney("dbn-ezdago@gmail.com", 30.0, MovementType.DEPOSIT);
			sut.close();
			assertNull(r);
		}catch(Exception e){
			fail("Ez litzateke honera iritsi beharko");
		}
	} 
	
	@Test
	public void test7() {
		try {
			double expected = balance;
			sut.open();
			Registered r = sut.manageMoney(email, 30.0, null);
			sut.close();
			assertEquals(expected,r.getBalance(),0.00001);
		}catch(Exception e){
			fail("Ez litzateke honera iritsi beharko");
		}
	} 
}
