package org.pp.qry;

public class QryTest {

	public static void main(String[] args) {
		
         String q = "a + b > 10 || c * (d + 3) < 6 && c > 5";
         QryProcessor qp = QryProcessor.compile(q,null);
         System.out.println(qp);
	}

}
