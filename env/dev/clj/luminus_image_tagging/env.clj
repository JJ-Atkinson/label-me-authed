(ns luminus-image-tagging.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [luminus-image-tagging.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[luminus-image-tagging started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[luminus-image-tagging has shut down successfully]=-"))
   :middleware wrap-dev})
