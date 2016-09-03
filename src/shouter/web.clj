(ns shouter.web
  (:require [compojure.core :refer [defroutes]]
            [ring.adapter.jetty :as ring]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [shouter.controllers.shouts :as shouts]
            [shouter.views.layout :as layout]
            [shouter.models.migration :as schema]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.session :as session]
            [ring.middleware.format :refer [wrap-restful-format]]
            [ring.middleware.anti-forgery :refer [wrap-anti-forgery]])
  (:gen-class))

(defroutes routes
  shouts/routes
  (route/resources "/")
  (route/not-found (layout/four-oh-four)))

(def application
  (wrap-reload
    (session/wrap-session
      (wrap-restful-format
        (wrap-defaults routes (update-in site-defaults [:security] dissoc :frame-options))))))

(defn start [port]
  (ring/run-jetty #'application {:port  port
                                 :join? false}))

(defn -main []
  ;(schema/migrate)
  (let [port (Integer. (or (System/getenv "PORT") "5000"))]
    (start port)))
