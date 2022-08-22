(ns moorhollow.header
  (:require
   [rum.core :as rum :refer [adapt-class]] 
   ["react-dom" :as rdom]
   ["react-router-dom" :refer [Link HashRouter]]
   ["@mui/material/" :as mui]))

(rum/defc header [token]
  (let [pages (if (not= token "") ["Home" "Game" "About"] ["Home" "About"])
        [anchorElNav setAnchorElNav] (rum/use-state nil)]
    (defn handleOpenNavMenu
      [event]
      (setAnchorElNav event.currentTarget))

    (defn handleCloseNavMenu [] (setAnchorElNav nil))

    (adapt-class mui/AppBar {:position "fixed" :padding "10px" :sx #js {:background-color "darkorange"}}
       (adapt-class mui/Container 
          (adapt-class mui/Toolbar {:disableGutters true} 
              (adapt-class mui/Button {:variant "h6" :nowrap "true" :component Link :to "/"
                             :sx #js {:mr 2 :display #js {:xs "none" :md "flex"}
                                      :fontFamily "Jolly Lodger"
                                      :fontWeight 700
                                      :letterSpacing ".2rem"
                                      :fontSize "18px"
                                      :color "purple"
                                      :textDecoration "none"}} "Moorhollow, the Adventure Game")
             (adapt-class mui/Box {:sx #js {:flexGrow 1 :display #js {:xs "flex" :md "none"}}}
                (adapt-class mui/IconButton {:size "large" :aria-controls "menu-appbar"
                                   :aria-haspopup "true"
                                   :onClick handleOpenNavMenu
                                   :color "inherit"}
                   "📜")
                (adapt-class mui/Menu {:id "menu-appbar"
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
                          (adapt-class mui/MenuItem {:key page :onClick handleCloseNavMenu
                                           :component Link :to (str "/" page)}
                             (adapt-class mui/Typography {:sx #js {:fontFamily "Jolly Lodger"
                                                         :fontSize "22px"
                                                         :color "purple"}
                                                :textAlign "center"} page))) pages)))
             (adapt-class mui/Typography {:variant "h5" :nowrap "true"
                                :component Link
                                :to "/"
                                :sx #js {:mr 2
                                         :display #js {:xs "flex" :md "none"}
                                         :flexGrow 1
                                         :fontFamily "Jolly Lodger"
                                         :fontWeight 700
                                         :letterSpacing ".3rem"
                                         :color "purple"
                                         :textDecoration "none"
                                         :textAlign "center"}} "Moorhollow, the Adventure Game")
            
             (adapt-class mui/Box {:justifyContent "right" :sx #js {:flexGrow 1 :display #js {:xs "none" :md "flex"}}}
                (map (fn [page] (adapt-class mui/Button {:key page 
                                               :component Link :to (str "/" page)
                                               :sx #js {:my 2
                                                        :marginLeft "25px" :marginRight "25px"
                                                        :fontFamily "Jolly Lodger"
                                                        :fontWeight "200"
                                                        :fontSize "18px"
                                                        :color "purple"
                                                        :display "block"}} page)) pages)))))))


