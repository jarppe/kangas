(ns kangas.util)

(defn fn-> [& fns]
  (apply comp (reverse fns)))

(defn window-size []
  [js/window.innerWidth js/window.innerHeight])

(def PI   Math/PI)
(def PI2  (* PI 2))

(def floor Math/floor)

(defn scale [{:keys [from-min from-max to-min to-max]
              :or {from-min 0 from-max 0 to-min 0 to-max 0}}
             v]
  (if v
    (let [scale (/ (- to-max to-min) (- from-max from-min))]
      (+ to-min (* v scale)))))

(defn scaler [from-min from-max to-min to-max]
  (let [scale (/ (- to-max to-min) (- from-max from-min))]
    (fn [v]
      (if v
        (+ to-min (* v scale))))))

(defn bound [min-val max-val v]
  (if v
    (-> v (min max-val) (max min-val))))

(def rgb
  (let [scale (fn-> (scaler 0.0 100.0 0.0 256.0) (partial bound 0.0 255.0) floor)
        ascale (fn-> (scaler 0.0 100.0 0.0 1.0) (partial bound 0.0 1.0))]
    (fn [r g b & [a]]
      (str "rgba(" (scale r) "," (scale g) "," (scale b) "," (or (ascale a) "1") ")"))))

(defn rad->deg [r] (-> r (/ PI) (* 180.0) (+ 90.0)))
(defn deg->rad [r] (-> r (- 90.0) (/ 180.0) (* PI)))

(defn timestamp->date [ts]
  (let [d (js/Date. ts)]
    [(-> d .getYear (+ 1900))
     (-> d .getMonth inc)
     (.getDate d)
     (.getHours d)
     (.getMinutes d)
     (.getSeconds d)
     (.getMilliseconds d)]))

(defn safe-map [m missing-message default-value]
  (fn [v]
    (or (get m v)
        (do
          (js/console.log (str "error: " missing-message ": " (pr-str v)))
          default-value))))
