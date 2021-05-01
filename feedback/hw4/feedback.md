### Written Answers: 14/26
- for multiplication, you have to multiply each term by all other terms (and add all together). it is not enough to just multiply the terms that have the same degree
- for division, your invariant does not always hold. At the very start of the loop, r is equal to p, so p != q * r + k.
- you cannot use addition between a ratpoly and a ratterm without defining that operation (we have only defined poly by poly addition) 
- think about a case like 2 / 1. Your loop will run forever because the degrees are always going to be equal to each other.
- for 1.1, you *must* also change `checkRep` and `hashCode`.
- for 2.2, you *must* also change `checkRep`, `getExpt`, and `hashCode`.
- for 2.3, you *must* also change `checkRep`.
- for 1.2, it is also because the method specification does not include the `@spec.modifies` tag.
- Immutability is a property of the specification, and `checkRep` does not assume
the specification was correctly implemented.  So, in general, regardless of
whether or not they are immutable, ADTs need calls to `checkRep` at the
beginning and end of all public methods. We only dispose of this restriction (and only need them at the end of constructors) for classes that are guaranteed immutability by the compiler. This is only if all of the fields of a class are both `final` and immutable.

### Code Quality: 2/3

### Mechanics: 3/3

### General Feedback
Good job on the coding portion overall. You are making good use of the methods you write. My suggestion would be to look at some of the features of the representation that might make pieces easier to program (like your `degree()` method).

### Specific Feedback
- great work with RatTerm. You took advantage of methods well (for example using `negate()` and `add()` in your subtraction method)
- remember that your RatPoly list must be sorted. Thus, you don't need to loop through the entire list for your `degree()` method and can instead just check the first element.
- remember that you were supposed to include invariants for non-trivial loops in ratpoly
- you should declare types as interfaces wherever possible (e.g. `List` instead of `ArrayList`)
- rather than writing an entire duplicate method, you can take advantage of some of the other constructors for lists.
- you should include checkrep calls at the start and end of each method in RatPoly.
- your `getNthFromTop` can be made simpler with the `Stack.get()` method
- in RatPolyStack, you don't need to create an empty ratpoly each time that is just going to be reassigned. for you methods, don't create the `answers` poly until you do the actual operation on a and b

