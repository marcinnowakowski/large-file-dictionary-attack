(ns large-file-dictionary-attack.core)

(defn dictionary-attack-line-seq [try-fn dictionary-file-name]
  "Perform dictionary attack on try-fn predicate using buffered reader and lazy sequence"
  (with-open [rdr (clojure.java.io/reader dictionary-file-name)]
      (first (filter try-fn (line-seq rdr)))))

(defn dictionary-attack-java-nio-lines [try-fn dictionary-file-name]
  "Perform dictionary attack on try-fn predicate using java.nio package (java.nio.file.Files/lines)"
  (let [java-predicate-from-try-fn (reify java.util.function.Predicate (test [this input] (try-fn input)))
        path (java.nio.file.Paths/get dictionary-file-name (into-array String []))
        stream-of-lines (java.nio.file.Files/lines path)]
    (let [optionalMatch (.findFirst (.filter stream-of-lines java-predicate-from-try-fn))]
      (if (.isPresent optionalMatch)
        (.get optionalMatch)
        nil))))

(def dictionary-attack dictionary-attack-java-nio-lines)