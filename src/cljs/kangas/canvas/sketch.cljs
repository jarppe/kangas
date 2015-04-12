(ns kangas.canvas.sketch
  (:require [kangas.util :as u]
            [kangas.canvas.render :refer [render]]))

(defmethod render :sketch [ctx [_ & elements]]
  (doseq [e elements]
    (render ctx e)))
