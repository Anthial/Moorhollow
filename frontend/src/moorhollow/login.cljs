(ns moorhollow.login
  (:require
   [rum.core :as rum]
   ["@mui/material/" :as mui]
   [moorhollow.state.state :refer [get-token set-token]]))





(rum/defc get-login-ui [set-token!]
          (let [[email set-email!] (rum/use-state "")
                [password set-password!] (rum/use-state "")
                [register set-register!] (rum/use-state "Register")]
          [:div {:class-name "p-6 max-w-xl  mx-auto bg-white rounded-xl 
                         shadow-lg flex items-center space-x-4"}
           [:div {:class-name "shrink-1"}
            [:div {:class-name "p-2 text-4xl text-center 
                                       font-spooky"} "Welcome to Moorhollow"]]
           [:div {:class-name "pt-3"}
            [:div [:input {:class-name "p-2 text-left w-5/12 text-md border-solid border-2" :type "text"
                           :on-change (fn [event] (set-email! event.target.value))
                           :on-key-press (fn [event]
                                           (if (= event.key "Enter")
                                             (js/console.log email)))
                           :placeholder "Email"}] 
             [:input {:class-name "p-2 m-3 text-left w-5/12 text-md border-solid border-2" :type "text"
                      :on-change (fn [event] (set-password! event.target.value))
                      :on-key-press (fn [event]
                                      (if (= event.key "Enter")
                                        (js/console.log password)))
                      :placeholder "Password"}]]
            [:div {:class-name "flex align-items-center"}
             (rum/adapt-class mui/Button {:variant "h6" :nowrap "true"
                                               :sx #js {:mr 2
                                                        :fontFamily "Jolly Lodger"
                                                        :fontWeight 700
                                                        :letterSpacing ".2rem"
                                                        :fontSize "18px"
                                                        :color "inherit"
                                                        :textDecoration "none"}} "Log in!")
             (rum/adapt-class mui/Button {:variant "h6" :nowrap "true"
                                          :sx #js {:mr 2
                                                   :fontFamily "Jolly Lodger"
                                                   :fontWeight 700
                                                   :letterSpacing ".2rem"
                                                   :fontSize "18px"
                                                   :color "inherit"
                                                   :textDecoration "none"}
                                          :onClick (fn [event] (do
                                                                 (js/console.log (get-token))
                                                                 (set-register! (get-token))
                                                                 (set-token "REGISTErrrR")
                                                                 (js/console.log (get-token))
                                                                 (set-register! (get-token))))} register)]]]))