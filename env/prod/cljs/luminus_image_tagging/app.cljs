(ns luminus-image-tagging.app
  (:require [luminus-image-tagging.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
