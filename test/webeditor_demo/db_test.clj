(ns webeditor-demo.db
  (:require [clojure.java.jdbc :as sql]))

(def db-spec {:classname "org.h2.Driver"
              :subprotocol "h2:file"
              :subname "db/webeditor-demo"})


(defn chg-board
  [board-id title content]
  (let [results (sql/with-connection db-spec
                                     (sql/update-or-insert-values
                                       :boards
                                       ["id = ?" board-id]
                                       {:title title :content content}))]
   (assert (= (count results) 1))
   (first (vals results)))

  )


  (testing "chg-board update-or-insert success"
    (let [results (sql/with-connection db-spec
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
