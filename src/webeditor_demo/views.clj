(ns webeditor-demo.views
  (:require [webeditor-demo.db :as db]
            [clojure.string :as str]
            [hiccup.page :as h]))

(defn gen-page-head
  [title]
  [:head
   [:title (str "Locations: " title)]
   (h/include-css "/css/styles.css")])


;;<link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.no-icons.min.css" rel="stylesheet">
;;<link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-responsive.min.css" rel="stylesheet">
;;<link href="http://netdna.bootstrapcdn.com/font-awesome/3.0.2/css/font-awesome.css" rel="stylesheet">
;;<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
;;<script src="external/jquery.hotkeys.js"></script>
;;<script src="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
(defn gen-page-head-basicwebeditor
  [title]
  [:head
   [:title (str "Basic Webeditor Bootstrap Wysisyg: " title)]

(h/include-css "/js/basic/external/google-code-prettify/prettify.css")

   (h/include-css "http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.no-icons.min.css")
 (h/include-css  "http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-responsive.min.css")
 (h/include-css  "http://netdna.bootstrapcdn.com/font-awesome/3.0.2/css/font-awesome.css")
 (h/include-js  "http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js")
 (h/include-js "/js/basic/external/jquery.hotkeys.js")
 (h/include-js "http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/js/bootstrap.min.js")
 (h/include-js "/js/basic/external/google-code-prettify/prettify.js")
 (h/include-css "/js/basic/index.css")

 (h/include-js "/js/basic/bootstrap-wysiwyg.js")
 (h/include-js "https://code.jquery.com/ui/1.10.3/jquery-ui.js")
 (h/include-js "/js/paste-image/jquery.paste_image_reader.js")
 (h/include-js "/js/paste-image/jquery.paste_image_reader_impl.js")

 (h/include-js "/js/basic/main-init.js")

   (h/include-css "/css/styles.css")])

(defn gen-page-head-jqtewebeditor
  [title]
  [:head
   [:title (str "Jquery Text Editor Webeditor: " title)]
   (h/include-css "/js/jqte/jquery-te-1.4.0.css")
 (h/include-js  "http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js")
   (h/include-js "/js/jqte/jquery-te-1.4.0.js")
   (h/include-css "/js/jqte/demo/demo.css")
   (h/include-js "/js/paste-image/jquery.paste_image_reader.js")

 (h/include-js "/js/jqte/webeditor-init.js")
   (h/include-css "/css/styles.css")])


(def header-links
  [:div#header-links
   "[ "
   [:a {:href "/"} "Home"]
   " | "
   [:a {:href "/add-location"} "Add a Location"]
   " | "
   [:a {:href "/all-locations"} "View All Locations"]
   " | "
   [:a {:href "/chg-board/1"} "view 1"]
   " | "
   [:a {:href "/chg-board/2"} "view 2"]
   " ]"])

(defn home-page
  []
  (h/html5
    (gen-page-head "Home")
    header-links
    [:h1 "Home"]
    [:p "Webapp to store and display some 2D (x,y) locations."]))

(defn add-location-page
  []
  (h/html5
    (gen-page-head "Add a Location")
    header-links
    [:h1 "Add a Location"]
    [:form {:action "/add-location" :method "POST"}
     [:p "x value: " [:input {:type "text" :name "x"}]]
     [:p "y value: " [:input {:type "text" :name "y"}]]
     [:p [:input {:type "submit" :value "submit location"}]]]))

(defn add-location-results-page
  [{:keys [x y]}]
  (let [id (db/add-location-to-db x y)]
    (h/html5
      (gen-page-head "Added a Location")
      header-links
      [:h1 "Added a Location"]
      [:p "Added [" x ", " y "] (id: " id ") to the db. "
       [:a {:href (str "/location/" id)} "See for yourself"]
       "."])))

(defn location-page
  [loc-id]
  (let [{x :x y :y} (db/get-xy loc-id)]
    (h/html5
      (gen-page-head (str "Location " loc-id))
      header-links
      [:h1 "A Single Location"]
      [:p "id: " loc-id]
      [:p "x: " x]
      [:p "y: " y])))

(defn all-locations-page
  []
  (let [all-locs (db/get-all-locations)]
    (h/html5
      (gen-page-head "All locations in the db")
      header-links
      [:h1 "All Locations"]
      [:table
       [:tr [:th "id"] [:th "x"] [:th "y"]]
       (for [loc all-locs]
         [:tr [:td (:id loc)] [:td (:x loc)] [:td (:y loc)]])])))

(defn chg-board-page
  [board-id]
  (let [result (db/get-board board-id)]
  (h/html5
    (gen-page-head-jqtewebeditor "webeditor changing")
    header-links
    [:h1 "webeditor"]
    [:form {:action (str "/chg-board/" board-id) :method "POST"}
     [:p "board-id: " [:input {:type "text"  :name "board-id" :value (:id result)}]]
     [:p "title: " [:input {:type "text" :name "title" :value (:title result)}]]
     [:p "content: " [:input {:type "text" :value (:content result)}]]
     [:textarea {:class "editor" :name "content"} (:content result)]
;;     [:textarea {:class "editor"} "default value. Please change"]
;;     [:textarea {:class "editor"} "default value. Please change"]
     [:p [:input {:type "submit" :value "submit content"}]]])))

(defn chg-board-results-page
  [{:keys [board-id title content]}]
  (let [result (db/chg-board board-id {:title title :content content})]
    (h/html5
      (gen-page-head-jqtewebeditor "webeditor changed")
      header-links
      [:h1 "change applied"]
    [:form {:action (str "/chg-board/" board-id) :method "POST"}
     [:p "board-id: " [:input {:type "text"  :name "board-id" :value (:id result)}]]
     [:p "title: " [:input {:type "text" :name "title" :value (:title result)}]]
     [:textarea {:class "editor" :name "content"} (:content result)]
     [:p [:input {:type "submit" :value "submit content"}]]])))
