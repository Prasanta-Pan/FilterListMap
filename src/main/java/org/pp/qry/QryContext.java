package org.pp.qry;

import java.util.Map;

interface QryContext {
   /**
    *  Is the field name is PK    
    */
   boolean isPk(String fName);
  /**
    * Seek to a particular record when PK is provided
    */
   void seek(Object pkVal);
   /**
    * Return the current loaded fields
    */
   Map<String,Object> currentMap();
   /**
    * Check if next record exist
    */
   boolean hasNextMap();
   /**
    *  get a higher value  
    */   
   Object higherValue(Object val);
   /**
    *  We are done with iterating
    */
   void quite();
}
