(ns kangas.canvas.text
  (:require [kangas.util :as u]
            [kangas.canvas.render :refer [render]]
            [kangas.canvas.styles :refer [apply-style]]
            [goog.string.format]
            [goog.string :as s]))

(defmethod apply-style :text-font [ctx _ v]
  (aset ctx "font" v))

(def text-align (u/safe-map {:start   "start"
                             :end     "end"
                             :left    "end"
                             :right   "right"
                             :center  "center"}
                            "unknown text-align"
                            "start"))

(defmethod apply-style :text-align [ctx _ v]
  (aset ctx "textAlign" (text-align v)))

(def text-baseline (u/safe-map {:top          "top"
                                :hanging      "hanging"
                                :middle       "middle"
                                :alphabetic   "alphabetic"
                                :ideographic  "ideographic"
                                :bottom       "bottom"}
                               "unknown text-baseline"
                               "alphabetic"))

(defmethod apply-style :text-baseline [ctx _ v]
  (aset ctx "textBaseline" (text-baseline v)))

(defmethod render :draw-text [ctx [_ & [x y fmt & args]]]
  (.strokeText ctx (apply s/format fmt args) x y))

(defmethod render :fill-text [ctx [_ & [x y fmt & args]]]
  (.fillText ctx (apply s/format fmt args) x y))
