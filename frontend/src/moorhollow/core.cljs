(ns moorhollow.core
  (:require
   [rum.core :as rum]
   ["react-dom/client" :as rdom]
   ["react-router-dom" :refer [HashRouter Routes Route]]
   [moorhollow.header :refer [header]]
   [moorhollow.game :refer [get-game-ui]]
   [moorhollow.login :refer [get-login-ui]]))

;; -------------------------
;; Views






(rum/defc home-page [] 
    (rum/adapt-class HashRouter
     [:div {:class "bg-lightgrey"}
            (header "l")
            (rum/adapt-class Routes
                             (rum/adapt-class Route {:path "/" :element (get-login-ui "")}) 
                   (rum/adapt-class Route {:path "/game" :element (get-game-ui)}))]))

;; -------------------------
;; Initialize app
(defonce root (rdom/createRoot (js/document.getElementById "app")))
;(set! root (rdom/createRoot (js/document.getElementById "app")))
(defn mount-root []
  (.render root (home-page)))
  ;(d/render [home-page] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
