(defproject kangas "0.0.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-3126"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [metosin/potpuri "0.2.1"]]

  :aliases {"develop" ["do" "clean" ["figwheel"]]}

  :source-paths ["src/clj"]
  :test-paths ["test/clj"]
  :resource-paths ["resources"]
  :auto-clean false

  :profiles {:dev {:dependencies [[http-kit "2.1.19"]
                                  [figwheel "0.2.5" :exclusions [org.clojure/clojure]]]
                   :plugins [[lein-cljsbuild "1.0.5"]
                             [lein-figwheel "0.2.5"]]
                   :resource-paths ["target/dev"]}}

  :figwheel {:http-server-root  "public"
             :server-port       3449
             :repl              false
             :server-logfile    "target/figwheel-logfile.log"}

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs" "src/dev-cljs" "src/sketch"]
                        :compiler {:main            "kangas.figwheel"
                                   :asset-path      "js/out"
                                   :output-to       "target/dev/public/js/kangas.js"
                                   :output-dir      "target/dev/public/js/out"
                                   :source-map      true
                                   :optimizations   :none
                                   :cache-analysis  true
                                   :pretty-print    true}}
                       {:id "min"
                        :source-paths ["src/cljs" "src/sketch"]
                        :compiler {:main           "kangas.main"
                                   :output-to      "target/adv/public/js/kangas.js"
                                   :optimizations  :advanced
                                   :elide-asserts  true
                                   :pretty-print   false}}]})
