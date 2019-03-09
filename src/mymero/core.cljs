(ns ^:figwheel-hooks mymero.core
  (:require
   [goog.dom :as gdom]
   [reagent.core :as reagent :refer [atom]]
   [mymero.dict :as d]
   [mymero.dealer :as dealer]))

(enable-console-print!)

; Calculate initial number of pairs based on the available screen size.
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
            :unmatched true
            :selected false))

;; A "deck" is a map[number map] where each entry is a card-entry.
;; The number is the ID of the item and the map is the card.
;; number -> vec[vec[string string]] -> vec[map[number card]]
(defn create-deck [npairs word-article-pairs]
  (->> (map create-card word-article-pairs)
       (dealer/deal-cards npairs)
       (map-indexed hash-map)))

(defonce app-state
  (let [initial-theme (first (keys d/dict))
        initial-n (initial-npairs)]
       (atom {:theme initial-theme
              :n     initial-n
              :deck (create-deck initial-n (get d/dict initial-theme))})))


;; game logic

(defn hidden? [card]
  (and (:unmatched card) (not (:selected card))))

(defn match? [card1 card2]
  (= (:article card1) (:article card2)))

(defn check-for-match? [state]
  (let [entries (vals (:deck state))]
       (>= (count (filter #(:selected %) entries)) 2)))


;; actions

(defn select [id state]
  (if (and (get-in state [:deck id :unmatched])
           (not (check-for-match? state)))
      (assoc-in state [:deck id :selected] true)
      state))

(defn select! [id]
  (swap! app-state #(select id %)))





(defn card-component [card-entry]
 (let [id (key card-entry)
       card (val card-entry)
       {:keys [word article unmatched selected]} card]
      ^{:key id}[:button.card {:class (str (when (not (hidden? card)) article)
                                           (when unmatched " unmatched")
                                           (when selected " selected"))
                               :onClick #(select! id)}
                             (if (hidden? card) id word)]))

(defn mymero []
  [:div.game
    [:div.container
      [:h1 (:theme @app-state)]
      [:div.cards
         [card-component (first {91 {:word "Drucker" :article "der" :unmatchhed false :selected false}})]
         [card-component (first {92 {:word "Schlange" :article "die" :unmatched false :selected false}})]
         [card-component (first {93 {:word "Mädchen" :article "das" :unmatched false :selected false}})]
         [card-component (first {94 {:word "Drucker" :article "der" :unmatched false :selected true}})]
         [card-component (first {95 {:word "Schlange" :article "die" :unmatched true :selected true}})]
         [card-component (first {96 {:word "Maedchen" :article "das" :unmatched true :selected false}})]
         (map card-component (:deck @app-state))]
      [:h3 (str (:deck @app-state))]]])

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
