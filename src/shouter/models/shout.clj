(ns shouter.models.shout
  (:require [clojure.java.jdbc :as sql]
            [crypto.password.bcrypt :as password]))

(def spec (or (System/getenv "DATABASE_URL")
              "postgresql://localhost:5432/shouter"))

(defn all []
  (into [] (sql/query spec ["select * from shouts order by id desc limit 128"])))

(defn create [shout]
  (sql/insert! spec :shouts [:body] [shout]))

(defn insert-user!
  [user-name email password]
  (let [enc-password (password/encrypt password)]
    (sql/insert! spec
                 :users
                 {:full_name user-name :email email :enc_password enc-password})))

(defn verify-user
  [user-id password]
  (let [enc-password (-> (sql/query spec
                                    ["SELECT enc_password
                                      FROM users WHERE id = ?" user-id])
                         first
                         :enc_password)]
    (password/check password enc-password)))

(defn user-by-email
  [email]
  (-> (sql/query spec ["SELECT id FROM users WHERE email = ?" email])
      first
      :id))

(defn verify-user-by-email
  [email password]
  (let [user-id (user-by-email email)]
    (verify-user user-id password)))

(defn is-admin?
  [id]
  (let [result (-> (sql/query spec ["SELECT is_admin FROM users WHERE id = ?" id])
                   first
                   :is_admin)]
    result))

(defn insert-video!
  [{:keys [title url week_number semester_number is_beginner is_advanced is_intermediate]}]
  (sql/insert! spec
               :videos
               {:title           title :url url
                :week_number     (Integer/parseInt week_number)
                :semester_number (Integer/parseInt semester_number)
                :is_beginner     (boolean (Boolean/valueOf is_beginner))
                :is_advanced     (boolean (Boolean/valueOf is_advanced))
                :is_intermediate     (boolean (Boolean/valueOf is_advanced))}))

(defn all-videos-in-semester
  [{:keys [level semester]}]
  (case (Integer/parseInt level)
    1
    (sql/query spec ["SELECT * FROM videos WHERE semester_number = ? AND is_beginner = ? " (Integer/parseInt semester) (boolean (Boolean/valueOf "TRUE"))])

    2
    (sql/query spec ["SELECT * FROM videos WHERE semester_number = ? AND is_advanced = ? " (Integer/parseInt semester) (boolean (Boolean/valueOf "TRUE"))])))