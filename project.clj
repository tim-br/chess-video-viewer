(defproject shouter "0.0.3"
  :description "Shouter app"
  :url "https://github.com/technomancy/shouter"
  :min-lein-version "2.0.0"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.542"]
                 [org.clojure/java.jdbc "0.4.1"]
                 [cheshire "5.6.3"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.2"]
                 [ring/ring-devel "1.5.0"]
                 [ring/ring-anti-forgery "1.0.1"]
                 [ring-middleware-format "0.7.0"]
                 [selmer "1.0.7"]
                 [crypto-password "0.2.0"]
                 [hiccup "1.0.5"]]
  :plugins [#_[lein-figwheel "0.5.8"]
            [lein-cljsbuild "1.1.6"]]

  :cljsbuild {
              :builds {:dev {:source-paths ["src/cljs"]
                             ;:figwheel     true
                             :compiler     {:main       "hello-seymore.core"
                                            :output-to  "resources/public/javascript/main.js"
                                            :output-dir "resources/public/cljs/out_dev"
                                            :asset-path "cljs/out_dev"}}

                       :prod {:source-paths ["src/cljs"]
                              ;:figwheel true
                              :compiler     {:optimizations :whitespace
                                             :main "hello-seymore.core"
                                             :output-to  "resources/public/javascript/main.js"
                                             :output-dir "resources/public/cljs/out"
                                             :asset-path "cljs/out"}}}
              }

  :clean-targets [:target-path "out"]

  :main ^:skip-aot shouter.web
  :uberjar-name "shouter-standalone.jar"
  :ring {:handler shouter.web/application
         :init shouter.models.migration/migrate}
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring-mock "0.1.5"]]}
             :uberjar {:aot :all}})
