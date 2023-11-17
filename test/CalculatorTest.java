import static org.junit.Assert.*;

import org.junit.Test;

import com.bookstore.entity.Users;

public class CalculatorTest {

	@Test
	public void testAdd() {
		Calculator calculator= new Calculator();
		int a=2;
		int b=2;
		int rs = calculator.add(a,b);
		int ex = 4;
		assertEquals(rs,ex);
		

	}

}
