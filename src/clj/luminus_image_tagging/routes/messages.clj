(ns luminus-image-tagging.routes.messages
  (:require [luminus-image-tagging.layout :as layout]
            [luminus-image-tagging.db.core :as db]
            [clojure.java.io :as io]
            [luminus-image-tagging.middleware :as middleware]
            [ring.util.http-response :as response]
            [struct.core :as st]))



(def message-schema
  [[:name
    st/required
    st/string]

   [:message
    st/required
    st/string
    {:message "message must contain at least 10 characters"
     :validate #(> (count %) 9)}]])

(defn validate-message [params]
  (first (st/validate params message-schema)))


(defn save-message! [{:keys [params]}]
  (if-let [errors (validate-message params)]
    (-> (response/found "/")
        (assoc :flash (assoc params :errors errors)))
    (do
      (println :got-a-request params)
      (response/found "/"))))