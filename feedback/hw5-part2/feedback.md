### Written Answers: 7/10
- Missing disadvantage to each of the possible graph implementations.

### Design: 3/3

### Documentation & Specification (including JavaDoc): 2/3

### Code quality (code and internal comments including RI/AF): 3/3

### Testing (test suite quality & implementation): 3/3

### Mechanics: 3/3

#### Overall Feedback

Great job this week! You did great making sure to call `checkRep` consistently throughout your
implementation. 

#### More Details

- Your class comments should be a bit more substantial outlining the data type that
you handle in your graph and some of the parameters surrounding the data that you
can store (ie no duplicates, String values for nodes and labels etc.)

- Specifying both `@spec.requires !A` and `@throws SampleException when A` is
contradictory, since, on the one hand, you are declaring the behavior of the
method under the condition `A` to be undefined, and, on the other, you are
declaring it to be defined (to throw an exception) under the condition `A`.

- Modifying the Set returned by keySet() will actually modify the underlying Map,
so just returning the keySet() will lead to rep exposure.

