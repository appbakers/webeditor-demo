(require '[clojure.java.jdbc :as sql])

(def db-spec
  '{:classname "org.h2.Driver"
   :subprotocol "h2:file"
   :subname "db/webeditor-demo"})

(sql/with-connection
  db-spec
  (sql/drop-table :boards))

;; (sql/with-connection
;;   db-spec
;;   (sql/create-table :boards
;;                     [:id "bigint primary key auto_increment"]
;;                     [:title "varchar"]
;;                     [:content "clob"]))

(sql/with-connection
  db-spec
  (sql/create-table :boards
                    [:id "bigint primary key auto_increment"]
                    [:title "varchar"]
                    [:content "varchar"]))


(sql/with-connection
  db-spec
  (sql/insert-records :boards
                      {:title "this is test" :content "this is clob data"}))
(sql/with-connection
  db-spec
  (sql/update-or-insert-values :boards ["id = ?" 2]
                               {:title "2 changed" :content "changed"}))

(sql/with-connection db-spec
                  (sql/with-query-results res
                ["select id, title, content from boards"]
                (doall res)))
(sql/with-connection db-spec
                     (sql/delete-rows :boards ["id = ?" 1]))
