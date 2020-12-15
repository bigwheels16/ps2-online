(defproject ps2-online "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/data.json "0.2.6"]
                 [clj-http "3.7.0"]
                 [compojure "1.5.2"]
                 [ring/ring-core "1.7.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-json "0.4.0"]]

  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler com.jkbff.ps2.online.handler/app
         :port 8080
         :host "0.0.0.0"}

  :main com.jkbff.ps2.online.core
  :init-ns com.jkbff.ps2.online.core
  :repl-options {:init-ns com.jkbff.ps2.online.core})
