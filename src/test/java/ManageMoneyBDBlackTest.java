import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;

import domain.Registered;
import enums.MovementType;
import enums.QueryFilterType;
import exceptions.NotEnoughMoneyException;
import testOperations.TestDataAccess;

public class ManageMoneyBDBlackTest {

	//sut:system under test
	static DataAccess sut = new DataAccess();

	//additional operations needed to execute the test 
	static TestDataAccess testDA = new TestDataAccess();

	@SuppressWarnings("unused")
	private  Registered reg; 
	private String email,username,pass;
	private double balance;


	@Before
	public  void initTest(){
		email = "user1@gmail.com";
		username = "User1";
		pass = "123";
		balance = 20;

		testDA.open();
		reg = testDA.createRegistered(email, username, pass);
		testDA.setBalance(email, balance);
		testDA.close();

	}
	@After
	public  void finishTest(){
		testDA.open();
		testDA.removeRegistered(email);
		testDA.close();
	}	

	@Test
	public void test1() {
		try {
			sut.open();
			sut.manageMoney(email, 30.0, MovementType.WITHDRAW);
			sut.close();
			fail("NotEnoughMoneyException altxatu beharko litzateke");

		}catch(Exception ex) {
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
	} 
	
	@Test
	public void test5() {
	    try {
	        sut.open();
	        sut.manageMoney(null, 30.0, MovementType.DEPOSIT);
	        fail("IllegalArgumentException bota beharko luke");
	    }catch(NotEnoughMoneyException ex) {
	        fail("IllegalArgumentException bota beharko luke");
	    }
	    catch (IllegalArgumentException e) {
	        assertTrue(true);
	    } finally {
	        sut.close();
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
