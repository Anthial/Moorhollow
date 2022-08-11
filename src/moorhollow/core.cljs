(ns moorhollow.core
  (:require
   [rum.core :as rum]
   ["react-dom/client" :as rdom]
   ["react-router-dom" :refer [HashRouter Routes Route]]
   [moorhollow.header :refer [header]]
   [clojure.string :as str]
   ["@mui/material/" :as mui]
   ["react-device-detect" :refer [isMobile]]))

;; -------------------------
;; Views



(rum/defc textbox-text []
  [:div {:class-name "box-content"} [:div {:class-name "p-1 text-left"}  "You've found your way to the old city of Moorhollow. What do you do?"]
         [:div {:class-name "p-1 font-bold text-center"}  "Look Grab Key"]
         [:div {:class-name "p-1 italic text-right"} "Look"]
         [:div {:class-name "p-1 text-left"}  "This is a static example, but still."]
         [:div {:class-name "p-1 font-bold text-center"}  "Look Grab Key"]
         [:div {:class-name "p-1 italic text-right"} "Look"]])

(defn get-options [set-options]
  (set-options (clojure.string/join " " ["LOOK" "GRAB" "KEY"])))

(defn do-option [text]
  (case (clojure.string/lower-case text)
    "look" "You look around and find that this is only a static example."
    "grab" "You try to grab the static example but nothing changes..."
    "key" "There is no key, you get nothing."
    "longass" "Lorem ipsum dolor sit amet, consectetur adipiscing elit, 
               sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
               Erat pellentesque adipiscing commodo elit at imperdiet dui accumsan. 
               Id venenatis a condimentum vitae. Quam viverra orci sagittis eu volutpat odio. 
               Egestas egestas fringilla phasellus faucibus scelerisque. 
               Egestas fringilla phasellus faucibus scelerisque. 
               Mi in nulla posuere sollicitudin aliquam ultrices sagittis orci. 
               In nulla posuere sollicitudin aliquam ultrices sagittis. 
               Feugiat nisl pretium fusce id. Auctor augue mauris augue neque."
    "Sorry, I don't understand what you are trying to do."))

(defn moorhollow-input [set-options]
  (get-options set-options))
(defn update-response [input current-messages set-messages]
  (set-messages (take 10 (conj current-messages (do-option input)))))


(rum/defc get-desktop-ui []
  (let [[messages set-messages] (rum/use-state [])
        [input set-input] (rum/use-state "")
        [options set-options] (rum/use-state [])]
    [:div {:class-name "p-6 max-w-xl  mx-auto bg-white rounded-xl 
                         shadow-lg items-center space-x-4"}
           [:div {:class-name "shrink-1"}
                  [:div {:class-name "p-2 text-4xl text-center 
                                       font-spooky"} "Welcome to Moorhollow"]]
           [:div {:class-name "box-border overflow-y-auto h-72 
                                flex flex-col-reverse break-all 
                                [overflow-anchor: none] border-solid 
                                border-2 text-sm font-serif"}
                  [:div {:class "text-center p-4 font-bold"} options]
                  [:div (map-indexed (fn [idx item] [:div
                                                      {:key (str "message " (mod idx 15))} item])
                                      messages)]
                  [:div {:class-name "[overflow-anchor: auto]"}]]
           [:div {:class-name "pt-3"}
              [:div [:input {:class-name "p-2 text-left w-full text-md border-solid border-2" :type "text"
                               :on-change (fn [event] (set-input event.target.value))
                               :on-key-press (fn [event]
                                               (if (= event.key "Enter")
                                                 (do
                                                   (moorhollow-input set-options)
                                                   (update-response input messages set-messages))))
                               :placeholder "Choice"}]]]]))

(rum/defc get-mobile-ui []
  (let [[messages set-messages] (rum/use-state [])
        [input set-input] (rum/use-state "")
        [options set-options] (rum/use-state [])]
    [:div
     [:div {:class-name "shrink-1"}
            [:div {:class-name "p-2 text-4xl text-center 
                                       font-spooky"} "Welcome to Moorhollow"]]
     [:div {:class-name "overflow-y-auto h-[15rem] md:h-72 
                                flex flex-col-reverse break-word
                                [overflow-anchor: none] 
                                text-sm font-serif"}
            [:div {:class "text-center p-4 font-bold"} options]
            [:div {:class "p-4 text-center"} (map-indexed (fn [idx item] [:div
                                                {:key (str "message " (mod idx 15)) :class-name "pt-4"}] item)
                                (reverse messages))]
            [:div {:class-name "[overflow-anchor: auto]"}]]
     [:div {:class-name "pt-2"}
            [:input {:class-name "p-2 max-w-xl text-center w-full text-md" :type "text"
                      :on-change (fn [event] (set-input event.target.value))
                      :on-key-press (fn [event]
                                      (if (= event.key "Enter")
                                        (do
                                          (moorhollow-input set-options)
                                          (update-response input messages set-messages))))
                      :placeholder "Choice"}]]]))


(rum/defc greeting [] 
  (if (= (str isMobile) "true")
    (get-mobile-ui)
    (get-desktop-ui)))



(rum/defc home-page []
  (rum/adapt-class HashRouter
     [:div {:class-name "bg-lightgrey"}
            (header)
            (rum/adapt-class Routes
               (rum/adapt-class Route {:path "/" :element (greeting)}))]))

;; -------------------------
;; Initialize app
(defonce root (rdom/createRoot (js/document.getElementById "app")))
;(set! root (rdom/createRoot (js/document.getElementById "app")))
(defn mount-root []
  (.render root (home-page)))
  ;(d/render [home-page] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
