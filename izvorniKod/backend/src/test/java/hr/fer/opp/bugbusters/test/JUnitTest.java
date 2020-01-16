package hr.fer.opp.bugbusters.test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hr.fer.opp.bugbusters.control.Util;
import hr.fer.opp.bugbusters.control.Util.TransakcijaCollection;
import hr.fer.opp.bugbusters.dao.model.Racun;

class JUnitTest {
	
	Racun from;
	Racun to;
	BigDecimal amount;
	
	@BeforeEach
	void setup() {
		from = new Racun("1", "01234567891", new Date(System.currentTimeMillis()), new BigDecimal("200.25"), 1, new BigDecimal("2000"), null, null);
		to = new Racun("2", "01234567891", new Date(System.currentTimeMillis()), new BigDecimal("200.25"), 1, new BigDecimal("2000"), null, null);
		amount = new BigDecimal("150.75");
	}

	@Test
	void test1() {
		assertEquals(1, Util.getRandomString(1).length());
	}
	
	@Test
	void test2() {
		assertEquals(100, Util.getRandomString(100).length());
	}
	
	@Test
	void test3() {
		assertThrows(IllegalArgumentException.class, () -> Util.getRandomString(0));
	}
	
	@Test
	void test4() {
		assertThrows(IllegalArgumentException.class, () -> Util.getRandomString(-10));
	}
	
	@Test
	void test5() {
		assertThrows(NullPointerException.class, () -> Util.doTransaction(null, to, amount));
	}
	
	@Test
	void test6() {
		assertThrows(NullPointerException.class, () -> Util.doTransaction(from, null, amount));
	}
	
	@Test
	void test7() {
		assertThrows(NullPointerException.class, () -> Util.doTransaction(from, to, null));
	}
	
	@Test
	void test8() {
		assertThrows(IllegalArgumentException.class, () -> Util.doTransaction(from, to, new BigDecimal("2500")));
	}
	
	@Test
	void test9() {
		assertThrows(IllegalArgumentException.class, () -> Util.doTransaction(from, to, new BigDecimal("0")));
	}
	
	@Test
	void test10() {
		assertThrows(IllegalArgumentException.class, () -> Util.doTransaction(from, to, new BigDecimal("-1")));
	}
	
	@Test
	void test11() {
		TransakcijaCollection tc = Util.doTransaction(from, to, amount);
		assertEquals(new BigDecimal("49.50"), tc.from.getStanje());
		assertEquals(new BigDecimal("351.00"), tc.to.getStanje());
	}

}
