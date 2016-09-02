(ns shouter.views.shouts
  (:require [shouter.views.layout :as layout]
            [hiccup.core :refer [h]]
            [hiccup.form :as form]
            [clojure.java.io :as io]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]
            [ring.util.anti-forgery :as anti-forgery]
            [selmer.parser :as parser]))

(selmer.parser/add-tag! :csrf-field (fn [_ _] (ring.util.anti-forgery/anti-forgery-field)))

(defn shout-form []
  [:div {:id "shout-form" :class "sixteen columns alpha omega"}
   (form/form-to [:post "/"]
                 (anti-forgery/anti-forgery-field)
                 (form/label "shout" "What do you want to SHOUT?")
                 (form/text-area "shout")
                 (form/submit-button "SHOUT!"))])

(defn display-shouts [shouts]
  [:div {:class "shouts sixteen columns alpha omega"}
   (map
    (fn [shout] [:h2 {:class "shout"} (h (:body shout))])
    shouts)])

(defn index [shouts]
  (layout/common "SHOUTER"
                 (shout-form)
                 [:div {:class "clear"}]
                 (display-shouts shouts)))

(defn login-page
  []
  (io/resource
    "public/login.html"))

(defn index2
  []
  (slurp (login-page)))

(defn index
  []
  (parser/render (index2)  {:csrf-token *anti-forgery-token*}))

(defn main-view
  []
  (-> (io/resource "main_view.html")
      slurp
      (parser/render {:csrf-token  (str *anti-forgery-token*)})))

(defn admin-view
  []
  (-> (io/resource "admin_controller.html")
      slurp
      (parser/render {:csrf-token  (str *anti-forgery-token*)})))

