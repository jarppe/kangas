(ns kangas.canvas
  (:require [kangas.util :as u]
            [kangas.canvas.render :refer [render]]
            [kangas.canvas.sketch]
            [kangas.canvas.simple]
            [kangas.canvas.styles]
            [kangas.canvas.text]
            [kangas.canvas.transformations]))

(defn clear [ctx w h background]
  (if-not background
    (doto ctx
      (.clearRect 0 0 w h))
    (doto ctx
      (.save)
      (aset "fillStyle" (apply u/rgb background))
      (.fillRect 0 0 w h)
      (.restore))))

(defn render-sketch [{:keys [ctx w h background] [tx ty] :translate [sx sy] :scale} sketch]
  (doto ctx
    (.save)
    (clear w h background)
    (.translate tx ty)
    (.scale sx sy)
    (render sketch)
    (.restore)))
