#!/usr/bin/env hys

(import flask [Flask request])
(import flask_cors [CORS])
(import json)
(import flask_pymongo [PyMongo])
(require hyrule [case as-> ->])

(setv app (Flask __name__))
(CORS app :resources "/*" :origins ["http://localhost:3000" "http://192.168.0.33:3000" "https://192.168.0.33:3000"])
(setv (get app.config "MONGO_URI") "mongodb://localhost:27017/Moorhollow")
(setv mongo (PyMongo app))

(defn demo-db [map]
(mongo.db.Demo.find_one_or_404 map))

(defn get-area-text [area]
(:text (demo-db {"_id" area})))

(defn get-area-options [area redirects]
  (if (in area redirects)
    (do (print "TEST FAILED")
    (get-area-options (get redirects area)))
  (get-options-from-db area)))

(defn get-options-from-db [area]
  (:options (demo-db {"_id" area})))

(defn look-at [area]
  (:look (:options (demo-db {"_id" area}))))

(defn do-grab [area items]
  (let [potential-item (:grab (:options (demo-db {"_id" area})))]
  (items.append (:item potential-item))
  (mongo.db.users.find_one_and_update {"_id" "test-user-1"} {"$set" {"items" items}})
  (:text potential-item)))

(defn do-use [items]
(str ["You totally used: " items]))

(defn go-to [option user-data area redirects]
  (as-> (demo-db {"_id" area}) $
    (:options $)
    (get $ option)
    (mongo.db.users.find_one_and_update {"_id" "test-user-1"} {"$set" {"current-area" $}})
    (get-user-data "test-user-1")
    
    (do 
    (print (get-area-options (get $ "current-area") redirects))
    {"message" (get-area-text  (get $ "current-area")) "options" (filter-options (get-area-options (get $ "current-area") redirects) (:items user-data))})))

(defn filter-options [area-options items]
  (let [area-keys (.keys area-options)]
  (if (and (in "grab" area-keys) (in (:item (:grab area-options)) items))
    (do (area-options.pop "grab" None) 
    (list (.keys area-options)))
    (list area-keys))))

(defn handle-option [option user-data]
  (let [area (get user-data "current-area")
      items (:items user-data)
      area-options (get-area-options area (:redirects user-data))
      current-options (filter-options area-options items)]
  (case (.lower option)
    "grab" {"message" (do-grab area items) "options" (filter-options area-options (:items (get-user-data "test-user-1")))}
    "use" {"message" (do-use items) "options" current-options}
    "look" {"message" (look-at area) "options" current-options}
    else (go-to option user-data area (:redirects user-data)))))

(defn get-user-data [user]
  (mongo.db.users.find_one_or_404 {"_id" user}))

(defn do-option [text]
  (let [text (.lower text)
  user-data (get-user-data "test-user-1")
  redirects (:redirects user-data)
  current-area (get user-data "current-area") 
  area-options (get-area-options current-area redirects)]
  (if (in text (filter-options area-options (:items user-data)))
    (handle-option text user-data)
    {"message" "Sorry, you can't do that." "options" (list (.keys area-options))})))


(defn [(app.post "/getmessage")] post-get-message []
    (setv request_data (request.get_json))
    (print request_data)
    (json.dumps (do-option (:input request_data))))

(defn [(app.post "/getinitmessage")] post-get-init []
    (setv request_data (request.get_json))
    (print request_data)
    (json.dumps (let [u-data (get-user-data (:user request_data))
                      u-area (get u-data "current-area")]
                {"message" (get-area-text u-area)})))
                
(defn [(app.post "/getinitoptions")] post-get-init-options []
    (setv request_data (request.get_json))
    (print request_data)
    (json.dumps (let [u-data (get-user-data (:user request_data))
                      u-area (get u-data "current-area")]
                {"options" (filter-options (get-area-options u-area (:redirects u-data)) (:items u-data))})))

(if (= __name__ "__main__")  
  (app.run :host "192.168.0.33" :port 5000 :debug True)
  None)