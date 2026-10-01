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
			Registered r = sut.manageMoney(email, 2.23, MovementType.SELL);
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
			sut.manageMoney(email, 30.0, MovementType.WITHDRAW);
			sut.close();
			fail("Ez litzateke honera iritsi beharko");
		}catch(NotEnoughMoneyException ex) {
			ex.printStackTrace();
			assertTrue(true);
		}catch(Exception e){
			fail("Ez litzateke honera iritsi beharko");
		}/*finally {
			assertEquals(balance, r.getBalance());
		}*/
	} 

	@Test
	public void test3() {
		try {
			double amount = 10;
			sut.open();
			Registered r = sut.manageMoney(email, amount, MovementType.WITHDRAW);
			sut.close();
			assertNotNull(r);

			//Expected, obtained, error
			assertEquals(balance, r.getBalance()+ amount, 0.000000001);
		}catch(Exception ex) {
			ex.printStackTrace();
			fail("Ez luke salbuespenik altsatu beharko");
		}
	}
	@Test
	public void tes4() {
		try {
			double amount = 10;
			sut.open();
			Registered r = sut.manageMoney(email, amount, MovementType.DEPOSIT);
			sut.close();
			assertNotNull(r);

			//Expected, obtained, error
			assertEquals(balance, r.getBalance() - amount, 0.000000001);
		}catch(Exception ex) {
			ex.printStackTrace();
			fail("Ez luke salbuespenik altsatu beharko");
		}
	}
}
