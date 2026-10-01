import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.random.*;
import java.util.Date;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;

import domain.Registered;

import testOperations.TestDataAccess;

public class ManageMoneyBDWhiteTest {

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
			Registered r = sut.manageMoney(email, 2.23, );
			sut.close();
			assertNotNull(r);
			
			//Expected, obtained, error
			assertEquals(balance, r.getBalance(),0.000000001);
		}catch(Exception ex) {
			ex.printStackTrace();
			fail("Ez luke salbuespenik altsatu beharko");
		}
	} 
	
	@Test
	public void test2() {
		try {
			sut.open();
			Registered r = sut.manageMoney(email, 22.2, null);
			sut.close();
			assertNotNull(r);
			
			//Expected, obtained, error
			assertEquals(balance, r.getBalance(),0.000000001);
		}catch(Exception ex) {
			ex.printStackTrace();
			fail("Ez luke salbuespenik altsatu beharko");
		}
	} 

}
