(ns ^:figwheel-hooks mymero.core
  (:require
   [goog.dom :as gdom]
   [reagent.core :as reagent :refer [atom]]
   [mymero.dict :as d]
   [mymero.dealer :as dealer]))

(enable-console-print!)

;; Calculate initial number of pairs based on the available screen size.
;; The width and the height must be the same as the size of the card defined in
;; CSS.
(defn initial-npairs []
 (let [avail-width (.-clientWidth (.-documentElement js/document))
       avail-height (.-clientHeight (.-documentElement js/document))
       max-width 800
       width 200
       height 30]
      (-> (min max-width avail-width)
          (quot width)
          (* (quot (- avail-height 150) height))
          (quot 2))))

;; vec[string string] -> card
(defn create-card [[word article]]
  (hash-map :word word
            :article article
            :matched false
            :revealed false))

;; A "deck" is a map[number map] where each entry is a card-entry.
;; The number is the ID of the item and the map is the card.
;; number -> vec[vec[string string]] -> vec[map[number card]]
(defn create-deck [npairs word-article-pairs]
  (->> (map create-card word-article-pairs)
       (dealer/deal-cards npairs)
       (zipmap (range))))

(defonce app-state
  (let [initial-theme (first (keys d/dict))
        initial-n (initial-npairs)]
       (atom {:theme initial-theme
              :n     initial-n
              :deck (create-deck initial-n (get d/dict initial-theme))})))


;; game logic

(defn hidden? [card]
  (and (not (:matched card)) (not (:revealed card))))

(defn match? [cards]
  (apply = (map #(:article %) cards)))

;; actions

(defn revealed-cards [state]
  (filter #(:revealed %) (vals (:deck state))))

(defn update-values [m f & args]
  (reduce (fn [r [k v]] (assoc r k (apply f v args))) {} m))

(defn hide [card]
  (assoc-in card [:revealed] false))

(defn hide-all [state]
  (update-in state [:deck] #(update-values % hide)))

(defn match-revealed [card]
  (if (:revealed card)
      (assoc-in card [:matched] true)
      card))

(defn check-for-match [state]
  (if (< (count (revealed-cards state)) 2)
      state
      (let [revealed-cards (revealed-cards state)]
           (if (match? revealed-cards)
               (-> (update-in state [:deck] #(update-values % match-revealed))
                   (hide-all))
               (hide-all state)))))

(defn select [id state]
  (let [card (get-in state [:deck id])]
    (if (:matched card)
        state
        (-> (assoc-in state [:deck id :revealed] true)
            (check-for-match)))))

(defn select! [id]
  (swap! app-state #(select id %)))





(defn card-component [card-entry]
 (let [id (key card-entry)
       card (val card-entry)
       {:keys [word article matched revealed]} card]
      ^{:key id}[:button.card {:class (str (when (not (hidden? card)) article)
                                           (when (not matched) " unmatched")
                                           (when revealed " selected"))
                               :onClick #(select! id)}
                             (if (hidden? card) "" word)]))

(defn mymero []
  [:div.game
    [:div.container
      [:h1 (:theme @app-state)]
      [:div.cards
         (map card-component (:deck @app-state))]]])

(defn- test-cards []
  (map card-component [(first {91 {:word "drucker" :article "der" :matchhed true :revealed false}})
                       (first {92 {:word "schlange" :article "die" :matched false :revealed false}})
                       (first {93 {:word "mädchen" :article "das" :matched true :revealed false}})
                       (first {94 {:word "drucker" :article "der" :matched true :revealed true}})
                       (first {95 {:word "schlange" :article "die" :matched false :revealed true}})
                       (first {96 {:word "maedchen" :article "das" :matched false :revealed false}})]))

(defn mount [el]
  (reagent/render-component [mymero] el))

(defn get-app-element []
  (gdom/getElement "app"))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element)

;; specify reload hook with ^;after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element))
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
