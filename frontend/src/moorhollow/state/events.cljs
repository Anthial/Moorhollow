(ns moorhollow.state.events
  (:require [ajax.core :refer [GET POST]]
            [moorhollow.state.state :refer [add-message add-options]]))


;ex handlers from ajax core
(defn handler [response]
  (let [response (.parse js/JSON response)]
    (js/console.log response)
    (add-options (aget response "options"))
    (add-message (aget response "message"))))

(defn error-handler [{:keys [status status-text]}]
  (js/alert (str "something bad happened: " status " " status-text)))

(defn make-choice [input]
  (POST "http://192.168.0.33:5000/getmessage" {:format :json
                                            :params {:input input :token "Test Token"}
                                            :handler handler
                                            :error-handler error-handler}))
