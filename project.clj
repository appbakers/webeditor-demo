(defproject webeditor-demo "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                        [fact/lein-light-nrepl "0.1.3"]

		 [hiccup "1.0.2"]
		 [org.clojure/java.jdbc "0.2.3"]
		 [com.h2database/h2 "1.3.170"]
;;		 [org.hsqldb/hsqldb "2.3.2"]
		 ]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler webeditor-demo.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]
                        [fact/lein-light-nrepl "0.1.3"]
			[clj-webdriver "0.7.1"]
			[org.seleniumhq.selenium/selenium-server "2.46.0"]
			[ring/ring-jetty-adapter "1.4.0"]]}})
