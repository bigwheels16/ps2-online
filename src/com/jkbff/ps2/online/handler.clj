(ns com.jkbff.ps2.online.handler
    (:require [compojure.core :refer :all]
              [compojure.route :as route]
              [com.jkbff.ps2.online.middleware :as middleware]
              [com.jkbff.ps2.online.online-handler :as online-handler]
              [com.jkbff.ps2.online.helper :as helper]
              [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
              [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
              [ring.middleware.multipart-params :as mp]))

(defroutes open-routes
           (GET "/" []
                 (online-handler/get-online-characters)))

(defroutes unknown-route
           (route/not-found {:body {:message "Not Found"}}))

(def app (-> (routes
                 open-routes
                 unknown-route)
             middleware/log-request-and-response
             (middleware/wrap-hide-exceptions {:status 500
                                               :body {:message "Internal Server Error"}})
             (wrap-json-response {:key-fn #(helper/entities-fn (name %))})
             middleware/trim-trailing-slash
             (wrap-json-body {:keywords? #(keyword (helper/identifiers-fn %))})
             (wrap-defaults api-defaults)
             ;ring.middleware.params/wrap-params
             ;(ring.middleware.multipart-params/wrap-multipart-params :store ba/byte-array-store)
             ))