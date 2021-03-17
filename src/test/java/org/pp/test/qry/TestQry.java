package org.pp.test.qry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.pp.qry.Query;


public class TestQry {
	// list of map to filter
    private static List<Map<String, Object>> list = null;
    
	@BeforeClass // setup test
	public static void setUp() { 
		list = new ArrayList<Map<String, Object>>();
		list.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 5.73f, 10, "46 eastwood rd"));
		list.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 10.30f, 15, "46 eastwood rd"));
		list.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 15.54f, 20, "46 eastwood rd"));
		
		list.add(getMap("Maumita Pan", "pan.maumita@gmail.com", 83995981, 5.73f, 10, "46 eastwood rd"));
		list.add(getMap("Maumita Pan", "pan.maumita@gmail.com", 83995981, 10.30f, 15, "46 eastwood rd"));
		list.add(getMap("Maumita Pan", "pan.maumita@gmail.com", 83995981, 15.54f, 20, "46 eastwood rd"));
		
		list.add(getMap("Moana Pan", "pan.moana@gmail.com", 83995982, 5.73f, 10, "46 eastwood rd"));
		list.add(getMap("Moana Pan", "pan.moana@gmail.com", 83995982, 10.30f, 15, "46 eastwood rd"));
		list.add(getMap("Moana Pan", "pan.moana@gmail.com", 83995982, 15.54f, 20, "46 eastwood rd"));
	}
	// get a Map
	private static Map<String, Object> getMap(Object... vals) {
		Map<String, Object> m = new HashMap<>();
		m.put("name", vals[0]);
		m.put("email", vals[1]);
		m.put("mobile", vals[2]);
		m.put("price", vals[3]);
		m.put("units", vals[4]);
		m.put("address", vals[5]);
		return m;
	}
	
	@Test
	public void test1() {
		Query qry = new Query("email = 'pan.prasanta@gmail.com' && units > ?");
		List<Map<String, Object>> fList = qry.setParam(10).compile().filter(list);
		print(fList);
	}
	
	@Test
	public void test2() {
		Query qry = new Query("email = 'pan.prasanta@gmail.com' && (price > ? && units < 20)");
		List<Map<String, Object>> fList = qry.setParam(10).compile().filter(list);
		print(fList);
	}
	
	@Test
	public void test3() {
		Query qry = new Query("email = 'pan.prasanta@gmail.com' && units nbet (10,15)");
		List<Map<String, Object>> fList = qry.setParam(10).compile().filter(list);
		print(fList);
	}
	
	@Test
	public void test4() {
		Query qry = new Query("email != ? && (price > ? || units < 20) && email = ?");
		List<Map<String, Object>> fList = qry.setParam("pan.prasanta@gmail.com")
				   .setParam(10)
				   .setParam("pan.moana@gmail.com")
				   .compile()
				   .filter(list);
		print(fList);
	}
	
	@Test
	public void test5() {
		Query qry = new Query("email between (?,?) || units nin (10,15,?)");
		List<Map<String, Object>> fList = qry.setParam("pan.maumita@gmail.com")
				   .setParam("pan.moana@gmail.com")
				   .setParam(50)
				   .compile()
				   .filter(list);
		print(fList);
	}
	
	@Test
	public void test6() {
		Query qry = new Query("email <= 'pan.moana@gmail.com' && units in (10,15,50)");
		List<Map<String, Object>> fList = qry.setParam("pan.maumita@gmail.com")
				                             .compile()
				                             .filter(list);
		print(fList);
	}
	
	// print list map
	private static void print(List<Map<String, Object>> fList) {
		StringBuilder sbldr = new StringBuilder();
		for (Map<String, Object> m : fList) {
			for (Object o : m.values())
				sbldr.append(o.toString() + ",");

			sbldr.append("\n");
		}
		System.out.println(sbldr);
	}
}
