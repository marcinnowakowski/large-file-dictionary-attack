# large-file-dictionary-attack

A clojure library providing example implementation of dictionary attack.

## Technical notes

Current version (dictionary-attack-java-nio-lines) is a single thread implementation using java.nio.file.Files/lines method for io operations.

Previous version (dictionary-attack-line-seq) is a single thread implementation using BufferedReader and lazy sequences. This implementation is slower then the first one (dictionary-attack-java-nio-lines) - results in section "Test results".

## Possible improvements

1. Method java.nio.file.Files/readAllBytes could be used instead of java.nio.file.Files/lines. Based on this article: https://funnelgarden.com/java_read_file/#Performance_Summary.
1. try-fn predicate verification could be done in parallel. This improvement can bring benefits if try-fn requires take more time then io operations. It is applicable if multiple processor are available on the machine.
1. If io operations are performance bottle neck instead of plain text dictionary, compressed dictionary can be used. In this approach a compressed dictionary is stored on the disk. Dictionary is read and decompressed before performing computation.

## Test results

dictionary-attack-java-nio-lines
```
lein test large-file-dictionary-attack.core-test
DICTIONARY 1 - MATCH
"Elapsed time: 10.63876 msecs"
DICTIONARY 2 - NO MATCH
"Elapsed time: 2.439274 msecs"
2G DICTIONARY - MATCH
"Elapsed time: 15791.472212 msecs"

Ran 3 tests containing 3 assertions.
0 failures, 0 errors.
```

dictionary-attack-line-seq
```
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
