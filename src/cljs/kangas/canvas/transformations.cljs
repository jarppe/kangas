(ns kangas.canvas.transformations
  (:require [kangas.util :as u]
            [kangas.canvas.render :refer [render]]))

(defmulti apply-transformation (fn [ctx k v] k))

(defmethod apply-transformation :default [_ k _]
  (js/console.log "error: unknown transformation:" (pr-str k)))

(defmethod apply-transformation :translate [ctx _ [x y]]
  (.translate ctx x y))

(defmethod apply-transformation :rotate [ctx _ r]
  (.rotate ctx (-> r (/ 180.0) (* u/PI))))

(defmethod apply-transformation :scale [ctx _ [x y]]
  (.rotate ctx x (or y x)))

(defmethod apply-transformation :matrix [ctx _ [a b c d e f]]
  (.transform ctx a b c d e f))

(defn apply-transformations [ctx transformations]
  (doseq [[k v] transformations]
    (apply-transformation ctx k v)))

(defn with-transformations [ctx t elements]
  (.save ctx)
  (apply-transformations ctx t)
  (doseq [e elements]
    (render ctx e))
  (.restore ctx))

(defmethod render :transform [ctx [_ transformations & elements]]
  (with-transformations ctx transformations elements))

(defmethod render :translate [ctx [_ t & elements]]
  (with-transformations ctx {:translate t} elements))

(defmethod render :rotate [ctx [_ t & elements]]
  (with-transformations ctx {:rotate t} elements))

(defmethod render :scale [ctx [_ t & elements]]
  (with-transformations ctx {:scale t} elements))
