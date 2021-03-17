package org.pp.qry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Collection {
	 // Add map to collection
     public abstract void put(Map<String,Object> m);   
     // get filtered list
     public abstract List<Map<String,Object>> list(String qry, Object... params);	
     
     private static final Map<String,Collection> colMap = new HashMap<>();
     /** instantiate a collection */
	 public static Collection newCollection(String name, String pk, Object type) {
		 if (name == null || "".equals(name = name.trim()))
			throw new RuntimeException("Collection name can not be null or empty");
		 // check map 
		 Collection col = colMap.get(name);
		 if (col == null) {
			 col = new CollectionImp(name, pk, type);
			 colMap.put(name, col);
		 }
		 return col;
	 }
}
