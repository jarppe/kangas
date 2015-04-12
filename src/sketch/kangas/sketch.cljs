(ns kangas.sketch
  (:require [kangas.util :as u :refer [fn->]]))

(defn paint-1 [{:keys [ts]}]
  [:sketch
   [:style {:fill-color [20 110 50 20]
            :line-color [20 110 50 30]
            :line-width 20}
    [:begin-path
     [:arc 0 0 1000 0 360]
     [:close-path]
     [:fill-path]
     [:draw-path]]]])

(defn arc [r a]
  [:begin-path
   [:arc 0 0 r 0 a true]
   [:arc 0 0 (- r 100) a 0 false]
   [:close-path]
   [:fill-path]
   #_[:draw-path]])

(defn paint-2 [{:keys [ts]}]
  (let [[_ _ _ h m s] (u/timestamp->date ts)]
    [:sketch
     [:style {:fill-color   [100 70 30 20]
              :line-color   [100 70 30]
              :line-width   20
              :line-join    :miter
              :miter-limit  100}
      (arc 900 (u/scale {:from-max 60 :to-max 360} s))
      (arc 750 (u/scale {:from-max 60 :to-max 360} m))
      (arc 600 (u/scale {:from-max (* 12 60) :to-max 360} (+ (* 60 (mod h 12)) m)))
      [:style {:line-width     5
               :text-font      "200px sans-serif"
               :text-align     :center
               :text-baseline  :middle}
       [:fill-text 0 0 "%02d:%02d:%02d" h m s]
       [:draw-text 0 0 "%02d:%02d:%02d" h m s]]]]))

(defn start [w h]
  (let [scale (/ (min w h) 2100)
        init  {:x  0
               :y  0
               :w  w
               :h  h
               :translate  [(/ w 2) (/ h 2)]
               :scale      [scale scale]}]
    [(assoc init
            :paint paint-1
            :background [20 20 20])
     (assoc init
            :paint paint-2)]))

