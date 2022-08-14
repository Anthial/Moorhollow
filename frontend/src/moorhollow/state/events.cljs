(ns moorhollow.state.events
  (:require [ajax.core :refer [GET POST]]
            [moorhollow.state.state :refer [add-message]]))

;ex handlers from ajax core
(defn handler [response]
  (add-message response))

(defn error-handler [{:keys [status status-text]}]
  (.log js/console (str "something bad happened: " status " " status-text)))

(defn get-choice-response [input]
  (POST "http://localhost:5000/getmessage" {:body {:input input :token "Test Token"}
                                     :handler handler
                                     :error-handler error-handler}))