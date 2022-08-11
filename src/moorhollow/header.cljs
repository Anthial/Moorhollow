(ns moorhollow.header
  (:require
   [rum.core :as rum]
   ["react-dom" :as rdom]
   ["react-router-dom" :refer [Link HashRouter]]
   ["@mui/material/" :as mui]))


(rum/defc header []
  (let [pages ["Home" "Game" "About"]
        [anchorElNav setAnchorElNav] (rum/use-state nil)]
    (defn handleOpenNavMenu
      [event]
      (setAnchorElNav event.currentTarget))

    (defn handleCloseNavMenu [] (setAnchorElNav nil))

    (rum/adapt-class mui/AppBar {:position "fixed" :padding "10px" :color "secondary"}
       (rum/adapt-class mui/Container 
          (rum/adapt-class mui/Toolbar {:disableGutters true} 
              (rum/adapt-class mui/Button {:variant "h6" :nowrap "true" :component Link :to "/"
                             :sx #js {:mr 2 :display #js {:xs "none" :md "flex"}
                                      :fontFamily "Jolly Lodger"
                                      :fontWeight 700
                                      :letterSpacing ".2rem"
                                      :fontSize "18px"
                                      :color "inherit"
                                      :textDecoration "none"}} "Moorhollow, the Adventure Game")
             (rum/adapt-class mui/Box {:sx #js {:flexGrow 1 :display #js {:xs "flex" :md "none"}}}
                (rum/adapt-class mui/IconButton {:size "large" :aria-controls "menu-appbar"
                                   :aria-haspopup "true"
                                   :onClick handleOpenNavMenu
                                   :color "inherit"}
                   "📜")
                (rum/adapt-class mui/Menu {:id "menu-appbar"
                             :anchorEl anchorElNav
                             :anchorOrigin #js {:vertical "bottom"
                                             :horizontal "left"}
                             :keepMounted true
                             :transformOrigin #js {:vertical "top"
                                               :horizontal "left"}
                             :open anchorElNav
                             :onClose handleCloseNavMenu
                             :sx #js {:display #js {:xs "block" :md "none"}}}
                   (map (fn [page]
                          (rum/adapt-class mui/MenuItem {:key page :onClick handleCloseNavMenu
                                           :component Link :to (str "/" page)}
                             (rum/adapt-class mui/Typography {:sx #js {:fontFamily "Jolly Lodger"
                                                         :fontSize "22px"
                                                         :color "inherit"}
                                                :textAlign "center"} page))) pages)))
             (rum/adapt-class mui/Typography {:variant "h5" :nowrap "true"
                                :component Link
                                :to "/"
                                :sx #js {:mr 2
                                         :display #js {:xs "flex" :md "none"}
                                         :flexGrow 1
                                         :fontFamily "Jolly Lodger"
                                         :fontWeight 700
                                         :letterSpacing ".3rem"
                                         :color "inherit"
                                         :textDecoration "none"
                                         :textAlign "center"}} "Moorhollow, the Adventure Game")
            
             (rum/adapt-class mui/Box {:justifyContent "right" :sx #js {:flexGrow 1 :display #js {:xs "none" :md "flex"}}}
                (map (fn [page] (rum/adapt-class mui/Button {:key page 
                                               :component Link :to (str "/" page)
                                               :sx #js {:my 2
                                                        :marginLeft "25px" :marginRight "25px"
                                                        :fontFamily "Jolly Lodger"
                                                        :fontWeight "200"
                                                        :fontSize "18px"
                                                        :color "white"
                                                        :display "block"}} page)) pages)))))))


