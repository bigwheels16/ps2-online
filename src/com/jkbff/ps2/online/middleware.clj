(ns com.jkbff.ps2.online.middleware)

(defn trim-trailing-slash
    [handler]
    (fn [request]
        (let [uri         (:uri request)
              last-letter (last uri)]
            (if (and (= \/ last-letter) (not= "/" uri))
                (handler (assoc request :uri (subs uri 0 (dec (count uri)))))
                (handler request)))))

(defn log-request-and-response
    [handler]
    (fn [request]
        (prn "request:" request)
        (let [response (handler request)]
            (prn "response:" response)
            response)))

; taken from: https://github.com/ring-clojure/ring/issues/353
(defn wrap-hide-exceptions [handler error-response]
    (fn [request]
        (if error-response
            (try (handler request)
                 (catch Exception e
                     (.printStackTrace e)
                     error-response))

            (handler request))))
