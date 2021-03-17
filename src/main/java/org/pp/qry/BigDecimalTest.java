package org.pp.qry;

import java.math.BigDecimal;
import java.util.Arrays;

public class BigDecimalTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
       BigDecimal bd1 = new BigDecimal("00000.000");
       BigDecimal bd2 = new BigDecimal("+123.548");
       
       System.out.println(bd1.multiply(bd2));
       String s = "কেমন আছেন";
       System.out.println(s.charAt(0));
       
       bd2 = new BigDecimal("5.639");
       System.out.println(bd2.ulp());
       
       float f = 5.639f;
       System.out.println(Math.nextUp(f));    
       byte[] arrs = s.getBytes();
       //arrs[arrs.length - 1] = (byte) (arrs[arrs.length - 1] + 1);       
      // System.out.println(new String(arrs));
       byte[] nArry = new byte[arrs.length + 1];       
       System.arraycopy(arrs, 0, nArry, 0, arrs.length);
       nArry[nArry.length - 1] = Byte.MIN_VALUE;
       System.out.println(new String(nArry));
       StringBuilder sbldr = new StringBuilder();
       sbldr.appendCodePoint(2478);
       System.out.println(sbldr.toString());
       
       f = 34.56f;
       int i = 10;
       if (i < f)
    	   System.out.println(i + f);
       
	}
	
	public static void calculate(Object l, Object r) {
		boolean eq = l.getClass().equals(r.getClass());
		if (!eq) {
			if (l instanceof Double || r instanceof Double) {
				double ld = (double) l;
				double rd = (double) r;
				
			}
		} else {
			
		}
		if (!eq && (l instanceof Double || r instanceof Double)) {
			
		}
	}
	

}
