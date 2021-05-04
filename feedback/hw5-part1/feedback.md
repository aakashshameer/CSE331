### Written Answers: 19/20

#### IntQueue1 AF/RI:
- Your RI is not properly worded. `entries != null` means that the `List entries` itself is not null. Whereas your second statement states that every _entry_ in the `entries` list must not be null. These are not equivalent statements, but they are both part of the Representation Invariant.

- There is no `queue` did you mean `entries`?

#### IntQueue2 AF/RI:
- Wrong abstraction function (-1)

#### IntQueue2 Snapshots:
- Well done!

#### Representation Exposure:
- Well done!

### Design: 2/3
- Your method `pointingTo` can be rephrased as `getChildren` or something similar to make it clear what the relationship between the node passed in is.

### Documentation & Specification (including JavaDoc): 2/3
- Read up on what the tag `@spec.modifies` is supposed to indicate. The way that you are using it is not exactly right since you claim that it modifies `this.*` but it's not actually clear how those are being modified.

### Testing (test suite quality & implementation): 3/3

### Code quality (code stubs/skeletons only, nothing else): 2/3

### Mechanics: 3/3

#### Overall Feedback

Good work! Take a look on the comments below and improve on them! :)

#### More Details
- When we use `<>` notation on a class, the characters we put in between the `<>` are interpreted as a type parameter. In your Graph, since you declared your graph as having `DirectedGraph<String, String1>` this means that `String` and `String1` is not referring to the built in Java String class but rather is a parameter that will be overridden by the client when they instantiate the class. You should not use generics at this point in time. You can simply fix this by removing the `<String, String1>` from the class header.

- Additionally, you declared `String1` as a generic type but there is no mention on where you have used `String1` in your class.

