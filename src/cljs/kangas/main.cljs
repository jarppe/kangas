(ns kangas.main
  (:require [kangas.sketch :as s]
            [kangas.util :as u]
            [kangas.canvas :refer [render-sketch]]))

(defn timestamp []
  (.getTime (js/Date.)))

(def schedule (or (.-requestAnimationFrame js/window)
                  (.-mozRequestAnimationFrame js/window)
                  (.-webkitRequestAnimationFrame js/window)
                  (.-msRequestAnimationFrame js/window)
                  (do
                    (js/console.log "WARNING: requestAnimationFrame not available, using setTimeout")
                    (fn [f]
                      (js/window.setTimeout (fn [] (f (timestamp))) 16)))))

(defonce sketches (atom nil))

(defn safe-start [w h]
  (try
    (s/start w h)
    (catch :default e
      (js/console.log "error in start:" e)
      nil)))

(defn ->canvas [app {:keys [x y w h] :as sketch}]
  (let [canvas (js/document.createElement "canvas")
        ctx    (.getContext canvas "2d")]
    (doto (.-style canvas)
      (aset "top" x)
      (aset "left" y))
    (doto canvas
      (aset "width" w)
      (aset "height" h))
    (.appendChild app canvas)
    (assoc sketch
           :canvas  canvas
           :ctx     ctx)))

(defn remove-all-childs [e]
  (loop [c (.-firstChild e)]
    (when c
      (.removeChild e c)
      (recur (.-firstChild e)))))

(defn init-sketch []
  (let [app             (js/document.getElementById "app")
        [width height]  (u/window-size)]
    (remove-all-childs app)
    (->> (safe-start width height)
         (mapv (partial ->canvas app))
         (reset! sketches))))

(defn update-sketch [context {:keys [paint prev-sketch ctx] :as sketch}]
  (if paint
    (try
      (let [next-sketch (paint context)]
        (if (not= prev-sketch next-sketch)
          (do
            (render-sketch sketch next-sketch)
            (assoc sketch :prev-sketch next-sketch))
          sketch))
      (catch :default e
        (js/console.log "error:" e)
        (dissoc sketch :paint)))
    sketch))

(defn run [_]
  (let [context  {:ts (timestamp)}
        update   (partial update-sketch context)]
    (swap! sketches (fn [s] (mapv update s))))
  (schedule run))

(defn restart []
  (reset! sketches (init-sketch)))

(defn start []
  (restart)
  (run nil)
  nil)

(defonce main-loop (start))
