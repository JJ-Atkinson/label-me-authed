(ns luminus-image-tagging.routes.home
  (:require
   [luminus-image-tagging.layout :as layout]
   [luminus-image-tagging.db.core :as db]
   [clojure.java.io :as io]
   [luminus-image-tagging.middleware :as middleware]
   [ring.util.response]
   [ring.util.http-response :as response]))

(defn home-page [request]
  (layout/render request "home.html"))

(defn home-routes []
  [""
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/" {:get home-page}]
   ["/auth-only" {:middleware [middleware/wrap-auth]
                  :get (fn [_]
                         (-> (response/ok "authed")
                             (response/header "Content-Type" "text/plain; charset=utf-8")))}]
   ["/docs" {:get (fn [_]
                    (-> (response/ok (-> "docs/docs.md" io/resource slurp))
                        (response/header "Content-Type" "text/plain; charset=utf-8")))}]])

