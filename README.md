# Frostedflakes Java Serialization Library

Frostedflakes is a serialization library for Java with the following goals:
* lightweight footprint
* ability to fully serialize/deserialize regular Java objects
* experimental proxy and cglib object serialization

The endgame is to be able to snapshot any java runtime stack and reinflate it at a later time.

# Show me the code

```java
// Initialize FrostedFlakes
IFrostedFlakes<List<String>> ff = new FrostedFlakes<>();
// Create and populate a new list
List<String> list1 = new ArrayList<String>();
list1.add("Hello");
list1.add("world");
list1.add("!!!");

// byte array serialization
byte[] bytes = ff.toBytes(list1);
List<String> listFromBytes = ff.fromBytes(bytes);

// file serialization
ff.save(list1, "mylist.bin");
List<String> listFromFile = ff.open("mylist.bin");
// list1.equals(listFromFile)
```
