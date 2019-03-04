(ns mymero.core-test
    (:require
     [cljs.test :refer-macros [deftest is testing]]
     [mymero.core :as c]))

(deftest create-card-test
  (let [input ["word1" "der"]
        output (c/create-card input)]
       (is (= output
              {:word "word1" :article "der"
               :unmatched true :selected false}))))
