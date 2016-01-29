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

(defn get-all-boards
  []
  (let [results (sql/with-connection db-spec
                  (sql/with-query-results res
                ["select id, title, content from boards"]
                (doall res)))]
    results))

(defn get-board
  [board-id]
  (let [results (sql/with-connection db-spec
                (sql/with-query-results res
                                        ["select id, title, content from boards where id = ?" board-id]
                                        (doall res)))]
;;    (assert (= (count results) 1))
    (first results)))

(defn chg-board
  [board-id m]
  (let [results (sql/with-connection db-spec
                                     (sql/update-or-insert-values
                                       :boards
                                       ["id = ?" board-id]
                                       m))]
;;  (println results)
   (assert (= (count results) 1))
   (first results)))
;
;; (get-board 3)
;; (get-board 2)
;; (get-board 1)
;; (get-all-boards)

;; (chg-board 2 {:title "xx" :content "xxfew"})
;; (chg-board 1 {:title "1" :content "xxfew"})








