### Written Answers: 5/6

- testBaseCase failed for the same reason that testThrowsIllegalArgumentException
did, but for no other reason.  The fix identified for testBaseCase belongs as a
fix for testInductiveCase instead.

### Code Quality: 2/3

### Mechanics: 3/3

### General Feedback

- Good work overall, remember to remove non-needed code, exceptions, etc. and also consider if something is overengineered for a task.

### Specific Feedback

- Make sure to remove useless comments, like TODOs and commented out code, to
make your code as clear as possible.

- When selecting a greeting in `RandomHello`, the best style would use the length
of the array to specify the maximum value for the random integer generation:
```
String nextGreeting = greetings[rand.nextInt(greetings.length)];
```
Notice how this benefits us later on if we wanted to change the number of
possible greetings in the array.

- Missing documentation for the new fields in `BallContainer.java` and/or `Box.java`.
Make sure to document new additions in the future!

- Your BallContainer add/remove methods are more complicated than they need to be.
Look at the documentation for `Set.add` and `Set.remove` and see whether you
need to explicitly handle cases the cases of adding something that already
exists in the set and removing something that doesn't exist in the set.

- Your getBallsFromSmallest() method is quite overkill and serves as mostly just a wrapper class. You could just return the contents of the constructor of that class in the original method and be fine. In general you should always try to find libraries that do your work for you, especially for well-known algorithms such as those that sort data.  In this case, `Collections.sort` or `TreeSet` given a user-defined `Comparator` will do as well as you can possibly do.
