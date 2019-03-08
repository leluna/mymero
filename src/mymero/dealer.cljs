(ns mymero.dealer)

(defn round-up [d]
  (int (Math/ceil d)))

(defn evenify [coll]
  (if (even? (count coll))
      coll
      (conj coll (rand-nth coll))))


(defn taker [[n n-extra deposit results] pile]
  (let [supply      (apply conj pile deposit)
        result      (vec (take (+ n (min n-extra 1)) supply))
        n-new       (- n (min (count supply) n))
        n-extra-new (- n-extra (max 0 (- (count result) n)))
        deposit-new (vec (drop (count result) supply))]
       [n-new n-extra-new deposit-new (conj results result)]))


;; number -> vec[coll] -> vec[coll]
(defn take-and-fill [n n-extra piles]
  (->> (sort-by (comp - count) piles)
       (reduce taker
               [n n-extra [] []])
       (last)))

;; number -> vec[coll] -> vec[coll]
(defn take-pairs-balanced [npairs piles]
  (let [npiles    (count piles)
        np-rem    (rem npairs npiles)
        np-thres  (- npairs np-rem)]
       (->> (map evenify piles)
            (take-and-fill (* 2 np-thres) (* 2 np-rem))
            (flatten)
            (vec))))

;; number -> vec[card] -> list[card]
(defn deal-cards [npairs cards]
  (->> (group-by :article cards)
       (vals)
       (take-pairs-balanced npairs)
       (shuffle)))
