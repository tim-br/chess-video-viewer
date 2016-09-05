(ns shouter.controllers.shouts
  (:require [cheshire.core :refer [generate-string]]
            [compojure.core :refer [defroutes GET POST DELETE]]
            [clojure.string :as str]
            [ring.util.response :as ring]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]
            [ring.middleware.session :as session]
            [ring.util.anti-forgery :as a]
            [ring.util.response :as response]
            [shouter.views.shouts :as view]
            [shouter.models.shout :as model]
            [clojure.java.io :as io]))

;(def index view/index)

(defn index []
  (view/index #_(model/all)))

(defn login
  [request]
  (let [{params :params} request
        {email :email} params
        {password :password} params
        {session :session} request]
    (println email)
    (println password)
    (println session)
    (if
      (model/verify-user-by-email email password)
      ;"access approved"
      (let [session (assoc session :logged_in_user (model/user-by-email email))]
        (->
          (if (model/is-admin? (model/user-by-email email))
            (ring/redirect "/admin_controller.html")
            (ring/redirect "/main_view.html"))
          #_{:status  200
           :headers {"Content-Type" "text/plain"}
           :body    (str "You are logged in as " email)}
          (assoc :session session)))
      #_(do
        (println "YSE")
        (let [session (assoc session :logged_in_email email)]
          (-> (str "You are logged in as " email)
              (assoc :session session))))
      (->
        {:status  200
         :headers {"Content-Type" "text/plain"}
         :body    "Hello World"}
        (assoc :session session))
      #_(-> (response/response "access denied")))))

(defn test-session
  [{session :session}]
  (println session)
  (str (:logged_in_email session)))

(defn create
  [shout]
  (when-not (or (str/blank? shout)
                (> (count shout) 512))
    (model/create shout))
  (ring/redirect "/"))

(defn main-view
  [{session :session}]
  (let [user-id (:logged_in_user session)]
    (if user-id
      (view/main-view)
      (str "access forbidden"))))

(defn admin-controller
  [{session :session}]
  (println "the session is")
  (println session)
  (let [user-id (:logged_in_user session)]
    (if (model/is-admin? user-id)
      (view/admin-view)
      (str "access denied"))))

(defn all-videos-for-semester
  [request]
  (let [{params :params} request]
    #_(println (model/all-videos-in-semester params))
    {:body (vec (model/all-videos-in-semester params))}))

;@title = params[:title]
;@url = params[:url]
;@week_number = params[:week_number]
;@semester_number = params[:semester_number]
;@is_beginner = params[:is_beginner]
;@is_advanced = params[:is_advanced]
;new_video(@title, @url, @week_number, @semester_number, @is_beginner, @is_advanced)

;def new_video(title, url, week_number, semester_number, is_beginner, is_advanced)
;Video.create(title: title,
;                    url: url,
;                     week_number: week_number,
;                    semester_number: semester_number,
;                    is_beginner: is_beginner,
;                    is_advanced: is_advanced)
;end
;

(defn add-video
  [request]
  (println "yoko")
  (let [{params :params} request
        {session :session} request
        user-id (:logged_in_user session)]
    (if (model/is-admin? user-id)
      (model/insert-video! params)
      (str "user is not admin, not allowed"))
    #_(let [user-id (:logged_in_user session)]
      (str "hello world")
      #_(if (model/is-admin? user-id)
        (view/admin-view)
        (str "access denied")))))

(defroutes routes
  (GET  "/" [] (index))
  (POST "/verify_login" [email name] (println email))
  (GET "/verify_login" [] (str "<h1>holy tsrat </h1>"))
  #_(GET "/test-session" request (test-session request))
  (GET "/main_view.html" request (main-view request))
  (GET "/admin_controller.html" request (admin-controller request))
  (DELETE "/videos/:id" [id]
    (model/delete-video! id))
  (GET "/videos/:level/:semester" request
    (all-videos-for-semester request))
  (GET "/videos" request
    (model/get-all-videos))
  (POST "/videos/" request
    (add-video request))
  (POST "/" request (login request)))

;get '/videos/:level/:semester_number' do
;content_type :json
;all_videos_for_semester(params[:level].to_i, params[:semester_number].to_i).to_json
;end
