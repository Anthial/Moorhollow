#!/usr/bin/env hys

(import flask [Flask request])
(import flask_cors [CORS])

(setv app (Flask __name__))
(CORS app :resources "/*" :origins ["http://localhost:3000"])

(defn do-option [text]
  (cond 
    (= text "init") "Welcome to Moorhollow, I hope you'll have a wonderful time here..."
    (= text "look") "You look around and find that this is only a static example."
    (= text "grab") "You try to grab the static example but nothing changes..."
    (= text "key") "There is no key, you get nothing."
    (= text "longass") "Lorem ipsum dolor sit amet, consectetur adipiscing elit, 
               sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
               Erat pellentesque adipiscing commodo elit at imperdiet dui accumsan. 
               Id venenatis a condimentum vitae. Quam viverra orci sagittis eu volutpat odio. 
               Egestas egestas fringilla phasellus faucibus scelerisque. 
               Egestas fringilla phasellus faucibus scelerisque. 
               Mi in nulla posuere sollicitudin aliquam ultrices sagittis orci. 
               In nulla posuere sollicitudin aliquam ultrices sagittis. 
               Feugiat nisl pretium fusce id. Auctor augue mauris augue neque."
    (is-not text None )"Sorry, I don't understand what you are trying to dododo."
    (= text None) "WTF"))


(defn [(app.post "/getmessage")] hello-world []
    (setv request_data (request.get_json))
    (do-option (:input request_data)))