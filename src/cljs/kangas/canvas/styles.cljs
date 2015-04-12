(ns kangas.canvas.styles
  (:require [kangas.util :as u]
            [kangas.canvas.render :refer [render]]))

(defmulti apply-style (fn [ctx k v] k))

(defmethod apply-style :default [ctx k v]
  (js/console.log "error: unknown style:" (pr-str k)))

(defmethod apply-style :fill-color [ctx _ v]
  (aset ctx "fillStyle" (apply u/rgb v)))

(defmethod apply-style :line-color [ctx _ v]
  (aset ctx "strokeStyle" (apply u/rgb v)))

(defmethod apply-style :line-width [ctx _ v]
  (aset ctx "lineWidth" v))

(def line-cap (u/safe-map {:butt    "butt"
                           :round   "round"
                           :square  "square"}
                        "unknown line cap"
                        "butt"))

(defmethod apply-style :line-cap [ctx _ v]
  (aset ctx "lineCap" (line-cap v)))

(def line-join (u/safe-map {:round  "round"
                            :bevel  "bevel"
                            :miter  "miter"}
                         "unknown line join:"
                         "round"))

(defmethod apply-style :line-join [ctx _ v]
  (aset ctx "lineJoin" (line-join v)))

(defmethod apply-style :miter-limit [ctx _ v]
  (aset ctx "miterLimit" v))

(defmethod render :style [ctx [_ style & elements]]
  (.save ctx)
  (doseq [[k v] style]
    (apply-style ctx k v))
  (doseq [e elements]
    (render ctx e))
  (.restore ctx))
