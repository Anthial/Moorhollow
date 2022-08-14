(ns moorhollow.state.state
  (:require [ajax.core :refer [GET POST]]
            [clojure.core.async :refer [chan put!]]))





(def request-message
  "Welcome to Moorhollow, I hope you'll have a wonderful time here...")

(def initial-state (atom {:token "TEST"
                          :messages []}))

(defn get-token []
  (:token @initial-state))

(defn set-token [input]
  (swap! initial-state assoc :token input))

(def message-c (chan 1))
(defn get-messages [] 
    (:messages @initial-state))

(defn add-message [input]
  (let [messages (take 10 (conj (get-messages) input))]
    (swap! initial-state assoc :messages messages)
    (put! message-c (get-messages))))

;ex handlers from ajax core
(defn handler [response]
  (add-message response))

(defn error-handler [{:keys [status status-text]}]
  (.log js/console (str "something bad happened: " status " " status-text)))

(defn get-choice-response [input]
  (POST "http://localhost:5000/getmessage" {:format :json
                                            :params {:input input :token "Test Token"}
                                            :handler handler
                                            :error-handler error-handler}))

(defn make-choice [input]
  (get-choice-response input))



