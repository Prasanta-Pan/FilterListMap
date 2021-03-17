package org.pp.qry;

import static java.lang.Math.nextUp;
import static org.pp.qry.QryEngine.getFilteredList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

class CollectionImp extends Collection {
	// Key field
	private Key key = null;
	private String pk = null;
	// Collection counter
	private static int cc = 0;
	// key value pair
    private static final NavigableMap<Key, Map<String, Object>> data = new TreeMap<>();    
    
    private CollectionImp() {}
    // Main constructor
    CollectionImp(String colname, String pk, Object type) { 
    	this(); 
    	if (pk == null || "".equals(pk = pk.trim()))
			throw new RuntimeException("PK can not be null or empty");
    	// Validate pk type
    	this.pk = pk;
    	validateType(type, pk);    	
    	this.key = new Key(cc++, type, ++Key.slCntr);
    }
    // Validate field typeABCDEFGHJ1IIIIJKLMMMMMMMM3EWWWWWWWWWWWWWWWWWWWWWWWWADFSJK7T5DEW
    private static void validateType(Object type, String fName) {
    	if (type == null)
    		throw new NullPointerException("'" + fName + "' can not be null");
    	else if (!(type instanceof String) && !(type instanceof Boolean) && 
    			 !(type instanceof Integer) && !(type instanceof Long) && 
    			 !(type instanceof Float) && !(type instanceof Double))
    		throw new RuntimeException("'" + fName + "' "
    				+ "must be of type String/Boolean/Integer/Long/Float/Double");
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        CollectionImp col = (CollectionImp) newCollection("test", "email", "");
        col.put(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 5.73f, 10, "46 eastwood rd"));
        col.put(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 10.30f, 15, "46 eastwood rd"));
        col.put(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 15.54f, 20, "46 eastwood rd"));
		
        col.put(getMap("Maumita Pan", "pan.maumita@gmail.com", 83995981, 5.73f, 10, "46 eastwood rd"));
        col.put(getMap("Maumita Pan", "pan.maumita@gmail.com", 83995981, 10.30f, 15, "46 eastwood rd"));
        col.put(getMap("Maumita Pan", "pan.maumita@gmail.com", 83995981, 15.54f, 20, "46 eastwood rd"));
		
        col.put(getMap("Moana Pan", "pan.moana@gmail.com", 83995982, 5.73f, 10, "46 eastwood rd"));
        col.put(getMap("Moana Pan", "pan.moana@gmail.com", 83995982, 10.30f, 15, "46 eastwood rd"));
        col.put(getMap("Moana Pan", "pan.moana@gmail.com", 83995982, 15.54f, 20, "46 eastwood rd"));
        
        List<Map<String, Object>>  l = null;
		long t = System.nanoTime();
		String qry = "email = 'pan.maumita@gmail.com' && (price > ? || units < 20)";
		l = col.list(qry, 10);
		t = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - t);
        System.out.println("Time: " + t);
		print(qry, l);
				
		t = System.nanoTime();       
        qry = "price > ? && units < 20";
        l = col.list(qry, 10);
        t = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - t);
        System.out.println("Time: " + t);
        print(qry, l); 
        
        t = System.nanoTime();   
        qry = "email = 'pan.maumita@gmail.com' && units not in (10,15)";
        l = col.list(qry, 10);
        t = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - t);
        System.out.println("Time: " + t);
        print(qry, l); 
        
        t = System.nanoTime();   
        qry = "email < 'pan.moana@gmail.com' && units nbet (5,10)";
        l = col.list(qry, 10);
        t = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - t);
        System.out.println("Time: " + t);
        print(qry, l); 
        
        t = System.nanoTime();   
        qry = "units < 5 + ? * 3 && price < ?";
        l = col.list(qry, 10, 30);
        t = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - t);
        System.out.println("Time: " + t);
        print(qry, l); 
        
        t = System.nanoTime();   
        qry = "email between ('pan.maumita@gmail.com','pan.moana@gmail.com') "
        		+ "&& (price > ? && units < 20)";
        l = col.list(qry, 10);
        t = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - t);
        System.out.println("Time: " + t);
        print(qry, l); 
        
        t = System.nanoTime();   
        qry = "email < 'pan.moana@gmail.com' && (price > ? && units < 20)";
        l = col.list(qry, 10);
        t = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - t);
        System.out.println("Time: " + t);  
        print(qry, l); 
        
        t = System.nanoTime();   
        qry = "units > ? && units < ?";
        l = col.list(qry, 5, 20);
        t = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - t);
        System.out.println("Time: " + t);
        print(qry, l); 
        
        qry = "email between ('pan.maumita@gmail.com','pan.moana@gmail.com') && (price > ? || units < 20)";
		print(qry, col.list(qry, 10));
		
		qry = "email <= 'pan.moana@gmail.com' && units in (10,15,50)";
		print(qry, col.list(qry, 10));
		
		qry = "email between ('pan.maumita@gmail.com','pan.moana@gmail.com') && units nin (10,15,?)";
		print(qry, col.list(qry, 50));
        
	}
	
	private static Map<String,Object> getMap(Object...vals) {
		Map<String,Object> m = new HashMap<>();
		m.put("name", vals[0]);
		m.put("email", vals[1]);
		m.put("mobile", vals[2]);
		m.put("price", vals[3]);
		m.put("units", vals[4]);
		m.put("address", vals[5]);		
		return m;
	}
	
	private static void print(String qry, List<Map<String, Object>> fList) {
		StringBuilder sbldr = new StringBuilder("####### " + qry + "#######\n\n");
		for (Map<String, Object> m: fList) {
			for (Object o : m.values())
				sbldr.append(o.toString() + ",");
			
			sbldr.append("\n");
		}
		System.out.println(sbldr);
	}
	
	@Override
	public void put(Map<String, Object> map) {
		// Check if PK present
		Object pkVal = null;
		if ((pkVal = map.get(pk)) == null)
			throw new RuntimeException("Either PK field is not present in map or null");
		else if (!pkVal.getClass().equals(key.pkVal.getClass()))
			throw new RuntimeException("PK type mismatch");
		else {
			Key nKey = new Key(key.colCode,pkVal, ++Key.slCntr);
			data.put(nKey, map);
		}		
	}

	@Override
	public List<Map<String, Object>> list(String qry, Object... params) {
		// TODO Auto-generated method stub
		return getFilteredList(new QryContextImp(), qry, params);
	}
	/**
	 * Query Context Implementation
	 * @author prasantsmac
	 *
	 */
	private final class QryContextImp implements QryContext {
		// current map
        private Map<String,Object> curM = null;
        // Current iterator
        private Iterator<Map<String,Object>> itr = null;
                        
		@Override
		public boolean isPk(String fName) {
			// TODO Auto-generated method stub
			return pk.equals(fName);
		}

		@Override
		public void seek(Object pkVal) {
			Key k = new Key(key.colCode,pkVal,0);
			itr = data.tailMap(k).values().iterator();	
			// get first loaded recode		
			curM = itr.hasNext() ? itr.next() : null;			
		}

		@Override
		public Map<String, Object> currentMap() {
			// TODO Auto-generated method stub
			return curM;
		}

		@Override
		public boolean hasNextMap() {
			// TODO Auto-generated method stub
			boolean ok = false;
			if (itr != null && itr.hasNext()) {
				curM = itr.next();
				ok = true;
			}
			return ok;
		}

		@Override
		public Object higherValue(Object val) {
			// Some character range might not work
        	if (val instanceof String) {
        		char[] nchar = (((String) val)).toCharArray();
        		nchar[nchar.length - 1] = (char)(nchar[nchar.length - 1] + 1);
        		String newVal = new String(nchar);
        		return newVal;
        	} 
        	else if (val instanceof Integer) {
        		int v = (int) val;
        		return ++v;
        	}
        	else if (val instanceof Long) {
        		long v = (long) val;
        		return ++v;
        	}
        	else if (val instanceof Float) {
        		float v = (float) val;
        		return nextUp(v);
        	}
        	else if (val instanceof Double) {
        		double v = (double) val;
        		return nextUp(v);
        	} 
        	else 
        		throw new RuntimeException("Unsupported PK value type '" + val + "'");
		}

		@Override
		public void quite() {
			// TODO Auto-generated method stub
			itr = null;
		}	
		
	}
	
	// Key for the tree map
	private static final class Key implements Comparable<Key> {
		// Collection code
		private int colCode = -1;
		// Serial number
		private int slNo = -1;
		// Primary key field type
		private Object pkVal = null;
		// Serial number counter 
		private static int slCntr = -1;
		
		private Key (int colCode, Object pkVal, int slNo) {
			this.pkVal = pkVal;
			this.colCode = colCode;
			this.slNo = slNo;
		}
		
		@Override
		public int compareTo(Key o) {
			int status = compareInt(colCode, o.colCode);
			if (status != 0)
				return status;
			// if collection code equal
			if (pkVal == null)
				return -1;
			@SuppressWarnings("unchecked")
			Comparable<Object> l = (Comparable<Object>) pkVal;
			@SuppressWarnings("unchecked")
			Comparable<Object> r = (Comparable<Object>) o.pkVal;
			status = l.compareTo(r);
			if (status != 0)
				return status;
			status = compareInt(slNo, o.slNo);
			return status;
		}		
		private int compareInt(int x, int y) {
			return (x < y) ? -1 : ((x == y) ? 0 : 1);
		}
	}		
}
