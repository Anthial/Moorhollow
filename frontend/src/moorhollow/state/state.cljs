(ns moorhollow.state.state
  (:require [ajax.core :refer [GET POST]]
            [clojure.core.async :refer [chan put!]]))





(def request-message
  "Welcome to Moorhollow, I hope you'll have a wonderful time here...")

(def initial-state (atom {:token "TEST"
                          :messages []
                          :options []}))

(defn get-token []
  (:token @initial-state))

(defn set-token [input]
  (swap! initial-state assoc :token input))


(defn get-options []
  (:options @initial-state))
(defn add-options [input]
  (swap! initial-state assoc :options input))

(def message-c (chan 1))
(defn respond-message-c []
  (put! message-c "200 OK"))

(defn get-messages []
  (:messages @initial-state))
(defn add-message [input]
  (let [messages (take 10 (conj (get-messages) input))]
    (swap! initial-state assoc :messages messages))) 



