(ns kangas.canvas.render)

(defmulti render (fn [ctx [element-type & _]] element-type))

(defmethod render :default [_ [element-type & _]]
  (js/console.log "error: Unknown element type:" (pr-str element-type)))
