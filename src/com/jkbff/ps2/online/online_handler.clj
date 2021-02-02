(ns com.jkbff.ps2.online.online-handler
    (:require [com.jkbff.ps2.online.api :as api]
              [com.jkbff.ps2.online.config :as config]
              [com.jkbff.ps2.online.helper :as helper]))

(defn format-member
    [member]
    (let [name (get-in member [:character :name :first])
          battle-rank (get-in member [:character :battle-rank :value])
          outfit-alias (get-in member [:outfit :alias])
          outfit-name (get-in member [:outfit :name])
          rank-name (:rank member)
          rank-number (:rank-ordinal member)
          character-id (:character-id member)
          last-login (Integer/parseInt (get-in member [:character :times :last-login]))]

        {:character    (str name " (" character-id ")")
         :battle-rank  battle-rank
         :outfit       (str "[" outfit-alias "] " outfit-name)
         :rank         (str rank-name " (" rank-number ")")
         :time-online  (helper/get-time-str (- (quot (System/currentTimeMillis) 1000) last-login))}))

(defn get-online-characters
    []
    (let [outfit-members (flatten (map #(api/get-outfit-members %) (config/SUBSCRIBE_OUTFITS)))
          online-members (filter #(= "1" (:online-status %)) outfit-members)
          formatted-members (map format-member online-members)]
        {:body {:online-members (group-by :outfit formatted-members)}}))