(ns large-file-dictionary-attack.core-test
  (:require [clojure.test :refer :all]
            [large-file-dictionary-attack.core :refer :all]))

(defn generate-password [length]
  "Generates random character password with given length"
  (let [chars (map char (range 33 127))
        password (take length (repeatedly #(rand-nth chars)))]
    (reduce str password)))

(defn generate-line [sequence-number]
  "Generates a line consisting of sequence number and random password with length between 1 and 11 characters"
  (let [password (generate-password (+ (rand-int 10) 1))]
    (format "%010d%s\n" sequence-number password)))

(defn generate-dictionary [number-of-passwords output-file-name]
  "Generates a dictionary with given number of lines"
  (with-open [writer (clojure.java.io/writer output-file-name)]
    (dorun (map #(.write writer (generate-line %)) (range number-of-passwords)))))

(defn delete-dictionary [dictionary-file-name]
  "Deletes dictionary file"
  (when (.exists (clojure.java.io/file dictionary-file-name)) (clojure.java.io/delete-file dictionary-file-name)))

(defn generate-dictionary-if-doesnt-exist [number-of-passwords dictionary-file-name]
  "Generates dictionary only if it does not exist"
  (when (not (.exists (clojure.java.io/file dictionary-file-name))) (generate-dictionary number-of-passwords dictionary-file-name)))

(defn create-predicate-matching-after-number-of-failed-tests [number-of-failed-tests]
  "Creates a predicate which return:
    - false until 'number-of-failed-tests' is reached
    - true otherwise"
  (let [counter (atom -1)
   next-value (fn [] (swap! counter inc))]
    (fn [ignore] (> (next-value) number-of-failed-tests))))

; Integration test
; creates a large 2G dictionary - takes time
; runs dictionary attack using large dictionary
(deftest test-with-2G-dictionary
  (testing "test with large dictionary"
    (let [dictionary-file-name "dictionary-2G.txt"
          number-of-lines 125000000
          match-after (- number-of-lines 1000)
          try-fn (create-predicate-matching-after-number-of-failed-tests match-after)]
      (do
        (println (str "2G DICTIONARY - MATCH"))
        (generate-dictionary-if-doesnt-exist number-of-lines dictionary-file-name)
        (is (= (not (nil? (time (dictionary-attack try-fn dictionary-file-name)))) true))))))

; Functional test - small dictionary
; creates a small dictionary
; runs dictionary attack using small dictionary
; finally match is found
(deftest test-with-small-dictionary
  (testing "test with small dictionary - match found"
    (let [dictionary-file-name "dictionary-1.txt"
          number-of-lines 10000
          match-after 9995
          try-fn (create-predicate-matching-after-number-of-failed-tests match-after)]
      (do
        (println (str "DICTIONARY 1 - MATCH"))
        (delete-dictionary dictionary-file-name)
        (generate-dictionary number-of-lines dictionary-file-name)
        (is (= (not (nil? (time (dictionary-attack try-fn dictionary-file-name)))) true))))))

; Runs test on small dictionary
; no match is found
(deftest test-with-small-dictionary-without-match
  (testing "test with small dictionary - no match found"
    (let [dictionary-file-name "dictionary-2.txt"
          number-of-lines 10000
          match-after 10010
          try-fn (create-predicate-matching-after-number-of-failed-tests match-after)]
      (do
        (println (str "DICTIONARY 2 - NO MATCH"))
        (delete-dictionary dictionary-file-name)
        (generate-dictionary number-of-lines dictionary-file-name)
        (is (= (nil? (time (dictionary-attack try-fn dictionary-file-name))) true))))))

(defn test-ns-hook []
  (do (test-with-small-dictionary)
    (test-with-small-dictionary-without-match)
    ; uncomment to run integration test
    ;(test-with-2G-dictionary)
      ))
