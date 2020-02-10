(ns luminus-image-tagging.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[luminus-image-tagging started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[luminus-image-tagging has shut down successfully]=-"))
   :middleware identity})
