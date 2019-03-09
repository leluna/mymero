(ns mymero.dealer)

(defn round-up [d]
  (int (Math/ceil d)))

(defn evenify [coll]
  (if (even? (count coll))
      coll
      (conj coll (rand-nth coll))))


(defn pair-taker [[np np-extra deposit results] pile]
  (let [supply       (apply conj pile deposit)
        np-to-take   (+ np (min np-extra 1))
        result       (vec (take (* 2 np-to-take) supply))
        np-taken     (quot (count result) 2)
        np-new       (- np (min np-taken np))
        np-extra-new (- np-extra (max 0 (- np-taken (- np np-new))))
        deposit-new  (vec (drop (count result) supply))]
       [np-new np-extra-new deposit-new (conj results result)]))


;; number -> number -> vec[coll] -> vec
(defn take-and-fill [np np-extra piles]
  (->> (sort-by (comp - count) piles)
       (reduce pair-taker
               [np np-extra [] []])
       (last)))

;; number -> vec[coll] -> vec[coll]
(defn take-pairs-balanced [npairs piles]
  (let [npiles    (count piles)
        np-rem    (rem npairs npiles)
        np-thres  (- npairs np-rem)]
       (->> (map evenify piles)
            (take-and-fill np-thres np-rem)
            (flatten)
            (vec))))

;; number -> vec[card] -> seq[card]
(defn deal-cards [npairs cards]
  (->> (group-by :article cards)
       (vals)
       (take-pairs-balanced npairs)
       (shuffle)))
