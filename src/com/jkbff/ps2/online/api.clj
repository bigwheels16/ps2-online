(ns com.jkbff.ps2.online.api
    (:require [clj-http.client :as client]
              [com.jkbff.ps2.online.helper :as helper]
              [com.jkbff.ps2.online.config :as config]))

(def LANG :en)
(def BASE-URL (str "https://census.daybreakgames.com/s:" (config/SERVICE_ID) "/get/ps2:v2"))
(defn create-url
    [collection-type query-string]
    (str BASE-URL "/" collection-type "?" (clojure.string/join "&" (map (fn [[k v]] (str k "=" v)) (seq query-string)))))

(defn get-outfit-members
    [outfit-id]
    (let [url    (create-url "outfit_member" {"outfit_id" outfit-id
                                              "c:resolve" "online_status,character,outfit"
                                              "c:limit" "1000"})
          result (client/get url)
          body   (helper/read-json (:body result))]
        (:outfit-member-list body)))