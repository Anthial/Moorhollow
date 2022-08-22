#!/usr/bin/env hys

(import flask [Flask request])
(import flask_cors [CORS])
(import json)
(require hyrule [case])

(setv app (Flask __name__))
(CORS app :resources "/*" :origins ["http://localhost:3000" "http://192.168.0.33:3000" "https://192.168.0.33:3000"])

(defn get-redirects [user]
  (case user
  "test-user" {"moorhollow-water" "moorhollow-key"}))

(defn get-available-options [area]
  (setv redirects (get-redirects "test-user"))
  (if (in area redirects)
    (get-available-options (get redirects area))
  (get-options-from-db area)))

(defn get-options-from-db [area]
  (case area
   "moorhollow-init" {:options ["LOOK" "GRAB" "LONGASS"]}
   "moorhollow-water" {:options ["LOOK" "GRAB" "KEY"]}
   "moorhollow-key" {:options ["LOOK" "INIT"]}))

(defn get-current-area [user]
  (case user
  "test-user" "moorhollow-water"))

(defn handle-option [option]
  (case (.lower option)
    "init" {"message" "Welcome to Moorhollow, I hope you'll have a wonderful time here..." "options" ["LOOK" "GRAB" "LONGASS"]}
    "look" {"message" "You look around and find that this is only a static example." "options" ["INIT" "KEY" "LONGASS"]}
    "grab" {"message" "You try to grab the static example but nothing changes..." "options" ["LOOK" "KEY" "INIT"]}
    "key" {"message" "There is no key, you get nothing." "options" ["LOOK" "GRAB"]}
    "longass" {"message" "Lorem ipsum dolor sit amet, consectetur adipiscing elit, 
               sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
               Erat pellentesque adipiscing commodo elit at imperdiet dui accumsan. 
               Id venenatis a condimentum vitae. Quam viverra orci sagittis eu volutpat odio. 
               Egestas egestas fringilla phasellus faucibus scelerisque. 
               Egestas fringilla phasellus faucibus scelerisque. 
               Mi in nulla posuere sollicitudin aliquam ultrices sagittis orci. 
               In nulla posuere sollicitudin aliquam ultrices sagittis. 
               Feugiat nisl pretium fusce id. Auctor augue mauris augue neque." "options" ["LOOK" "GRAB" "KEY"]}
    else {"message" "Sorry, I don't understand what you are trying to dododo." "options" ["LOOK" "GRAB" "KEY"]}))

(defn do-option [text]
  (setv text (.upper text))
  (setv available-options (get-available-options (get-current-area "test-user")))
  (print available-options)
  (if (in text (:options available-options))
    (handle-option text)
    {"message" "Sorry, you can't do that." "options" available-options}))


(defn [(app.post "/getmessage")] hello-world []
    (setv request_data (request.get_json))
    (print request_data)
    (json.dumps (do-option (:input request_data))))

(if (= __name__ "__main__")  
  (app.run :host "192.168.0.33" :port 5000 :debug True)
  None)