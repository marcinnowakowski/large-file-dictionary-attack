(ns large-file-dictionary-attack.core)

(defn dictionary-attack [try-fn dictionary-file-name]
  "Perform dictionary attack on try-fn predicate"
  (with-open [rdr (clojure.java.io/reader dictionary-file-name)]
      (first (filter try-fn (line-seq rdr)))))

