# Frostedflakes Java Serialization Library

Frostedflakes is a serialization library for Java with the following goals:
* lightweight footprint
* ability to fully serialize/deserialize regular Java objects
* experimental proxy and cglib object serialization

The endgame is to be able to snapshot any java runtime stack and reinflate it at a later time.

# Show me the code

```
// Initialize FrostedFlakes
IFrostedFlakes<A> FLAKES = new FrostedFlakes<>();
// Create and populate a new list
List<String> list1 = new ArrayList<String>();
list1.add("Hello");
list1.add("world");
list1.add("!!!");
// Checking the equality
FLAKES.save(list1, "mylist.bin");
List<String> list2 = FLAKES.open("mylist.bin");
// list1.equals(list2)
```
