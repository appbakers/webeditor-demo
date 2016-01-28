(require '[clojure.java.jdbc :as sql])

(def db-spec
  '{:classname "org.h2.Driver"
   :subprotocol "h2:file"
   :subname "db/clj-webdriver-tutorial"})

(sql/with-connection
  db-spec
  (sql/drop-table :board_data))

(sql/with-connection
  db-spec
  (sql/create-table :board_data
                    [:id "bigint primary key auto_increment"]
                    [:title "varchar"]
                    [:content "clob"]))

(sql/with-connection
  db-spec
  (sql/create-table :board_data
                    [:id "bigint primary key auto_increment"]
                    [:title "varchar"]
                    [:content "varchar(4000)"]))


(sql/with-connection
  db-spec
  (sql/insert-records :board_data
                      {:title "this is test" :content "this is clob data"}))
(sql/with-connection db-spec
                  (sql/with-query-results res
                ["select id, title, content from board_data"]
                (doall res)))
