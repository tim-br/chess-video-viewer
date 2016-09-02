(ns shouter.models.migration
  (:require [clojure.java.jdbc :as sql]
            [shouter.models.shout :as shout]))

(defn migrated? []
  (-> (sql/query shout/spec
                 [(str "select count(*) from information_schema.tables "
                       "where table_name='shouts'")])
      first :count pos?))

(defn migrate []
  (when (not (migrated?))
    (print "Creating database structure...") (flush)
    (sql/db-do-commands shout/spec
                        (sql/create-table-ddl
                         :users
                         [:user_id :serial "PRIMARY KEY"]
                         [:full_name :text "NOT NULL"]
                         [:email :text "NOT NULL"]
                         [:enc_password :text "NOT NULL"]
                         [:created_at :timestamp
                          "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]))
    (println " done")))

