(ns large-file-dictionary-attack.core)

(defn lazy-open [input-file-name]
  "Open file and read it using lazy sequence"
  (letfn [(read-one-line-and-append-to-sequence [reader]
    (lazy-seq
      (if-let [line (.readLine reader)]
        (cons line (read-one-line-and-append-to-sequence reader))
        (do (.close reader) nil))))]
  (let [reader (clojure.java.io/reader input-file-name)]
      {:reader reader
       :lines (read-one-line-and-append-to-sequence reader)})))

(defn dictionary-attack [try-fn dictionary-file-name]
  "Perform dictionary attack on try-fn predicate"
  (let [{reader :reader lines :lines} (lazy-open dictionary-file-name)
        match (first (filter try-fn lines))]
    (do
      ; if necessary close reader associated with lazy sequence
      (if-not (nil? match) (.close reader))
      match)))

