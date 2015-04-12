(ns kangas.figwheel
  (:require [kangas.main :as m]
            [figwheel.client :as fw]))

(fw/start {
  :websocket-url "ws://localhost:3449/figwheel-ws"
  :on-jsload m/restart})
