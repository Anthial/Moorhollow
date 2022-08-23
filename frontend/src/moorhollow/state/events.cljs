(ns moorhollow.state.events
  (:require [ajax.core :refer [GET POST]]
            [moorhollow.state.state :refer [add-message add-options respond-message-c]]))


;ex handlers from ajax core
(defn handler [response]
  (let [response (.parse js/JSON response)]
    (js/console.log response)
    (add-options (aget response "options"))
    (add-message (aget response "message"))
    (respond-message-c)))

(defn error-handler [{:keys [status status-text]}]
  (js/alert (str "something bad happened: " status " " status-text)))

(defn make-choice [input]
  (POST "http://192.168.0.33:5000/getmessage" {:format :json
                                            :params {:input input :token "Test Token"}
                                            :handler handler
                                            :error-handler error-handler}))
(defn handle-init-message [response]
  (let [response (.parse js/JSON response)]
    (js/console.log response)
    (add-message (aget response "message"))
    (respond-message-c)))

(defn handle-init-options [response]
  (let [response (.parse js/JSON response)]
    (js/console.log response)
    (add-options (aget response "options"))
    (respond-message-c)))

(defn fetch-init-message []
  (POST "http://192.168.0.33:5000/getinitmessage" {:format :json
                                               :params {:user "test-user-1" :token "Test Token"}
                                               :handler handle-init-message
                                               :error-handler error-handler}))
(defn fetch-init-options []
  (POST "http://192.168.0.33:5000/getinitoptions" {:format :json
                                                   :params {:user "test-user-1" :token "Test Token"}
                                                   :handler handle-init-options
                                                   :error-handler error-handler}))
