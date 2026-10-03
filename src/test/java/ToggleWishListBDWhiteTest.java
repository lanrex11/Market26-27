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
	private  Registered regist; 
	private String email;
	private Sale sale;
	private String username;
	private String pass;
	
	@Before
	public void initTest() {
		email = "aimar@gmail.com";
		username = "Aimar";
		pass = "123";
		testDA.open();
		regist = testDA.createRegistered(email, username, pass);
		sale = testDA.addSaleToRegistered(email, "Baloia", "Oso polita", 1, 20, new Date(), null);
		regist.addToWishList(sale);
	}
	
	@After
	public  void finishTest(){
		testDA.open();
		testDA.removeRegistered(email);
		testDA.close();
	}	
	
	@Test
	public void test1() {
		sut.open();
		
	}
}