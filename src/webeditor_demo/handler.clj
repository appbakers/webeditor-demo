(ns webeditor-demo.handler
  (:require
            [webeditor-demo.views :as views]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            ))

(defroutes app-routes
  (GET "/"
       []
       (views/home-page))
  (GET "/add-location"
       []
       (views/add-location-page))
  (POST "/add-location"
        {params :params}
        (views/add-location-results-page params))
  (GET "/location/:loc-id"
       [loc-id]
       (views/location-page loc-id))
  (GET "/all-locations"
       []
       (views/all-locations-page))

  (GET "/chg-board/:board-id"
       [board-id]
       (views/chg-board-page board-id))
  (POST "/chg-board/:board-id"
        {params :params}
;;        (println (str params "x"))
        (views/chg-board-results-page params))

  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
