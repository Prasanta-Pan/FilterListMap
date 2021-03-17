# FilterListMap
If you are looking for a simple way to filter a list of maps using a simple sql like query language 
than this library could be a great help for you. This is a simple java library built using maven.

## How to use it
Have a Look at the below JUnit sample code for your reference. First create a query as shown below.
Provide necessary parameters if required followed by compile and than filter. 

``` java
import org.pp.qry.Query;

@Test
public void test1() {
    // Create a query 
		Query qry = new Query("email = 'pan.prasanta@gmail.com' && units > ?");
    // now compile the query with required parameters followed by filter
		List<Map<String, Object>> fList = qry.setParam(10).compile().filter(list);
    // Print the filtered list
		print(fList);
}
``` 
below is the setup to prepare the list of maps to be filtered for the above code sample

``` java
import org.pp.qry.Query;

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
``` 
...and here is the sample output...

``` java
46 eastwood rd,10.3,Prasanta Pan,84984827,15,pan.prasanta@gmail.com,
46 eastwood rd,15.54,Prasanta Pan,84984827,20,pan.prasanta@gmail.com,

```
Let's see one more example to make things more clearer

``` java
	@Test
	public void test4() {
		Query qry = new Query("email != ? && (price > ? || units < 20) && email = ?");
		List<Map<String, Object>> fList = 
        qry.setParam("pan.prasanta@gmail.com")
				   .setParam(10)
				   .setParam("pan.moana@gmail.com")
				   .compile()
				   .filter(list);
		print(fList);
	}
```
To get more familiar with the API usage please have a look into the JUnit test cases provided in package.

## List of operators supported by the query processor
All common Arithmetic operators eg. + , -, *, /, %. Sample usage below.

``` java
// Using arithmatic operator along with relational operator. THis is valid
String qry = "unit > 5 + ? || price < 7 + (5 * 10)"
// Invalid expression. Expression must be evaluated to boolean condition
String qry = "unit + price / ?"

```
All common conditional operators e.g. >, <, >= , <= . Sample usage below
``` java
// Expression with conditional operators
String qry = "unit > 5  || price < 7"
// one more example
String qry = "email >= 'pan.prasanta@gmail.com' && (price <= 10 || unit >= 23)"

```
some sql operators are also supported e.g between, in, not between, not in.
Sort form of not in/between operators are also supported e.g. nbet, nin.
See Below usage example.

``` java
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
```
## Any issue ?
Please reach out to me pan.prasanta@gmail.com

