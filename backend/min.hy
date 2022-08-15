#!/usr/bin/env hys

(import flask [Flask request])
(import flask_cors [CORS])
(import json)

(setv app (Flask __name__))
(CORS app :resources "/*" :origins ["http://localhost:3000" "http://192.168.0.33:3000" "https://192.168.0.33:3000"])

(defn do-option [text]
  (setv text (.lower text))
  (cond 
    (= text "init") {"message" "Welcome to Moorhollow, I hope you'll have a wonderful time here..." "options" ["LOOK" "GRAB" "LONGASS"]}
    (= text "look") {"message" "You look around and find that this is only a static example." "options" ["INIT" "KEY" "LONGASS"]}
    (= text "grab") {"message" "You try to grab the static example but nothing changes..." "options" ["LOOK" "KEY" "INIT"]}
    (= text "key") {"message" "There is no key, you get nothing." "options" ["LOOK" "GRAB"]}
    (= text "longass") {"message" "Lorem ipsum dolor sit amet, consectetur adipiscing elit, 
               sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
               Erat pellentesque adipiscing commodo elit at imperdiet dui accumsan. 
               Id venenatis a condimentum vitae. Quam viverra orci sagittis eu volutpat odio. 
               Egestas egestas fringilla phasellus faucibus scelerisque. 
               Egestas fringilla phasellus faucibus scelerisque. 
               Mi in nulla posuere sollicitudin aliquam ultrices sagittis orci. 
               In nulla posuere sollicitudin aliquam ultrices sagittis. 
               Feugiat nisl pretium fusce id. Auctor augue mauris augue neque." "options" ["LOOK" "GRAB" "KEY"]}
    (is-not text None ){"message" "Sorry, I don't understand what you are trying to dododo." "options" ["LOOK" "GRAB" "KEY"]}
    (= text None) {"message" "WTF" "options" ["LOOK" "KEY" "INIT"]}))


(defn [(app.post "/getmessage")] hello-world []
    (setv request_data (request.get_json))
    (print request_data)
    (json.dumps (do-option (:input request_data))))