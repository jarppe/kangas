(ns kangas.canvas.simple
  (:require [kangas.util :as u]
            [kangas.canvas.render :refer [render]]))

(defmethod render :clear-rect [ctx [_ x y w h]]
  (.clearRect ctx x y w h))

(defmethod render :draw-rect [ctx [_ x y w h]]
  (.strokeRect ctx x y w h))

(defmethod render :fill-rect [ctx [_ x y w h]]
  (.fillRect ctx x y w h))

(defmethod render :begin-path [ctx [_ & elements]]
  (.beginPath ctx)
  (doseq [e elements]
    (render ctx e)))

(defmethod render :close-path [ctx _]
  (.closePath ctx))

(defmethod render :line-to [ctx [_ x y]]
  (.lineTo ctx x y))

(defmethod render :move-to [ctx [_ x y]]
  (.moveTo ctx x y))

(defmethod render :fill-path [ctx _]
  (.fill ctx))

(defmethod render :draw-path [ctx _]
  (.stroke ctx))

(defmethod render :arc [ctx [_ x y radius start-angle end-angle & [clockwise]]]
  (.arc ctx
    x y
    radius
    (u/deg->rad start-angle)
    (u/deg->rad end-angle)
    (if (nil? clockwise) false (not clockwise))))

(defmethod render :arc-to [ctx [_ x1 y1 x2 y2 radius]]
  (.arcTo ctx x1 y1 x2 y2 radius))
