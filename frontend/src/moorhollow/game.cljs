(ns moorhollow.game
  (:require [rum.core :as rum]
            ["react-device-detect" :refer [isMobile]]
            [moorhollow.state.state :refer [get-messages message-c get-options]]
            [moorhollow.state.events :refer [make-choice]]
            [clojure.core.async :refer [go-loop <!]]))

;(defn get-options [set-options]
;  (set-options (clojure.string/join " " ["LOOK" "GRAB" "KEY"])))

;(defn moorhollow-input [set-options]
  ;)
(defn update-response [input current-messages set-messages set-options]
  (make-choice input)
  (js/console.log current-messages)
  (go-loop [data (<! message-c)]
    (set-messages (:messages data))
    (js/console.log data)
    (set-options (clojure.string/join " " (get-options)))))


(rum/defc get-desktop-ui []
  (let [[messages set-messages] (rum/use-state (get-messages))
        [input set-input] (rum/use-state "")
        [options set-options] (rum/use-state (clojure.string/join " " ["LOOK" "GRAB" "KEY"]))]
    [:div {:class "p-6 max-w-xl  mx-auto bg-rose-900 rounded-xl 
                         shadow-lg items-center space-x-4"}
     [:div {:class "shrink-1"}
      [:div {:class "p-2 text-4xl text-center 
                                       font-spooky text-gray-300"} "Welcome to Moorhollow"]]
     [:div {:class "box-border overflow-y-auto h-72 
                                flex flex-col-reverse break-word 
                                [overflow-anchor: none] border-solid 
                                border-2 text-sm font-serif
                    bg-neutral-800 border-red-300"}
      [:div {:class "text-center p-4 font-bold text-gray-300"} options]
      [:div (map-indexed (fn [idx item] [:div
                                         {:key (str "message " (mod idx 15)) :class "pt-2 pl-2 text-gray-300"} item])
                         (reverse messages))]
      [:div {:class "[overflow-anchor: auto]"}]]
     [:div {:class "pt-3"}
      [:div [:input {:class "p-2 text-left w-full text-md 
                             bg-neutral-800	 text-gray-300 
                             border-solid border-2 border-red-300" :type "text"
                     :on-change (fn [event] (set-input event.target.value))
                     :on-key-press (fn [event]
                                     (if (= event.key "Enter")
                                       (do
                                         ;(moorhollow-input set-options)
                                         (update-response input messages set-messages set-options))))
                     :placeholder "Choice"}]]]]))

(rum/defc get-mobile-ui []
  (let [[messages set-messages] (rum/use-state [(get-messages)])
        [input set-input] (rum/use-state "")
        [options set-options] (rum/use-state [(clojure.string/join " " ["LOOK" "GRAB" "KEY"])])]
    [:div
     [:div {:class "shrink-1"}
      [:div {:class "p-2 text-4xl text-center text-gray-300
                                       font-spooky"} "Welcome to Moorhollow"]]
     [:div {:class "overflow-y-auto h-[15rem] md:h-72 
                                flex flex-col-reverse break-word
                                [overflow-anchor: none] 
                                text-sm font-serif
                    bg-neutral-800"}
      [:div {:class "text-center p-4 text-gray-300 font-bold"} options]
      [:div {:class "p-4 text-center text-gray-300"} (map-indexed (fn [idx item] [:div
                                                                                  {:key (str "message " (mod idx 15)) :class "pt-2 pl-2 text-gray-300"} item])
                                                                  (reverse messages))]
      [:div {:class "[overflow-anchor: auto]"}]]
     [:div {:class "pt-2"}
      [:input {:class "p-2 max-w-xl text-center bg-neutral-800 
                       text-gray-300 w-full text-md" :type "text"
               :on-change (fn [event] (set-input event.target.value))
               :on-key-press (fn [event]
                               (if (= event.key "Enter")
                                 (do
                                   (js/alert "YOU PRESSED ENTER")
                                   ;(moorhollow-input set-options)
                                   (update-response input messages set-messages set-options))))
               :placeholder "Choice"}]]]))


(rum/defc get-game-ui []
  (if (= (str isMobile) "true")
    (get-mobile-ui)
    (get-desktop-ui)))