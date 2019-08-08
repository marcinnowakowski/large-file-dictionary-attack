# large-file-dictionary-attack

A clojure library providing example implementation of dictionary attack.

## Technical notes

Current version is simplest possible single thread implementation using lazy sequence. Two aspects of solution are important:
1. Thanks to use of lazy sequence small amount of memory is used. Whole possibly large list of passwords is not loaded to memory at once. 
1. For reading a dictionary file java.io.BufferedReader is used. Default implementation of java.io.BufferedReader sets buffer size to 8k which is optimal for disk io operations.

## Possible improvements

1. Using java.nio package for faster reading of dictionary file. 
1. Implementing parallel execution of predicate verification. Useful if predicate verification takes more time then io operations.
1. Use compression for dictionary file if io operations are performance bottle neck. In this approach compressed dictionary file is stored on disk and during it is read and decompressed.

## Test results

```
\> lein test

lein test large-file-dictionary-attack.core-test
DICTIONARY 1 - MATCH
"Elapsed time: 5.955487 msecs"
DICTIONARY 2 - NO MATCH
"Elapsed time: 2.578922 msecs"
2G DICTIONARY - MATCH
"Elapsed time: 27345.368956 msecs"

Ran 3 tests containing 3 assertions.
0 failures, 0 errors.
```

## License

Copyright © 2019 Marcin Nowakowski

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
