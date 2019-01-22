(ns ^:figwheel-hooks mymero.core
  (:require
   [goog.dom :as gdom]
   [reagent.core :as reagent :refer [atom]]
   [mymero.dict :as d]))

(enable-console-print!)

;; calculate initial number of pairs based on the available screen size
(defn initial-npairs []
  6)

(defn process-word [[word article]]
  (hash-map :word word
            :article article
            :unmatched true
            :selected false))

(defn load-words [words]
   (apply merge (map-indexed #(hash-map %1 (process-word %2)) words)))



(defonce app-state (atom {:theme (first (keys d/dict))
                          :words (load-words (val (first d/dict)))}))

(defn get-app-element []
  (gdom/getElement "app"))

;; game logic

(defn hidden? [content]
  (and (:unmatched content) (not (:selected content))))

(defn match? [content1 content2]
  (= (:article content1) (:article content2)))


;; actions

(defn select [id state]
  (if (get-in state [:words id :unmatched])
      (assoc-in state [:words id :selected] true)
      state))

(defn select! [id]
  (swap! app-state #(select id %)))





(defn card [card-entry]
 (let [id (key card-entry)
       content (val card-entry)
       {:keys [word article unmatched selected]} content]
      ^{:key id}[:button.card {:class (str (when (not (hidden? content)) article)
                                           (when unmatched " unmatched")
                                           (when selected " selected"))
                               :onClick #(select! id)}
                             (if (hidden? content) id word)]))

(defn mymero []
  [:div.game
    [:div.container
      [:h1 (:theme @app-state)]
      [:div.cards
         [card (first {91 {:word "Drucker" :article "der" :unmatchhed false :selected false}})]
         [card (first {92 {:word "Schlange" :article "die" :unmatched false :selected false}})]
         [card (first {93 {:word "Mädchen" :article "das" :unmatched false :selected false}})]
         [card (first {94 {:word "Drucker" :article "der" :unmatched false :selected true}})]
         [card (first {95 {:word "Schlange" :article "die" :unmatched true :selected true}})]
         [card (first {96 {:word "Maedchen" :article "das" :unmatched true :selected false}})]
         (map card (:words @app-state))
         [:h3 (.-clientWidth (.-documentElement js/document))]
         [:h3 (.-clientHeight (.-documentElement js/document))]]]])

(defn mount [el]
  (reagent/render-component [mymero] el))

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
