(ns mymero.dealer-test
    (:require
     [cljs.test :refer-macros [deftest is testing]]
     [mymero.dealer :as dealer]))

(defn contains [e coll]
  (some #{e} coll))

(defn same-ignoring-order [coll1 coll2]
  (= (frequencies coll1) (frequencies coll2)))

(deftest evenify-test
  (is (= (dealer/evenify ["1" "1"]) ["1" "1"])
      (= (dealer/evenify ["1"]) ["1" "1"])))

(deftest pair-taker-test
  (is (= (dealer/pair-taker [1 0 [] []] ["1" "1" "1" "1"])
         [0 0 ["1" "1"] [["1" "1"]]]))
  (is (= (dealer/pair-taker [1 2 [] []] ["1" "1" "1" "1"])
         [0 1 [] [["1" "1" "1" "1"]]]))
  (is (= (dealer/pair-taker [2 2 [] []] ["1" "1"])
         [1 2 [] [["1" "1"]]]))
  (is (= (dealer/pair-taker [3 0 ["1" "1"] []] ["2" "2" "2" "2"])
         [0 0 [] [["2" "2" "2" "2" "1" "1"]]]))
  (is (= (dealer/pair-taker [1 1 ["1" "1"] [["2" "2" "1" "1"]]] ["3" "3"])
         [0 0 [] [["2" "2" "1" "1"] ["3" "3" "1" "1"]]])))


(defn count-fulfills [pred coll]
  (count (filter pred coll)))

(defn count-elem [e coll]
  (count-fulfills (partial = e) coll))

(defn has [n e coll]
  (= (count-elem e coll)
     n))

(deftest take-pairs-balanced-balanced-test
  (let [input [["1" "1" "1" "1"]
               ["2" "2" "2" "2"]
               ["3" "3" "3" "3"]]]
       (is (has 4 "1" (dealer/take-pairs-balanced 6 input)))
       (is (has 4 "2" (dealer/take-pairs-balanced 6 input)))
       (is (has 4 "3" (dealer/take-pairs-balanced 6 input)))))

(deftest take-pairs-balanced-unbalance-test
  (let [input [["1" "1" "1" "1"]
               ["2" "2" "2" "2"]
               ["3" "3" "3" "3"]]
        output (dealer/take-pairs-balanced 5 input)]
       (is (same-ignoring-order (map #(count-elem % output) ["1" "2" "3"])
                                [4 4 2]))))

(deftest take-pairs-balanced-insufficient-test
  (let [a ["a1"]
        b ["b1" "b2" "b3"]
        c ["c1" "c2" "c3" "c4" "c5" "c6" "c7"]
        input [a b c]
        output (dealer/take-pairs-balanced 8 input)]
       (is (>= (count-fulfills #(contains % a) output) 2))
       (is (>= (count-fulfills #(contains % b) output) 4))
       (is (>= (count-fulfills #(contains % c) output) 4))
       (is (= 14 (count output)))))
