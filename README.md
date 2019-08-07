# large-file-dictionary-attack

A clojure library providing example implementation of dictionary attack.

## Test results

```
\> lein test

lein test large-file-dictionary-attack.core-test
DICTIONARY 1 - MATCH
"Elapsed time: 15.89258 msecs"
DICTIONARY 2 - NO MATCH
"Elapsed time: 13.039752 msecs"
2G DICTIONARY - MATCH
"Elapsed time: 165229.876646 msecs"

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
