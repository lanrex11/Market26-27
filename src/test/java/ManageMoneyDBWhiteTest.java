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
import domain.Sale;
import domain.Registered;
import exceptions.MustBeLaterThanTodayException;
import exceptions.ParamNullException;
import exceptions.SaleAlreadyExistException;
import testOperations.TestDataAccess;

public class ManageMoneyDBWhiteTest {

	//sut:system under test
	static DataAccess sut = new DataAccess();

	//additional operations needed to execute the test 
	static TestDataAccess testDA = new TestDataAccess();

	@SuppressWarnings("unused")
	private  Registered seller; 
	private String email;


	@Before
	public  void initTest(){
		//TODO open eta close?
		email = "user1@gmail.com";
		seller = new Registered(email, "User1", "123");
		testDA.open();
		testDA.createRegistered(email, "User1", email)
		testDA.setBalance(email, 20);

	}
	@After
	public  void finishTest(){
		//TODO open eta close?
		testDA.removeRegistered(email);

	}	

	@Test
	public void test1() {

	} 

}
