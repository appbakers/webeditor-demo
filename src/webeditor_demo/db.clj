(ns webeditor-demo.db
  (:require [clojure.java.jdbc :as sql]))

(def db-spec {:classname "org.h2.Driver"
              :subprotocol "h2:file"
              :subname "db/webeditor-demo"})

(defn add-location-to-db
  [x y]
  (let [results (sql/with-connection db-spec
                                     (sql/insert-record :locations
                                                        {:x x :y y}))]
    (assert (= (count results) 1))
    (first (vals results))))

(defn get-xy
  [loc-id]
  (let [results (sql/with-connection db-spec
                (sql/with-query-results res
                                        ["select x, y from locations where id = ?" loc-id]
                                        (doall res)))]
    (assert (= (count results) 1))
    (first results)))

(defn get-all-locations
  []
  (let [results (sql/with-connection db-spec
                  (sql/with-query-results res
                ["select id, x, y from locations"]
                (doall res)))]
    results))

(defn get-board
  [board-id]
  (let [results (sql/with-connection db-spec
                (sql/with-query-results res
                                        ["select id, title, content from boards where id = ?" board-id]
                                        (doall res)))]
    (assert (= (count results) 1))
    (first results)))

(defn chg-board
  [board-id m]
  (let [results (sql/with-connection db-spec
                                     (sql/update-or-insert-values
                                       :boards
                                       ["id = ?" board-id]
                                       m))]
   (assert (= (count results) 1))
    (get-board (first results))
   ))








